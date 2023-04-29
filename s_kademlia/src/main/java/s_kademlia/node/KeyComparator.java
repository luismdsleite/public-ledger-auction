package s_kademlia.node;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.Comparator;

/**
 * Comparator based on the distance to a single node
 */
public class KeyComparator implements Comparator<Node> {

    private final BigInteger nodeID;

    /**
     * @param nodeID ID used to order all the distances
     */
    public KeyComparator(KademliaID nodeID) {
        this.nodeID = nodeID.hash();
    }

    /**
     * Compare two objects which must both be of type <code>Node</code>
     * and determine which is closest to the identifier specified in the
     * constructor.
     *
     * @param n0 Node 1 to compare distance from the key
     * @param n1 Node 2 to compare distance from the key
     */
    @Override
    public int compare(Node n0, Node n1) {
        BigInteger nID0 = n0.getNodeID().hash();
        BigInteger nID1 = n1.getNodeID().hash();
        BigInteger dist0 = nID0.xor(nodeID);
        BigInteger dist1 = nID1.xor(nodeID);
        return dist0.abs().compareTo(dist1.abs());
    }

}
