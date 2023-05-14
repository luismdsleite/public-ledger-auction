package s_kademlia.node;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import generated.NodeAPI.Empty;
import generated.NodeAPI.NodeInfo;
import generated.NodeAPI.NodesClose;
import generated.nodeAPIGrpc.nodeAPIImplBase;
import io.grpc.stub.StreamObserver;

/**
 * A Node in the S/Kademlia network - Contains basic node network information.
 */

public class Node implements Comparable<Node> {
    private final KademliaID nodeID;
    private final String name;
    private final int port;
    
    public Node(KademliaID nodeID, String name, int port) {
        this.nodeID = nodeID;
        this.name = name; // Access Point
        this.port = port; // Access Point
    }

    /**
     * @throws NoSuchAlgorithmException
     */
    public Node(String name, int port) throws NoSuchAlgorithmException {

        // Generate a key pair
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        keyPair.getPrivate();
        // Create a NodeID with the public and private key
        nodeID = new KademliaID(keyPair);
        this.name = name;
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public int getPort() {
        return port;
    }

    public KademliaID getNodeID() {
        return nodeID;
    }

    @Override
    public int compareTo(Node o) {
        return this.getNodeID().compareTo(o.getNodeID());
    }

}
