package com.ledger.gui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.ledger.Block;
import com.ledger.Transaction;

public class BlockPanel extends JPanel {

    public BlockPanel(Block block) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        JLabel indexLabel = new JLabel("Index: " + block.getIndex());
        JLabel hashLabel = new JLabel("Hash: " + block.getHash());
        JTextArea transactionsArea = new JTextArea("Transactions: ");
        for (Transaction transaction : block.getTransactions()) {
            transactionsArea.append("\n" + transaction.toString());
        }
        add(indexLabel, BorderLayout.NORTH);
        add(hashLabel, BorderLayout.CENTER);
        add(transactionsArea, BorderLayout.SOUTH);
    }
}