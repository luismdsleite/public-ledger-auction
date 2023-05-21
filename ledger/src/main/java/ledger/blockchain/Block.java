package ledger.blockchain;

// import java.io.UnsupportedEncodingException;
// import java.security.MessageDigest;
// import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;

import ledger.consensus.ProofOfWork;
import ledger.transaction.Transaction;

public class Block {
    private int index;
    private long timestamp;
    private ArrayList<Transaction> transactions = new ArrayList<Transaction>();
    private int nonce;
    private String hash;
    private String previousHash;
    
    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public int getIndex() {
        return index;
    }

    public String getHash() {
        return hash;
    }
    
    public long getTimestamp() {
        return timestamp;
    }

    public int getNonce() {
        return nonce;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public void setNonce(int nonce) {
        this.nonce = nonce;
    }

    public void setHash(String calculatedHashData) {
        this.hash = calculatedHashData;
    }

    // Overriding toString() method of String class
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" Index: ").append(index).append("\n");
        sb.append(" Timestamp: ").append(timestamp).append("\n");
        sb.append(" Previous Hash: ").append(previousHash).append("\n");
        sb.append(" Nonce: ").append(nonce).append("\n");
        sb.append(" Hash: ").append(hash).append("\n");
        sb.append(" Transactions: [\n");
        for (Transaction transaction : transactions) {
            sb.append(transaction.toString()).append("\n");
        }
        sb.append(" ]\n");
        return sb.toString();
    }

    public Block(int index, String previousHash, ArrayList<Transaction> transactions) {
        this.index = index;
        this.timestamp = new Date().getTime();
        this.previousHash = previousHash;
        this.transactions = transactions;
        this.nonce = 0;
    }

    

    
    public void mineBlock(int consensus, int difficulty) {
        switch (consensus) {
            case 1: // Proof-of-Work (PoW)
                ProofOfWork.mineBlock(this, difficulty);
                break;
        
            default: // Make default case Proof-of-Work
                ProofOfWork.mineBlock(this, difficulty);
                break;
        }
    }

    // public boolean addTransaction(Transaction transaction) {
    //     if (transaction == null) return false;
    //     if ((previousHash != "0")) {
    //         if ((transaction.processTransaction() != true)) {
    //             return false;
    //         }
    //     }
    //     transactions.add(transaction);
    //     return true;
    // }

   
}
