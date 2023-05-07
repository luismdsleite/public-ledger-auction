package com.ledger.gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.ledger.Block;
import com.ledger.Transaction;

public class BlockPanel extends JPanel {

    public BlockPanel(Block block) {
        setLayout(new GridBagLayout());

        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        JLabel indexLabel = new JLabel("Index: " + block.getIndex());

        JLabel prevHashLabel = new JLabel("Previous Hash: " + block.getPreviousHash());
        JLabel timestampLabel = new JLabel("Timestamp: " + DateFormat.getDateTimeInstance().format(new Date(block.getTimestamp())));
        JLabel nonceLabel = new JLabel("Nonce: " + block.getNonce());
        JLabel hashLabel = new JLabel("Hash of Block Data: " + block.getHash());

        JTextArea transactionsArea = new JTextArea("Transactions: ");
        for (Transaction transaction : block.getTransactions()) {
            transactionsArea.append("\n" + transaction.toString());
        }

        transactionsArea.setEditable(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets.top = 5;
        gbc.insets.left = 5;
        add(indexLabel, gbc);
        gbc.gridy = 1;
        add(prevHashLabel, gbc);
        gbc.gridy = 2;
        add(timestampLabel, gbc);
        gbc.gridy = 3;
        add(nonceLabel, gbc);
        gbc.gridy = 4;
        add(hashLabel, gbc);
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets.bottom = 5;
        gbc.insets.right = 5;
        add(transactionsArea, gbc);
    }
}