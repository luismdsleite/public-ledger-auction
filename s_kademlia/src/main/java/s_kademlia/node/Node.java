package s_kademlia.node;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;

import s_kademlia.utils.CryptoHash;
import s_kademlia.utils.KademliaUtils;

/**
 * A Node in the S/Kademlia network - Contains basic node network information.
 * Not responsible for storing data!
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

    public Node(byte[] pubKeyBytes, String name, int port) throws NoSuchAlgorithmException, InvalidKeySpecException {
        this.nodeID = new KademliaID(CryptoHash.bytesToPubKey(pubKeyBytes));
        this.name = name; // Access Point
        this.port = port; // Access Point
    }

    /**
     * Only used to create a local node! Will generate new a pair of keys used for
     * public cryptography.
     * 
     * @throws NoSuchAlgorithmException
     */
    public Node(String name, int port) throws NoSuchAlgorithmException {
        // Generate a key pair
        KeyPair keyPair = CryptoHash.genKeyPair();
        keyPair.getPrivate();
        // Create a NodeID with the public and private key
        nodeID = new KademliaID(keyPair);
        this.name = name;
        this.port = port;
    }

    public Node(String name, int port, PublicKey pubKey, PrivateKey prvKey) {
        try {
            this.nodeID = new KademliaID(pubKey, prvKey);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        this.name = name;
        this.port = port;
    }

    @Override
    public String toString() {
        return this.name + ":" + this.port + "(" + this.nodeID + ")";
    }

    public String getName() {
        return this.name;
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

    @Override
    public boolean equals(Object o) {
        if (o instanceof Node) {
            return this.compareTo(((Node) o)) == 0;
        }
        return false;
    }

}
