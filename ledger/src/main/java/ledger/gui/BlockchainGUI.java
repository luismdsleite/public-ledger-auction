package ledger.gui;

import javax.swing.*;

import ledger.blockchain.Block;
import ledger.blockchain.Blockchain;

import java.awt.*;

public class BlockchainGUI {
    private JFrame frame;
    private JPanel panel;

    public BlockchainGUI() {
        // Define the GUI components
        frame = new JFrame("Blockchain Status");
        frame.setLayout(new FlowLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
    }

    public JPanel getPanel() {
        return panel;
    }

    public JFrame getFrame() {
        return frame;
    }
    
    public JScrollPane createBlockPanelContainer(Blockchain blockchain) {
        JPanel blockPanelContainer = new JPanel();
        blockPanelContainer.setLayout(new BoxLayout(blockPanelContainer, BoxLayout.Y_AXIS));
    
        for (Block block : blockchain.getblockchainBlocks()) {
            BlockPanel blockPanel = new BlockPanel(block);
            blockPanelContainer.add(blockPanel);
        }
    
        JScrollPane scrollPane = new JScrollPane(blockPanelContainer);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    
        return scrollPane;
    }

    public void addBlockPanelContainer(JPanel blockPanelContainer) {
        JScrollPane scrollPane = new JScrollPane(blockPanelContainer);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        frame.add(scrollPane);
    }

    

   
}
