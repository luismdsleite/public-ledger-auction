package com.ledger.blockchain;

import java.util.ArrayList;
import java.util.List;

import com.ledger.transaction.Transaction;

public class Blockchain {
    private int difficulty;
    private List<Block> blockchainBlocks = new ArrayList<>();
    private int consensus;

    public List<Block> getblockchainBlocks() {
        return blockchainBlocks;
    }

    public Block getLatestBlock() {
        return blockchainBlocks.get(blockchainBlocks.size() - 1);
    }

    public void addBlock(Block newBlock) {
        blockchainBlocks.add(newBlock);
    }

    // Overriding toString() method of String class
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Difficulty: ").append(difficulty).append("\n");
        if(consensus == 1) sb.append("Consensus: PoW").append("\n-----------\n");
        else sb.append("Consensus: Smart Contract").append("\n-----------\n");
        sb.append("Blockchain Blocks: [\n");
        for (Block block : blockchainBlocks) {
            sb.append(block.toString()).append("\n");
        }
        sb.append("]\n");
        return sb.toString();
    }

    /**
     * Blockchain constructor.
     * 
     * @param consensus The consensus flag indicating the consensus algorithm. 
     *                  Set to 0 for Proof-of-Work (PoW) and 1 for Smart Contract.
     * @param difficulty The difficulty level for mining blocks (applicable for PoW).
     */

    public Blockchain(int consensus, int difficulty) {
        this.difficulty = difficulty;
        this.consensus = consensus;

        Block genesisBlock = new Block(0, "0", new ArrayList<>()); // Create genesisBlock
        genesisBlock.mineBlock(consensus, difficulty);; // Mine the genesis block during initialization
        blockchainBlocks.add(genesisBlock) ;
    }

    
}
