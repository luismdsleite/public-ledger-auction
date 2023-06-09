package ledger.consensus;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import ledger.blockchain.Block;
import ledger.transaction.Transaction;
import ledger.validation.BlockValidator;

public class ProofOfWork {

    // pow
    public static String calculateHashPoW(Block block) {
        String dataToHash = Integer.toString(block.getIndex()) + Long.toString(block.getTimestamp()) + block.getPreviousHash() + Integer.toString(block.getNonce());
        for (Transaction transaction : block.getTransactions()) {
            dataToHash += transaction.getHash();
        }
        try {
            // Convert the byte array generated by the SHA-256 hash function into 
            // a hexadecimal string that can be used as the data hash value for a block in a blockchain.
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(dataToHash.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static void mineBlock(Block block, int difficulty) {
        while (!BlockValidator.isValidProofPoW(block, difficulty)) {
            block.setNonce(block.getNonce() + 1);
            block.setHash(calculateHashPoW(block));
        }
    }
}
