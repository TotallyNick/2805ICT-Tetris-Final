package menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HighScorePanel extends JPanel {
    private MenuFacade menuFacade;

    public HighScorePanel(MenuFacade menuFacade) {
        this.menuFacade = menuFacade;

        // Set Layout for vertical alignment
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Add vertical glue before the content to push it down
        add(Box.createVerticalGlue());

        // Title Label for High Scores
        JLabel highScoreLabel = new JLabel("High Scores");
        highScoreLabel.setAlignmentX(CENTER_ALIGNMENT); // Center horizontally
        highScoreLabel.setFont(new Font(highScoreLabel.getFont().getName(), Font.BOLD, 16)); // Set title size to 16
        add(highScoreLabel);

        // Add space after title
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
