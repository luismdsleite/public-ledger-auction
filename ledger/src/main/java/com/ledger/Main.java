package com.ledger;

// import java.awt.BorderLayout;
// import java.io.IOException;
// import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

// import javax.swing.BoxLayout;
// import javax.swing.JPanel;

import com.ledger.blockchain.Block;
import com.ledger.blockchain.Blockchain;
// import com.ledger.gui.BlockPanel;
// import com.ledger.gui.BlockchainGUI;
import com.ledger.transaction.Transaction;
import com.ledger.wallet.WalletAddressGenerator;

import java.security.*;

import com.ledger.security.RSASignatureUtils;
// import s_kademlia.LaunchNode;

public class Main {

    // private static Blockchain createDummyBlockchain() {
    //     Blockchain blockchain = new Blockchain(0,3);
    
    //     Transaction transaction1 = new Transaction("Alice", "Bob", 10);
    //     Block block1 = new Block(1, blockchain.getLatestBlock().getHash(),
    //             new ArrayList<Transaction>(Arrays.asList(transaction1)));
    //     blockchain.addBlock(block1);
    
    //     Transaction transaction2 = new Transaction("Bob", "Charlie", 5);
    //     Block block2 = new Block(2, blockchain.getLatestBlock().getHash(),
    //             new ArrayList<Transaction>(Arrays.asList(transaction2)));
    //     blockchain.addBlock(block2);
    
    //     Transaction transaction3 = new Transaction("Charlie", "Dave", 7.5f);
    //     Block block3 = new Block(3, blockchain.getLatestBlock().getHash(),
    //             new ArrayList<Transaction>(Arrays.asList(transaction3)));
    //     blockchain.addBlock(block3);
    
    //     return blockchain;
    // }
    public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, SignatureException {
        // Blockchain blockchain = createDummyBlockchain();

        // Build UI
        // BlockchainGUI gui = new BlockchainGUI();

        // JPanel blockPanelContainer = new JPanel();
        // blockPanelContainer.setLayout(new BoxLayout(blockPanelContainer, BoxLayout.Y_AXIS));

        // for (Block block : blockchain.getBlockchain()) {
        //     BlockPanel blockPanel = new BlockPanel(block);
        //     blockPanelContainer.add(blockPanel);
        // }

        // Add blocks to Panel
        // gui.addBlockPanelContainer(blockPanelContainer);

        // gui.getFrame().getContentPane().add(BorderLayout.CENTER, gui.getPanel());
        // gui.getFrame().pack();
        // gui.getFrame().setVisible(true);
        

        // ----------------------
        // New blockchain using PoW
        Blockchain blockchain = new Blockchain(1,3);
        System.out.println(blockchain);

        // Generate wallet address
        // String generatedAddress = WalletAddressGenerator.generateWalletAddress();
        // System.out.println("Generated Address: " + generatedAddress);
        
        // Verify if wallet address is valid
        // String testAddress = "0x4A1DDCC299EEE65E089B54AF6A21BD59C4BCA52B";
        // boolean isValid = WalletAddressGenerator.verifyAddressFormat(testAddress);
        // System.out.println("Is Valid Address: " + isValid);
        
        // Get keys
        // Generate an RSA key pair
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048); // Set the desired key size
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        // Retrieve the public and private keys
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        
        // Create transaction
        Transaction test = new Transaction("absc", "dfawd", publicKey, 0, 0);

        // Sign transaction
        test.setSignature(RSASignatureUtils.signString(test.getHash(), privateKey));

        // Verify transaction
        System.out.println(RSASignatureUtils.verifyString(test.getHash(), test.getSignature(), publicKey));
    }
}
