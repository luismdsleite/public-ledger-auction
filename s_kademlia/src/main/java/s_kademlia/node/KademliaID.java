package s_kademlia.node;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;

import s_kademlia.utils.CryptoHash;

/**
 * Represents a Kademlia Node ID. Also stores the public and private key used
 * for the node ID.
 */
public class KademliaID {
    private final transient int ID_LENGTH = 256; // SHA-256
    private static final int zeroBits = 20; // How many bytes of the key need to be zero (Static Puzzle)
    private byte[] pubKeyBytes; // Bytes of the public key
    private byte[] prvKeyBytes; // Bytes of the private key
    private BigInteger hash; // Hash of the Public Key, Used as node ID

    /**
     * Construct the NodeID from a public cryptography pair. NodeID will become the
     * SHA-256 of the Public Key. Since the private key is given it is possible to
     * sign messages.
     * 
     * @param pubKeyBytes Public Key
     * @param prvKeyBytes Private Key
     * @throws NoSuchAlgorithmException
     */
    public KademliaID(KeyPair keyPair) throws NoSuchAlgorithmException {
        pubKeyBytes = keyPair.getPublic().getEncoded().clone();
        prvKeyBytes = keyPair.getPrivate().getEncoded().clone();
        hash = CryptoHash.toSha256(this.getPubKeyBytes());
    }

    /**
     * Construct the NodeID from a public key. NodeID will become the SHA-256 of the
     * Public Key. Cannot sign messages since the private key it not provided.
     * 
     * @param pubKeyBytes Public Key
     * @param prvKeyBytes Private Key
     */
    public KademliaID(byte[] pubKeyBytes) {
        pubKeyBytes = pubKeyBytes.clone();
    }

    /**
     * @return Returns the public key used
     */
    public byte[] getPubKeyBytes() {
        return this.pubKeyBytes;
    }

    /**
     * @return The BigInteger representation of the public key
     */
    public BigInteger getPubKeyInt() {
        return new BigInteger(1, this.getPubKeyBytes());
    }

    public BigInteger hash() {
        return hash;
    }

    /**
     * Compares a NodeId to this NodeId
     *
     * @param o The NodeId to compare to this NodeId
     *
     * @return boolean Whether the 2 NodeIds are equal
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof KademliaID) {
            KademliaID nid = (KademliaID) o;
            return this.hashCode() == nid.hashCode();
        }
        return false;
    }

    /**
     * Checks the distance between this and another NodeId
     *
     * @param nid
     *
     * @return The distance of this NodeId from the given NodeId
     */
    public KademliaID xor(KademliaID nid) {
        byte[] result = new byte[ID_LENGTH];
        byte[] nidBytes = nid.getPubKeyBytes();

        for (int i = 0; i < ID_LENGTH; i++) {
            result[i] = (byte) (this.pubKeyBytes[i] ^ nidBytes[i]);
        }

        KademliaID resNid = new KademliaID(result);

        return resNid;
    }

    /**
     * Counts the number of leading 0's in this NodeId
     *
     * @return Integer The number of leading 0's
     */
    public int getFirstSetBitIndex() {
        int prefixLength = 0;

        for (byte b : this.pubKeyBytes) {
            if (b == 0) {
                prefixLength += 8;
            } else {
                /* If the byte is not 0, we need to count how many MSBs are 0 */
                int count = 0;
                for (int i = 7; i >= 0; i--) {
                    boolean a = (b & (1 << i)) == 0;
                    if (a) {
                        count++;
                    } else {
                        break; // Reset the count if we encounter a non-zero number
                    }
                }

                /* Add the count of MSB 0s to the prefix length */
                prefixLength += count;

                /* Break here since we've now covered the MSB 0s */
                break;
            }
        }
        return prefixLength;
    }

    /**
     * Gets the distance from this NodeId to another NodeId
     *
     * @param to
     *
     * @return Integer The distance
     */
    public int getDistance(KademliaID to) {
        /**
         * Compute the xor of this and to
         * Get the index i of the first set bit of the xor returned NodeId
         * The distance between them is ID_LENGTH - i
         */
        return ID_LENGTH - this.xor(to).getFirstSetBitIndex();
    }

    @Override
    public String toString() {
        return this.hash().toString(16);
    }
}
