package s_kademlia.routing;

import s_kademlia.node.KademliaID;
import s_kademlia.node.Node;

public class RoutingTable {
    private final Node myNode;
    private transient Bucket[] buckets;

    public RoutingTable(Node myNode) {
        this.myNode = myNode;
        this.initializeBuckets();
        
    }

    private void initializeBuckets() {
        // Create a bucket per bit present in the hash.
        this.buckets = new Bucket[KademliaID.ID_LENGTH]; 
        for (int i = 0; i < buckets.length; i++) {
            // 256 nodes are stored in the closest bucket, 255 in the second closest, ...
            buckets[i] = new Bucket(i); 
        }
    }

    private synchronized final void insert(Contact c){
        this.buckets[this.getBucketIndex(c.getNode().getNodeID())].insert(c);
    }

    private final int getBucketIndex(KademliaID nid){
        int bIndex = this.myNode.getNodeID().getDistance(nid) - 1;
        // If i use my own nodeID the index will return -1, this if handles that case.
        if (bIndex < 0) bIndex = 0; 
        return bIndex;
    }

    /**
     * Adds a node to the routing table based on how far it is from the LocalNode.
     *
     * @param n The node to add
     */
    public synchronized final void insert(Node n)
    {
        this.buckets[this.getBucketIndex(n.getNodeID())].insert(n);
    }
}
