package s_kademlia.routing;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
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
     * <p>
     * Inserts a contact into the contacts list.
     * <p>
     * If the Contact was already present then we put it on the front of the bucket.
     * 
     * @param c Contact to add
     */
    public synchronized boolean insert(Contact c) {
        if (this.contacts.contains(c)) {
            /**
             * If the contact is already in the bucket, then update it and put it at the
             * head of the list.
             */
            var tmp = this.removeFromContacts(c.getNode());
            tmp.updateLastSeen();
            return this.contacts.add(tmp);
        }
        // If the bucket is not full simply add the contact to the contacts treeset.
        else if (contacts.size() < K_Nodes) {
            return contacts.add(c);
        }
        // If the bucket is full, remove the oldest contact and insert the new one.
        else {
            Contact OldestSeen = contacts.pollFirst();
            contacts.remove(OldestSeen);
            return contacts.add(c);
        }
    }

    public synchronized boolean insert(Node n) {
        return this.insert(new Contact(n));
    }

    /**
     * Removes a node from the bucket.
     * This cannot be named remove since it enters in conflict
     * 
     * @param n Node to remove
     * @return True if the contact was removed, false if the node is not present in
     *         the bucket.
     */
    public synchronized Contact removeFromContacts(Node n) {
        Contact c = getFromContacts(n);
        this.contacts.remove(c);
        return c;
    }

    public synchronized Contact getFromContacts(Node n) {
        for (Contact c : this.contacts) {
            if (c.getNode().equals(n)) {
                return c;
            }
        }

        /* This contact does not exist */
        throw new NoSuchElementException("The contact does not exist in the contacts list.");

    }

    /**
     * Return all the contacts stored in the bucket.
     * 
     * @return
     */
    public synchronized List<Contact> getContacts() {
        final ArrayList<Contact> ret = new ArrayList<>();

        /* If we have no contacts, return the blank arraylist */
        if (this.contacts.isEmpty()) {
            return ret;
        }

        /* We have contacts, lets copy put them into the arraylist and return */
        for (Contact c : this.contacts) {
            ret.add(c);
        }

        return ret;
    }

    public boolean remove(Node node) {
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

    @Override
    public String toString() {
        return contacts.toString();
    }
}