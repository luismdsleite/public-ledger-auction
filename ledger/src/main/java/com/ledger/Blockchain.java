package com.ledger;

import java.util.ArrayList;

public class Blockchain {
    private int difficulty;
    private ArrayList<Block> blockchain = new ArrayList<Block>();

    public ArrayList<Block> getBlockchain() {
        return blockchain;
    }

    public Blockchain(int difficulty) {
        this.difficulty = difficulty;
        Block genesisBlock = new Block(0, "0", new ArrayList<Transaction>());
        genesisBlock.mineBlock(difficulty);
        blockchain.add(genesisBlock);
    }

    public Block getLatestBlock() {
        return blockchain.get(blockchain.size() - 1);
    }

    public void addBlock(Block newBlock) {
        newBlock.mineBlock(difficulty);
        blockchain.add(newBlock);
    }

    public boolean isChainValid() {
        Block currentBlock;
        Block previousBlock;
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');

        for (int i = 1; i < blockchain.size(); i++) {
            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i - 1);
            if (!currentBlock.getHash().equals(currentBlock.calculateHash())) {
                return false;
            }
            if (!previousBlock.getHash().equals(currentBlock.getPreviousHash())) {
                return false;
            }
            if (!currentBlock.getHash().substring(0, difficulty).equals(hashTarget)) {
                return false;
            }
        }
        return true;
    }
} 
