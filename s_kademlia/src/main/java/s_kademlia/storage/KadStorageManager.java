package s_kademlia.storage;

import java.util.logging.Logger;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import s_kademlia.utils.CryptoHash;
import s_kademlia.utils.KademliaUtils;

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
        logger.info("put called with key:" + key + " value:" + value);
        // If the key is already present, check the timestamp and update only if newer.
        if (storage.containsKey(key)) {
            var prevEntry = storage.get(key);
            if (prevEntry.getTimestamp() < value.getTimestamp()) {
                storage.put(key, value);
                logger.info("Updated entry:" + value);
                return true;
            } else {
                return false;
            }
        }
        // If the key was not previously present just add it.
        storage.put(key, value);
        logger.info("Inserted new entry:" + value);
        return true;
    }

    public synchronized boolean put(BigInteger value, long timestamp) {
        var kadValue = new KadStorageValue(value, timestamp);
        try {
            var key = CryptoHash.toSha256(kadValue.getValue().toByteArray());
            return this.put(key, kadValue);

        } catch (NoSuchAlgorithmException e) {
            logger.severe("Error: Could not find algorithm" + KademliaUtils.CRYPTO_ALGO);
            e.printStackTrace();
        }
        return false;
    }

    public synchronized boolean put(BigInteger key, BigInteger value, long timestamp) {
        var kadValue = new KadStorageValue(value, timestamp);
        return this.put(key, kadValue);
    }

    public boolean put(byte[] value, long timestamp) {
        return this.put(new BigInteger(1, value), timestamp);
    }

    public KadStorageValue get(BigInteger key) {
        return storage.get(key);
    }
}
