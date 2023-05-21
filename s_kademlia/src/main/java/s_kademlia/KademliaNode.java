package s_kademlia;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import com.google.protobuf.ByteString;

import generated.nodeAPIGrpc;
import generated.NodeAPI.*;
import generated.nodeAPIGrpc.nodeAPIImplBase;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import s_kademlia.node.Node;
import s_kademlia.routing.RoutingTable;
import s_kademlia.storage.KadStorageManager;
import s_kademlia.storage.KadStorageValue;
import s_kademlia.utils.CryptoHash;
import s_kademlia.utils.KademliaUtils;

/**
 * Server that functions as the actual node in the network
 */
public class KademliaNode extends nodeAPIImplBase {
    private final transient Node localNode;
    private final transient RoutingTable routingTable;
    private final transient KadStorageManager storageManager;
    Logger logger = Logger.getLogger(KademliaNode.class.getName());

    public KademliaNode(Node localNode) {
        this.localNode = localNode;
        routingTable = new RoutingTable(localNode);
        storageManager = new KadStorageManager();
    }

    /**
     * Constructor used only for the Bootstrap Nodes. Does not initiate the bootstap
     * process.
     * 
     * @param name
     * @param port
     * @throws NoSuchAlgorithmException
     */
    public KademliaNode(String name, int port) throws NoSuchAlgorithmException {
        this(new Node(name, port));
    }

    /**
     * Create a Node object and join a network via the Bootstrap Node
     * 
     * @param name          Access Point name of the node
     * @param port          Access Point port of the node
     * @param bootstrapName Access Point name of the Bootstrap node
     * @param bootstrapPort Access Point port of the Bootstrap node
     * @throws NoSuchAlgorithmException
     */
    public KademliaNode(String name, int port, String bootstrapName, int bootstrapPort)
            throws NoSuchAlgorithmException {
        this(new Node(name, port));
        Node bootstrapNode = new Node(bootstrapName, bootstrapPort);
        this.bootstrap(bootstrapNode);
    }
    
    /**
     * Joins a network via the Bootstrap Node
     * 
     * @param bootstrapName Access Point name of the Bootstrap node
     * @param bootstrapPort Access Point port of the Bootstrap node
     */
    private void bootstrap(Node bootstrapNode) {
        routingTable.insert(bootstrapNode);
        logger.info("Initiating Bootstrap");
        // Establish a Channel with the bootstrap node and execute a
        // FIND_NODE(MY_NODE_ID), this will return a list of the K closest nodes.
        ManagedChannel channel = ManagedChannelBuilder.forAddress(bootstrapNode.getName(), bootstrapNode.getPort())
        .usePlaintext()
        .build();
        logger.info("Created Channel with Bootstrap Node " + channel + " to " + bootstrapNode.getName() + ":"
        + bootstrapNode.getPort());
        var stub = nodeAPIGrpc.newBlockingStub(channel);
        NodeProto nodeProto = KademliaUtils.nodeToNodeProto(this.localNode);

        NodesClose response = stub.findNode(nodeProto);
        List<Node> closestToAsk = new LinkedList<>();
        for (NodeProto neighProto : response.getNodesList()) {
            try {
                Node neigh = KademliaUtils.nodeProtoToNode(neighProto);
                closestToAsk.add(neigh);
                routingTable.insert(neigh);
            } catch (NoSuchAlgorithmException e) {
                logger.severe("Error: Could not find algorithm" + KademliaUtils.CRYPTO_ALGO);
                e.printStackTrace();
            } catch (InvalidKeySpecException e) {
                logger.severe("Error: Could not find hash" + KademliaUtils.HASH_ALGO);
                e.printStackTrace();
            }
            
        }
        
        logger.info("Received bootstrap findNode response, contacting closest nodes");
        // After the FIND_NODE call to the bootstrap node, we contact our K closests
        // nodes with a FIND_NODE(MY_NODE_ID).
        for (Node neigh : closestToAsk) {
            if (neigh.equals(this.getNode()))
            continue; // Skip self
            channel = ManagedChannelBuilder.forAddress(neigh.getName(), neigh.getPort())
            .usePlaintext()
            .build();
            stub = nodeAPIGrpc.newBlockingStub(channel);
            nodeProto = KademliaUtils.nodeToNodeProto(this.localNode);
            for (NodeProto neighProto : response.getNodesList()) {
                try {
                    neigh = KademliaUtils.nodeProtoToNode(neighProto);
                    routingTable.insert(neigh);
                } catch (NoSuchAlgorithmException e) {
                    logger.severe("Error: Could not find algorithm" + KademliaUtils.CRYPTO_ALGO);
                    e.printStackTrace();
                } catch (InvalidKeySpecException e) {
                    logger.severe("Error: Could not find hash" + KademliaUtils.HASH_ALGO);
                    e.printStackTrace();
                }
            }
        }

        logger.info("Bootstrap complete, obtain the following nodes" + routingTable);
    }
    
