package ledger.validation;

import java.util.List;

import ledger.blockchain.Block;
import ledger.blockchain.Blockchain;
import ledger.consensus.ProofOfWork;

public class BlockChainValidator {
    
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
            if (!currentBlock.getHash().equals(ProofOfWork.calculateHashPoW(currentBlock))) {
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
