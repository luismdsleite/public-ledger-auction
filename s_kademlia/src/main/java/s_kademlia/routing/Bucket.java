package s_kademlia.routing;

import java.util.TreeSet;

import s_kademlia.node.Node;

/**
 * Represents an S/Kademlia bucket 
 */
public class Bucket {
    private TreeSet<Contact> contacts;
    private final int K_Nodes; // How many nodes each bucket can hold.

    public Bucket(int k) {
        this.contacts = new TreeSet<>();
        this.K_Nodes = k;
    }
    
    /**
     * <p> Adds a Node to the bucket.
     * <p> If the node was already in the bucket then we update its timestamp.
     * @param contact
     * @return
     */
    public boolean addNode(Node node) {
        Contact contact = new Contact(node);
        // If we already had a contact then remove it, update its timestamp and add it again.
        if (contacts.contains(contact)){
            contacts.remove(contact);
            contact.updateLastSeen();
            contacts.add(contact);
            return true;
        }
        // If the bucket is not full simply add the contact to the contacts treeset.
        if (contacts.size() < K_Nodes) {
            contacts.add(contact);
            return true;
        }
        // If the bucket is full, remove the oldest contact and insert the new one.
        else{
            Contact OldestSeen = contacts.pollFirst();
            contacts.remove(OldestSeen);
            contacts.add(contact);
            return true;
        }
        
    }

    public boolean removeNode(Node node) {
        Contact contact = new Contact(node);
        return contacts.remove(contact);
    }

    public boolean isFull() {
        return contacts.size() == K_Nodes;
    }

    public boolean isEmpty() {
        return contacts.isEmpty();
    }

    public int size() {
        return contacts.size();
    }
}