    public Node getNode() {
        return localNode;
    }
    
    public byte[] getPvtKeyBytes(){
        return this.getNode().getNodeID().getPvtKeyBytes();
    }

    // Returning the K nodes closest to the requested node.
    @Override
    public void findNode(NodeProto request, StreamObserver<NodesClose> responseObserver) {
        logger.info("Received FIND_NOME: " + request);
        var nodesClose = new ArrayList<NodeProto>();
        try {
            var n = KademliaUtils.nodeProtoToNode(request);
            routingTable.insert(n);
            
            var neighbors = routingTable.findClosest(n.getNodeID(), KademliaUtils.K);
            for (var neigh : neighbors) {
                logger.info("Neighbor(" + neigh + ")");
                nodesClose.add(KademliaUtils.nodeToNodeProto(neigh));
                // nodesCloseBuilder.addNodes(KademliaUtils.nodeToNodeProto(neigh));
            }
        } catch (NoSuchAlgorithmException e) {
            logger.severe("Error: Could not find algorithm" + KademliaUtils.CRYPTO_ALGO);
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            logger.severe("Error: Could not find hash" + KademliaUtils.HASH_ALGO);
            e.printStackTrace();
        }

        var nodesProtoClose = NodesClose.newBuilder()
                .addAllNodes(nodesClose)
                .build();

        responseObserver.onNext(nodesProtoClose);
        responseObserver.onCompleted();
    }

    /**
     * Simple ping message. No additional info is given.
     */
    @Override
    public void ping(NodeProto request, StreamObserver<NodeProto> responseObserver) {
        logger.info("Received Ping Request from:" + request);
        responseObserver.onNext(KademliaUtils.nodeToNodeProto(localNode));
        responseObserver.onCompleted();
    }


    /**
     * Store a value in the network. Does not check if the key really belongs to it.
     */
    @Override
    public void store(StoreRequest request, StreamObserver<StoreResponse> responseObserver) {
        logger.info("Received Store Request from:" + request);
        StoreRequest storeRequest = StoreRequest.newBuilder().build();
        // Retrieve timestamp and value from the request.
        var key = new BigInteger(1, request.getKey().toByteArray());
        var timestamp = request.getTimestamp();
        var value = storeRequest.getValue().toByteArray();
        var kadValue = new KadStorageValue(new BigInteger(1, value), timestamp);

        boolean result = storageManager.put(key, kadValue);
        // Return the response.
        StoreResponse storeResponse = StoreResponse.newBuilder()
                .setTimestamp(timestamp)
                .setKey(ByteString.copyFrom(key.toByteArray()))
                .setSuccess(result)
                .build();
        responseObserver.onNext(storeResponse);

        responseObserver.onCompleted();
    }

    /**
     * Find a value in the network. Does not check if the key really belongs to it.
     */
    @Override
    public void findValue(FindRequest request, StreamObserver<FindResponse> responseObserver) {
        var key = new BigInteger(1, request.getKey().toByteArray());
        var value = storageManager.get(key);
        logger.info("FIND_VALUE: " + key + " " + value);
        FindResponse findResponse;
        if (value != null) {
            findResponse = FindResponse.newBuilder()
                    .setValue(ByteString.copyFrom(value.getValueBytes()))
                    .setTimestamp(value.getTimestamp())
                    .setSuccess(true)
                    .build();
        } else {
            findResponse = FindResponse.newBuilder()
                    .setSuccess(false)
                    .build();

        }
        responseObserver.onNext(findResponse);
        responseObserver.onCompleted();
    }

    // --------- Public API --------- //
    public boolean put(BigInteger key, BigInteger value) {
        throw new UnsupportedOperationException("Function not yet implemented");
    }
    
    public boolean put(byte[] key, byte[] value) {
        throw new UnsupportedOperationException("Function not yet implemented");
    }

    public boolean get(BigInteger key, BigInteger value) {
        throw new UnsupportedOperationException("Function not yet implemented");
    }

    public boolean get(byte[] key, byte[] value) {
        throw new UnsupportedOperationException("Function not yet implemented");
    }

}