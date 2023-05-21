package s_kademlia;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import com.google.protobuf.ByteString;

import generated.nodeAPIGrpc;
import generated.NodeAPI.*;
import generated.nodeAPIGrpc.nodeAPIImplBase;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import s_kademlia.node.KademliaID;
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
        routingTable.insert(localNode);
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

        FindNodeRequest findNodeRequest = FindNodeRequest.newBuilder()
                .setNode(nodeProto)
                .setKey(ByteString.copyFrom(this.getHash()))
                .build();

        NodesClose response = stub.findNode(findNodeRequest);
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

        logger.info("Bootstrap complete, routing table:\n" + routingTable);
    }

    public Node getNode() {
        return localNode;
    }

    public PrivateKey getPvtKey() {
        return this.getNode().getNodeID().getPrvKey();
    }

    public PublicKey getPubKey() {
        return this.getNode().getNodeID().getPubKey();
    }

    public byte[] getHash() {
        return this.getNode().getNodeID().hashBytes();
    }

    // Returning the K nodes closest to the requested key.
    @Override
    public void findNode(FindNodeRequest request, StreamObserver<NodesClose> responseObserver) {
        logger.info("Received FIND_NOME: " + request);
        var nodesClose = new ArrayList<NodeProto>();
        try {
            // Updating routing table with the node that made the request.
            var n = KademliaUtils.nodeProtoToNode(request.getNode());
            routingTable.insert(n);

            // Find the K closest nodes to the requested key.
            var kadID = new KademliaID(request.getKey().toByteArray());
            var neighbors = routingTable.findClosest(kadID, KademliaUtils.K);
            for (var neigh : neighbors) {
                logger.info("Neighbor(" + neigh + ")");
                nodesClose.add(KademliaUtils.nodeToNodeProto(neigh));
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

    /**
     * Execute a FIND_NODE rpc call to a node and updat routing table.
     * 
     * @param kadID
     * @param node
     * @return
     * @throws StatusRuntimeException
     */
    private NodesClose runFindNode(KademliaID kadID, Node node) throws StatusRuntimeException {
        var stub = nodeAPIGrpc.newBlockingStub(ManagedChannelBuilder.forAddress(node.getName(), node.getPort())
                .usePlaintext()
                .build()).withDeadlineAfter(KademliaUtils.RPC_CALL_TIMEOUT, TimeUnit.MILLISECONDS);

        FindNodeRequest findNodeRequest = FindNodeRequest.newBuilder()
                .setNode(KademliaUtils.nodeToNodeProto(this.localNode))
                .setKey(ByteString.copyFrom(kadID.hashBytes()))
                .build();

        NodesClose response = stub.findNode(findNodeRequest);

        for (NodeProto n : response.getNodesList()) {
            try {
                routingTable.insert(KademliaUtils.nodeProtoToNode(n));
            } catch (NoSuchAlgorithmException e) {
                logger.severe("Error: Could not find algorithm" + KademliaUtils.CRYPTO_ALGO);
                e.printStackTrace();
            } catch (InvalidKeySpecException e) {
                logger.severe("Error: Could not find hash" + KademliaUtils.HASH_ALGO);
                e.printStackTrace();
            }
        }
        return response;
    }

    /**
     * Execute a STORE rpc call to a node.
     */
    private boolean runStore(Node node, KademliaID kadID, KadStorageValue value)
            throws StatusRuntimeException {
        var stub = nodeAPIGrpc.newBlockingStub(ManagedChannelBuilder.forAddress(node.getName(), node.getPort())
                .usePlaintext()
                .build()).withDeadlineAfter(KademliaUtils.RPC_CALL_TIMEOUT, TimeUnit.MILLISECONDS);

        StoreRequest storeRequest = StoreRequest.newBuilder()
                .setKey(ByteString.copyFrom(kadID.hashBytes()))
                .setValue(ByteString.copyFrom(value.getValueBytes()))
                .setTimestamp(value.getTimestamp())
                .build();

        StoreResponse response = stub.store(storeRequest);
        return response.getSuccess();
    }

    /**
     * Execute a GET rpc call to a node.
     * 
     * @param node
     * @param Key
     * @return
     */
    private KadStorageValue runGet(Node node, KademliaID Key) {
        var stub = nodeAPIGrpc.newBlockingStub(ManagedChannelBuilder.forAddress(node.getName(), node.getPort())
                .usePlaintext()
                .build()).withDeadlineAfter(KademliaUtils.RPC_CALL_TIMEOUT, TimeUnit.MILLISECONDS);

        FindRequest findRequest = FindRequest.newBuilder()
                .setKey(ByteString.copyFrom(Key.hashBytes()))
                .build();

        FindResponse response = stub.findValue(findRequest);
        if (response.getSuccess()) {
            return new KadStorageValue(new BigInteger(1, response.getValue().toByteArray()), response.getTimestamp());
        } else {
            return null;
        }
    }

    // --------- Public API --------- //
    /**
     * Put a value in the network.
     * To find out what node is responsible for the key we do the following
     * procedure:
     * - Find the K closest nodes to the key.
     * - Send a FIND_NODE(key) request to each of them.
     * - Check if the routing table has a node closer to the key than my previous
     * closest node.
     * - Store in the closest node if one is not found, repeat otherwise.
     * 
     * @param key
     * @param value
     * @return
     */
    public boolean put(BigInteger key, KadStorageValue value) {
        // These searches never return null since we add the local node to the routing.
        var kadID = new KademliaID(key);
        var closestNodeBefore = routingTable.findClosest(kadID, 1).get(0);
        try {
            Node closestNodeAfter = KademliaUtils.nodeProtoToNode(runFindNode(kadID, closestNodeBefore).getNodes(0));
            // If the closest node is still the same, then we can send him the value.
            if (closestNodeBefore.equals(closestNodeAfter)) {
                return runStore(closestNodeBefore, kadID, value);
            } else {
                return put(key, value);
            }
        } catch (NoSuchAlgorithmException e) {
            logger.severe("Error: Could not find algorithm" + KademliaUtils.CRYPTO_ALGO);
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            logger.severe("Error: Could not find hash" + KademliaUtils.HASH_ALGO);
            e.printStackTrace();
        } catch (StatusRuntimeException e) {
            logger.info("The closest node is not responding, skipping");
            e.printStackTrace();
        }
        // The ALGO and hash problem will never happen, so the only way to get here is
        // if the request timeouts
        routingTable.penaltyContact(closestNodeBefore);
        return put(key, value); // Try Again.

    }

    /**
     * Executes FIND_NODE until closest node converges, then executes a FIND_VALUE.
     * 
     * @param key
     * @param priority_index
     * @return
     */
    public KadStorageValue get(BigInteger key, int priority_index) {
        // These searches never return null since we add the local node to the routing.
        var kadID = new KademliaID(key);
        var closestNodeBefore = routingTable.findClosest(kadID, 1).get(priority_index);
        try {
            Node closestNodeAfter = KademliaUtils.nodeProtoToNode(runFindNode(kadID, closestNodeBefore).getNodes(0));
            // If the closest node is still the same, then we can get the value.
            if (closestNodeBefore.equals(closestNodeAfter)) {
                var value = runGet(closestNodeBefore, kadID);
                if (value == null) { // Node did not have the key, try the next closest node.
                    return get(key, priority_index + 1);
                } else {
                    return value;
                }
            }
            // Found a closer node
            else {
                return get(key, 0);
            }
        } catch (NoSuchAlgorithmException e) {
            logger.severe("Error: Could not find algorithm" + KademliaUtils.CRYPTO_ALGO);
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            logger.severe("Error: Could not find hash" + KademliaUtils.HASH_ALGO);
            e.printStackTrace();
        } catch (StatusRuntimeException e) {
            logger.info("The closest node is not responding, skipping");
            e.printStackTrace();
        }
        // The ALGO and hash problem will never happen, so the only way to get here is
        // if the request timeouts
        routingTable.penaltyContact(closestNodeBefore);
        return get(key, 0); // Try Again.
    }


    public static put(byte[] key, KadStorageValue value){
        this.put(new BigInteger(1, key), value);
    }

    // --------- RPC Client API --------- //
    @Override
    public void get(GetRequest request, StreamObserver<GetResponse> responseObserver) {
        var kadID = new BigInteger(1, request.getKey().toByteArray());
        var value = this.get(kadID, 0);
        responseObserver.onNext(GetResponse.newBuilder()
                .setTimestamp(value.getTimestamp())
                .setValue(ByteString.copyFrom(value.getValueBytes()))
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void put(PutRequest request, StreamObserver<PutResponse> responseObserver) {
        var kadID = new BigInteger(1, request.getKey().toByteArray());
        var value = new BigInteger(1, request.getValue().toByteArray());
        var timestamp = request.getTimestamp();

        var storageEntry = new KadStorageValue(value, timestamp);
        var status = put(kadID, storageEntry);
        responseObserver.onNext(PutResponse.newBuilder()
                .setSuccess(status)
                .build());
        responseObserver.onCompleted();
    }

}