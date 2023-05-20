package s_kademlia.routing;

import s_kademlia.node.Node;

/**
 * Log used in a bucket. Stores the last time a node was seen.
 * 
 * <p>
 * Each time a node is shown to be alive execute {@link #getLastSeen()}.
 * 
 */
public class Contact implements Comparable<Contact> {
    private final Node n;
    private long lastSeen;

    public Contact(Node n) {
        this.n = n;
        this.lastSeen = System.currentTimeMillis() / 1000L;
    }

    /**
     * @return node the contact refers to.
     */
    public Node getNode() {
        return n;
    }

    /**
     * @return the last time a node was seen.
     */
    public long getLastSeen() {
        return lastSeen;
    }

    /**
     * Updates the time a node was seen.
     */
    public void updateLastSeen() {
        this.lastSeen = System.currentTimeMillis() / 1000L;
    }

    /**
     * <p> Orders nodes based on the last time they were seen, newest == bigger.
     * <p> Tie-breaker = NodeID. 
     * <p> Returns 0 if both contacts refer to the same node.
     */
    @Override
    public int compareTo(Contact o) {
        // If the both contact refer to the same node then they are the same.
        if (this.getNode().equals(o.getNode()))
            return 0;
        // Most recent seen node is bigger.
        return this.getLastSeen() > o.getLastSeen() ? 1 : -1;
    }

    @Override
    public String toString() {
        return n.toString();
    }
}
