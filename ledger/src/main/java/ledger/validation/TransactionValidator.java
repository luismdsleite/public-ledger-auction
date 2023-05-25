package ledger.validation;

import java.util.Date;

import ledger.transaction.Transaction;

public class TransactionValidator {

    // Functions to check if transaction is in a valid format before its put on the transaction pool

    public static boolean isValidTransaction(Transaction transaction) {
        // Verify that sender and recipient adddress is valid
        
        // Verify if all fields of transaction are field anda valid format
        if(!verifyTransactionFormat(transaction)) return false;
        return true;
    }
    

    public static boolean verifyTransactionFormat(Transaction transaction) {
        if (transaction == null) {
            return false;
        }
    
        // Check if sender, recipient, and hash are not null or empty
        if (transaction.getSender() == null || transaction.getSender().isEmpty()
                || transaction.getRecipient() == null || transaction.getRecipient().isEmpty()
                || transaction.getHash() == null || transaction.getHash().isEmpty()) {
            return false;
        }
    
        // Check if amount, timestamp, and fee are non-negative, and if time is before the current time
        if (transaction.getAmount() < 0 || transaction.getTimestamp() < 0  || transaction.getTimestamp() < new Date().getTime()) {
            return false;
        }
        
        return true;
    }
    // -------------------------------
    
    // This functions is for after the transaction is removed from the transaction pool, and before it gets mined

    public static boolean processTransaction(Transaction transaction) {
        // Perform checks and process the transaction
        // For example, you can check if the sender has enough funds, if the recipient is valid, etc.
        // Return true if the transaction is valid, false otherwise
        // Implement your validation logic here

        // Check if the sender and recipient addresses are valid
        
        // Check if the transaction amount is positive
        if (transaction.getAmount() <= 0) {
            return false;
        }
    
        // Additional validation checks can be added here based on your requirements
    
        // For example, you can check if the sender has enough funds to cover the transaction amount
        if (!hasSufficientFunds(transaction.getSender(), transaction.getAmount())) {
            return false;
        }
        return true;
    }
    
    private static boolean hasSufficientFunds(String sender, float amount) {
        // Implement logic to check if the sender has enough funds to cover the transaction amount
        // Retrieve the sender's balance from the blockchain or any other data structure
        // Compare the balance with the transaction amount
        // Return true if the sender has sufficient funds, false otherwise
        // Implement your balance checking logic here
    
        // Example: assume the sender always has sufficient funds
        return true;
    }

}