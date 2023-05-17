package com.ledger.consensus;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import com.ledger.blockchain.Block;
import com.ledger.blockchain.Blockchain;
import com.ledger.transaction.Transaction;
import com.ledger.validation.BlockValidator;

public class ProofOfWork {
    /**
    * Checks the validity of the blockchain by iterating through each block.
    * It verifies the integrity of each block's hash and the link between consecutive blocks.
    * Additionally, it ensures that each block's hash meets the required difficulty level.
    * Returns true if the blockchain is valid, and false otherwise.
    */
    public boolean isChainValidPoW(Blockchain blockchain, int difficulty) {
        Block currentBlock;
        Block previousBlock;
        List<Block> blockchainBlocks = blockchain.getblockchainBlocks();

        String hashTarget = new String(new char[difficulty]).replace('\0', '0');

        for (int i = 1; i < blockchainBlocks.size(); i++) {
            currentBlock = blockchainBlocks.get(i);
            previousBlock = blockchainBlocks.get(i - 1);
            if (!currentBlock.getHash().equals(currentBlock.calculateHashData())) {
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

    
    
    public static void mineBlock(Block block, int difficulty) {
        while (!BlockValidator.isValidProofPoW(block, difficulty)) {
            block.setNonce(block.getNonce() + 1);
            block.setHash(block.calculateHashData());
        }
    }
}
