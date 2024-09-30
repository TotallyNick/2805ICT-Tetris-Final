package menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import game.TetrisGame;

public class TetrisGamePanel extends JPanel {

    private TetrisGame tetrisGame;

    // Constructor for initializing the TetrisGamePanel and adding the Tetris game to it
    public TetrisGamePanel(MenuFacade frame) {
        // Set Layout for proper display of components
        setLayout(new BorderLayout());

        // Title at the top
        JLabel titleLabel = new JLabel("Welcome to the Tetris Game");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH); // Add title to the top of the panel

        // Initialize and add the Tetris game to the center of the panel
        tetrisGame = new TetrisGame();
        add(tetrisGame, BorderLayout.CENTER);

        // Ensure the TetrisGame panel can capture key inputs
        tetrisGame.setFocusable(true);
        tetrisGame.requestFocusInWindow();

        // Back Button to return to Main Menu
        JButton backButton = new JButton("Back to Main Menu");
        backButton.setPreferredSize(new Dimension(150, 50)); // Set size of the back button
        backButton.setAlignmentX(CENTER_ALIGNMENT); // Center horizontally
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.showMainMenu(); // Return to the main menu
            }
        });

        // Create a panel for the button and position it at the bottom
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Center the button horizontally
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH); // Add button panel to the bottom of the main panel
    }
}