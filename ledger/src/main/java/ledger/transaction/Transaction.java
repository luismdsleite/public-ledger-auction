package ledger.transaction;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.Date;

public class Transaction {
    private String sender;
    private String recipient;
    private String hash;
    private String signature;
    private PublicKey publicKey;
    
    private float amount;
    private float fee;
    
    private long timestamp;

    public Transaction(String sender, String recipient, PublicKey publicKey, float amount, float fee) {
        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
        this.fee = fee;
        this.publicKey = publicKey;
        this.timestamp = new Date().getTime();
        this.hash = calculateHashData();
    }

    public String getSender() {
        return sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public float getAmount() {
        return amount;
    }

    public String getHash() {
        return hash;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("     Timestamp: ").append(timestamp).append("\n");
        sb.append("     Sender: ").append(sender).append("\n");
        sb.append("     Recipient: ").append(recipient).append("\n");
        sb.append("     Amount: ").append(amount).append("\n");
        sb.append("     Fee: ").append(fee).append("\n");
        sb.append("     Hash: ").append(hash).append("\n");
        sb.append("     Signature: ").append(signature).append("\n");
        return sb.toString();
    }

    public String calculateHashData() {
        String dataToHash = sender + recipient + Float.toString(amount)+ Float.toString(fee) + Long.toString(timestamp) + publicKey;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(dataToHash.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
