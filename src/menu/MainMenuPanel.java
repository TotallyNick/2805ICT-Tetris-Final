package menu;

import javax.swing.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuPanel extends JPanel {
    private MenuFacade menuFacade;

    public MainMenuPanel(MenuFacade menuFacade) {
        this.menuFacade = menuFacade;

        // Set Layout for the Main Menu Panel
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Add vertical glue before the title to push it down
        add(Box.createVerticalGlue());

        // Title Label
        JLabel titleLabel = new JLabel("Tetris Game Menu 2805ICT Group 69");
        titleLabel.setAlignmentX(CENTER_ALIGNMENT); // Center horizontally
        titleLabel.setFont(new Font(titleLabel.getFont().getName(), Font.BOLD, 16)); // Set title size to 16
        add(titleLabel);

        // Add some space after the title
        add(Box.createVerticalStrut(20));

        // Start Game Button
        JButton startButton = new JButton("Start Game");
        startButton.setAlignmentX(CENTER_ALIGNMENT); // Center horizontally
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Starting the Game...");
                menuFacade.quitGame(); // Placeholder for now
            }
        });

        // Configuration Button - Opens the new ConfigMenuPanel
        JButton configButton = new JButton("Configuration");
        configButton.setAlignmentX(CENTER_ALIGNMENT); // Center horizontally
        configButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuFacade.showConfigMenu(); // Calls method to show ConfigMenuPanel
            }
        });

        // HighScore Button
        JButton highscoreButton = new JButton("High Scores");
        highscoreButton.setAlignmentX(CENTER_ALIGNMENT); // Center horizontally
        highscoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuFacade.showHighScore();
            }
        });

        // Quit Button
        JButton quitButton = new JButton("Quit");
        quitButton.setAlignmentX(CENTER_ALIGNMENT); // Center horizontally
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuFacade.quitGame(); // Quit without dialog box
            }
        });

        // Add Buttons to Panel
        add(startButton);
        add(Box.createVerticalStrut(10));
        add(configButton);
        add(Box.createVerticalStrut(10));
        add(highscoreButton);
        add(Box.createVerticalStrut(10));
        add(quitButton);

        // Add vertical glue after the buttons to push the author text down
        add(Box.createVerticalGlue());

        // Author Label (added below the buttons)
        JLabel authorLabel = new JLabel("by Nicholas Webster s5178735");
        authorLabel.setAlignmentX(CENTER_ALIGNMENT); // Center horizontally
        add(authorLabel);

        // Add a small vertical gap after the author label
        add(Box.createVerticalStrut(10));
    }
}
