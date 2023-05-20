package s_kademlia.routing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import s_kademlia.KademliaNode;
import s_kademlia.node.KademliaID;
import s_kademlia.node.KeyComparator;
import s_kademlia.node.Node;

public class RoutingTable {
    public static final Logger logger = Logger.getLogger(RoutingTable.class.getName());

    private final Node myNode;
    private transient Bucket[] buckets;

    public RoutingTable(Node myNode) {
        this.myNode = myNode;
        this.initializeBuckets();
        logger.info("Routing table created for " + myNode);

    }

    /**
     * Constructor used only for the Bootstrap Nodes. Buckets in this routing table dont have a size limit.
     * @param bootstrapNode
     */
    public RoutingTable(KademliaNode bootstrapNode) {
        myNode = bootstrapNode.getNode();
        this.bootstrapBuckets();
        logger.info("Routing table created for " + myNode);
    }

    private void initializeBuckets() {
        // Create a bucket per bit present in the hash.
        this.buckets = new Bucket[KademliaID.ID_LENGTH];
        for (int i = 0; i < buckets.length; i++) {
            // 256 nodes are stored in the closest bucket, 255 in the second closest, ...
            buckets[i] = new Bucket(i);
        }
    }

    /**
     * Used only by the BootStrap nodes. This makes each bucket with an infinite
     * size.
     */
    private void bootstrapBuckets() {
        // Create a bucket per bit present in the hash.
        this.buckets = new Bucket[KademliaID.ID_LENGTH];
        for (int i = 0; i < buckets.length; i++) {
            // No size limit.
            buckets[i] = new Bucket(-1);
        }

    }

    private synchronized final void insert(Contact c) {
        this.buckets[this.getBucketIndex(c.getNode().getNodeID())].insert(c);
    }

    private final int getBucketIndex(KademliaID nid) {
        int bIndex = this.myNode.getNodeID().getDistance(nid) - 1;
        // If i use my own nodeID the index will return -1, this if handles that case.
        if (bIndex < 0)
            bIndex = 0;
        return bIndex;
    }

    /**
     * Adds a node to the routing table based on how far it is from the LocalNode.
     *
     * @param n The node to add
     */
    public synchronized final void insert(Node n) {
        System.out.println("Inserted Node " + n + " in " + this.getBucketIndex(n.getNodeID()));
        
        if(n.equals(myNode)){
            logger.info( "Error: Trying to insert own node into bucket");
            return;
        }
        
        this.buckets[this.getBucketIndex(n.getNodeID())].insert(n);
        logger.info("Routing table added: " +  n);
        // System.out.println(this.buckets[0]);
    }

    /**
     * Find the closest set of contacts to a given NodeId
     *
     * @param target           The NodeId to find contacts close to
     * @param numNodesRequired The number of contacts to find
     *
     * @return List A List of contacts closest to target
     */
    public synchronized final List<Node> findClosest(KademliaID target, int numNodesRequired) {
        TreeSet<Node> sortedSet = new TreeSet<>(new KeyComparator(target));
        sortedSet.addAll(this.getAllNodes());

        List<Node> closest = new ArrayList<>(numNodesRequired);

        /* Now we have the sorted set, lets get the top numRequired */
        int count = 0;
        for (Node n : sortedSet) {
            if(n.getNodeID().equals(target))
                continue;
            closest.add(n);
            if (++count == numNodesRequired) {
                break;
            }
        }
        return closest;
    }

    /**
     * @return List A List of all Nodes in this RoutingTable
     */
    public synchronized final List<Node> getAllNodes() {
        List<Node> nodes = new ArrayList<>();

        for (Bucket b : this.buckets) {
            for (Contact c : b.getContacts()) {
                nodes.add(c.getNode());
            }
        }

        return nodes;
    }

    @Override
    public String toString() {
        return Arrays.toString(buckets);
    }

}
