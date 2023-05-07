package com.ledger;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {
        public static void main(String[] args) {
            Blockchain blockchain = new Blockchain(3);
    
            Transaction transaction1 = new Transaction("Alice", "Bob", 10);
            Block block1 = new Block(1, blockchain.getLatestBlock().getHash(), new ArrayList<Transaction>(Arrays.asList(transaction1)));
            blockchain.addBlock(block1);
    
            Transaction transaction2 = new Transaction("Bob", "Charlie", 5);
            Block block2 = new Block(2, blockchain.getLatestBlock().getHash(), new ArrayList<Transaction>(Arrays.asList(transaction2)));
            blockchain.addBlock(block2);
    
            System.out.println("Is blockchain valid? " + blockchain.isChainValid());
        }
    }

