package com.ledger;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import com.ledger.gui.BlockPanel;
import com.ledger.gui.BlockchainGUI;

public class Main {

    private static Blockchain createDummyBlockchain() {
        Blockchain blockchain = new Blockchain(3);
    
        Transaction transaction1 = new Transaction("Alice", "Bob", 10);
        Block block1 = new Block(1, blockchain.getLatestBlock().getHash(),
                new ArrayList<Transaction>(Arrays.asList(transaction1)));
        blockchain.addBlock(block1);
    
        Transaction transaction2 = new Transaction("Bob", "Charlie", 5);
        Block block2 = new Block(2, blockchain.getLatestBlock().getHash(),
                new ArrayList<Transaction>(Arrays.asList(transaction2)));
        blockchain.addBlock(block2);
    
        Transaction transaction3 = new Transaction("Charlie", "Dave", 7.5f);
        Block block3 = new Block(3, blockchain.getLatestBlock().getHash(),
                new ArrayList<Transaction>(Arrays.asList(transaction3)));
        blockchain.addBlock(block3);
    
        return blockchain;
    }
    public static void main(String[] args) {
        Blockchain blockchain = createDummyBlockchain();

        // Build UI
        BlockchainGUI gui = new BlockchainGUI();

        JPanel blockPanelContainer = new JPanel();
        blockPanelContainer.setLayout(new BoxLayout(blockPanelContainer, BoxLayout.Y_AXIS));

        for (Block block : blockchain.getBlockchain()) {
            BlockPanel blockPanel = new BlockPanel(block);
            blockPanelContainer.add(blockPanel);
        }

        // Add blocks to Panel
        gui.addBlockPanelContainer(blockPanelContainer);

        gui.getFrame().getContentPane().add(BorderLayout.CENTER, gui.getPanel());
        gui.getFrame().pack();
        gui.getFrame().setVisible(true);

    }
}
