package s_kademlia.node;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

import com.google.protobuf.ByteString;

import java.security.PrivateKey;

import s_kademlia.utils.CryptoHash;

/**
 * Represents a Kademlia Node ID. Also stores the public and private key used
 * for the node ID.
 */
public class KademliaID implements Comparable<KademliaID> {
    public final transient static int ID_LENGTH = 256; // SHA-256
    private static final int zeroBits = 20; // How many bytes of the key need to be zero (Static Puzzle)
    private PublicKey pubKey; // Public Key
    private PrivateKey prvKey; // Private Key
    private BigInteger hash; // Hash of the Public Key, Used as node ID

    /**
     * Construct the NodeID from a public cryptography pair. NodeID will become the
     * SHA-256 of the Public Key. Since the private key is given it is possible to
     * sign messages.
     * 
     * @param pubKey Public Key
     * @param prvKey Private Key
     * @throws NoSuchAlgorithmException
     */
    public KademliaID(KeyPair keyPair) throws NoSuchAlgorithmException {
        pubKey = keyPair.getPublic();
        prvKey = keyPair.getPrivate();
        hash = CryptoHash.toSha256(this.getPubKeyBytes());
    }

    /**
     * Construct the NodeID from a public key. NodeID will become the SHA-256 of the
     * Public Key. Cannot sign messages since the private key it not provided.
     * 
     * @param pubKey Public Key
     * @param prvKey Private Key
     * @throws NoSuchAlgorithmException
     */
    public KademliaID(PublicKey pubKey) throws NoSuchAlgorithmException {
        this.pubKey = pubKey;
        hash = CryptoHash.toSha256(this.getPubKeyBytes());
    }


    public PublicKey getPubKey() {
        return pubKey;
    }

    public PrivateKey getPrvKey() {
        return prvKey;
    }

    /**
     * @return Returns the public key used
     */
    public byte[] getPubKeyBytes() {
        return this.pubKey.getEncoded();
    }

    public byte[] getPvtKeyBytes() {
        return this.prvKey.getEncoded();
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

    public byte[] hashBytes() {
        return hash.toByteArray();
    }

    public ByteString hashByteStr() {
        return ByteString.copyFrom(this.hashBytes());
    }

    public ByteString getPubKeyByteStr() {
        return ByteString.copyFrom(this.getPubKeyBytes());
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
    public byte[] xor(KademliaID nid) {
        byte[] result = new byte[ID_LENGTH / 8];
        byte[] nidBytes = nid.hashBytes();
        byte[] keyBytes = this.hashBytes();
        for (int i = 0; i < ID_LENGTH / 8; i++) {
            result[i] = (byte) (keyBytes[i] ^ nidBytes[i]);
        }

        return result;
    }

    /**
     * Counts the number of leading 0's in this NodeId
     *
     * @return Integer The number of leading 0's
     */
    public static int getFirstSetBitIndex(byte[] hashBytes) {
        int prefixLength = 0;

        for (byte b : hashBytes) {
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
        return ID_LENGTH - KademliaID.getFirstSetBitIndex(this.xor(to));
    }

    @Override
    public String toString() {
        return this.hash().toString(16);
    }

    @Override
    public int compareTo(KademliaID o) {
        return this.hash().compareTo(o.hash());
    }
}
