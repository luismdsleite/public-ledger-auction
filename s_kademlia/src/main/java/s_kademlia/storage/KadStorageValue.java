package s_kademlia.storage;

import java.math.BigInteger;
import java.util.logging.Logger;

public class KadStorageValue {
    private final BigInteger value;
    private final long timestamp; // Unix timestamp in seconds
    Logger logger = Logger.getLogger(KadStorageValue.class.getName());

    public KadStorageValue(BigInteger value, long timestamp) {
        this.value = value;
        this.timestamp = timestamp;
    }

    public KadStorageValue(byte[] value, long timestamp) {
        this(new BigInteger(1, value), timestamp);
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
        return "KadStorageEntry{" +
                "Value='" + value.toString(16) +
                ", timestamp=" + timestamp +
                '}';
    }
}
