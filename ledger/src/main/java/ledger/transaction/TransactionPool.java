package ledger.transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionPool {
    private List<Transaction> transactions;

    public TransactionPool() {
        transactions = new ArrayList<>();
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public void removeTransaction(Transaction transaction) {
        transactions.remove(transaction);
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void clear() {
        transactions.clear();
    }
}

