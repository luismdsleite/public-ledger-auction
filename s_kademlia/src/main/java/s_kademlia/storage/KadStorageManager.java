package s_kademlia.storage;

import java.util.logging.Logger;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class KadStorageManager {
    private final Map<BigInteger, KadStorageValue> storage;
    Logger logger = Logger.getLogger(KadStorageManager.class.getName());

    public KadStorageManager() {
        this.storage = new HashMap<BigInteger, KadStorageValue>();
    }

    /**
     * Put a value in the storage. All other put methods call this one.
     * 
     * @param key
     * @param value
     * @return
     */
    public synchronized boolean put(BigInteger key, KadStorageValue value) {

        // If the key is already present, check the timestamp and update only if newer.
        if (storage.containsKey(key)) {
            var prevEntry = storage.get(key);
            if (prevEntry.getTimestamp() < value.getTimestamp()) {
                storage.put(key, value);
                logger.info("Updated entry " + key.toString(16) + " with " + value);
                return true;
            } else {
                return false;
            }
        }
        // If the key was not previously present just add it.
        storage.put(key, value);
        logger.info("Inserted new entry " + key.toString(16) + " with " + value);
        return true;
    }

    public synchronized boolean put(BigInteger key, BigInteger value, long timestamp) {
        var kadValue = new KadStorageValue(value, timestamp);
        return this.put(key, kadValue);
    }

    public KadStorageValue get(BigInteger key) {
        return storage.get(key);
    }

    @Override
    public String toString() {
        return storage.toString();
    }
}
