package s_kademlia.storage;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

import s_kademlia.utils.CryptoHash;
import s_kademlia.utils.KademliaUtils;

public class KadStorageValue {
    private final BigInteger value;
    private final long timestamp;
    Logger logger = Logger.getLogger(KadStorageValue.class.getName());

    public KadStorageValue(BigInteger value, long timestamp) {
        this.value = value;
        this.timestamp = timestamp;
    }

    public KadStorageValue(BigInteger value) {
        this(value, System.currentTimeMillis() / 1000L);
    }

    public BigInteger getValue() {
        return value;
    }

    public byte[] getValueBytes() {
        return value.toByteArray();
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        try {
            return "KadStorageEntry{" +
                    "key='" + CryptoHash.toSha256(value.toByteArray()).toString(16) +
                    ", timestamp=" + timestamp +
                    '}';
        } catch (NoSuchAlgorithmException e) {
            logger.severe("Error: Could not find algorithm" + KademliaUtils.CRYPTO_ALGO);
            e.printStackTrace();
            return "";
        }
    }
}
