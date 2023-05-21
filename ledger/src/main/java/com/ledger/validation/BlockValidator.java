package com.ledger.validation;

import com.ledger.blockchain.Block;

public class BlockValidator {
    /**
     * Checks if the proof-of-work (PoW) of a block is valid.
     * 
     * @param block The block to be validated.
     * @param difficulty The difficulty level for mining blocks.
     * @return True if the proof-of-work is valid, false otherwise.
     */
    public static boolean isValidProofPoW(Block block, int difficulty) {
        // String containing number of 0's 
        String target = new String(new char[difficulty]).replace('\0', '0');
        // Calculates hash using nonce
        block.setHash(block.calculateHashData());
        // Gets calculated hash
        String blockHash = block.getHash();
        return blockHash.substring(0, difficulty).equals(target);
    }
    
}
