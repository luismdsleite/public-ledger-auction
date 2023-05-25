package ledger;

import ledger.blockchain.Block;
import ledger.blockchain.Blockchain;
import ledger.transaction.Transaction;
import ledger.wallet.WalletAddressGenerator;

import java.io.IOException;
import java.security.*;
import java.util.ArrayList;
import java.util.Arrays;

import ledger.security.RSAKeyGenerator;
import ledger.security.RSASignatureUtils;


public class Main {

    private static Blockchain createDummyBlockchain() {

        // create public and private key
        RSAKeyGenerator bob = new RSAKeyGenerator();
        RSAKeyGenerator alice = new RSAKeyGenerator();
        
        // create wallet addres of bob and alice
        String bobWallet = WalletAddressGenerator.generateWalletAddress();
        String aliceWallet = WalletAddressGenerator.generateWalletAddress();
        
        Blockchain blockchain = new Blockchain(0,3);
        
        // create transaction from bob to alice of 1 with 0 fee
        Transaction transaction1 = new Transaction(bobWallet, aliceWallet, bob.getPublicKey(),10, 0);
        // sign transaction
        transaction1.setSignature(RSASignatureUtils.signString(transaction1.getHash(), bob.getPrivateKey()));

        Block block1 = new Block(blockchain.getLatestBlock().getIndex() + 1, blockchain.getLatestBlock().getHash(), new ArrayList<Transaction>(Arrays.asList(transaction1)));
        blockchain.addBlock(block1);
        
        // create transaction from alice to bob of 1 with 0 fee
        Transaction transaction2 = new Transaction(aliceWallet, bobWallet, alice.getPublicKey(),10, 0);
        // sign transaction
        transaction2.setSignature(RSASignatureUtils.signString(transaction2.getHash(), alice.getPrivateKey()));

        Block block2 = new Block(blockchain.getLatestBlock().getIndex() + 1, blockchain.getLatestBlock().getHash(), new ArrayList<Transaction>(Arrays.asList(transaction1)));
        blockchain.addBlock(block2);

        return blockchain;
    }
    public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, SignatureException, IOException, InterruptedException {
        
        
        // ----------------------
        // New blockchain using PoW
        Blockchain blockchain = createDummyBlockchain();
        System.out.println(blockchain);
        
    }
}
