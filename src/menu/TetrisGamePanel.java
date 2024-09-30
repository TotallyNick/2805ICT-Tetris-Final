package menu;

import javax.swing.*;
import java.awt.*;
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
        tetrisGame = new TetrisGame(frame);
        add(tetrisGame, BorderLayout.CENTER);

        // Ensure the TetrisGame panel can capture key inputs
        tetrisGame.setFocusable(true);
        tetrisGame.requestFocusInWindow();
    }
}
