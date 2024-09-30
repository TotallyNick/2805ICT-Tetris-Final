package menu;

import javax.swing.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfigMenuPanel extends JPanel {

    public ConfigMenuPanel(MenuFacade menuFacade) {

        // Set Layout for vertical alignment
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Add vertical glue before the content to push it down
        add(Box.createVerticalGlue());

        // Title Label for Configuration
        JLabel configLabel = new JLabel("Configuration");
        configLabel.setAlignmentX(CENTER_ALIGNMENT); // Center horizontally
        configLabel.setFont(new Font(configLabel.getFont().getName(), Font.BOLD, 16)); // Set title size to 16
        add(configLabel);

        // Add space after title
        add(Box.createVerticalStrut(20));

        // Example Configuration Option (Placeholder)
        JLabel volumeLabel = new JLabel("Volume: (More settings coming)");
        volumeLabel.setAlignmentX(CENTER_ALIGNMENT); // Center horizontally
        add(volumeLabel);

        // Add space after volume label
        add(Box.createVerticalStrut(20));

        // Back Button to return to Main Menu
        JButton backButton = new JButton("Back to Main Menu");
        backButton.setAlignmentX(CENTER_ALIGNMENT); // Center horizontally
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuFacade.showMainMenu(); // Return to the main menu
            }
        });

        // Add button to the panel
        add(backButton);

        // Add vertical glue after the content to push it up
        add(Box.createVerticalGlue());
    }
}
