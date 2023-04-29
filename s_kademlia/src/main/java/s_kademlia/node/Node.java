package s_kademlia.node;

import java.net.InetAddress;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

/**
 * A Node in the S/Kademlia network - Contains basic node network information.
 */

public class Node {
    private final KademliaID nodeID;
    private InetAddress inetAddress;
    private int port;


    public Node(KademliaID nodeID, InetAddress inetAddress, int port) {
        this.nodeID = nodeID;
        this.inetAddress = inetAddress;
        this.port = port;
    }

    /**
     * @param inetAddress
     * @param port
     * @throws NoSuchAlgorithmException
     */
    public Node(InetAddress inetAddress, int port) throws NoSuchAlgorithmException {
        
        // Generate a key pair
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        
        // Create a NodeID with the public and private key
        nodeID = new KademliaID(keyPair.getPublic().getEncoded());
        this.inetAddress = inetAddress;
        this.port = port;
    }

    public KademliaID getNodeID() {
        return nodeID;
    }

    public InetAddress getInetAddress() {
        return inetAddress;
    }
    public void setInetAddress(InetAddress inetAddress) {
        this.inetAddress = inetAddress;
    }
    public int getPort() {
        return port;
    }
    public void setPort(int port) {
        this.port = port;
    }

    
}
