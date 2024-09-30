package menu;

import javax.swing.*;
import java.awt.*;

public class MenuFacade {
    public JFrame mainFrame;
    private MainMenuPanel mainMenuPanel;
    private ConfigMenuPanel configMenuPanel;
    private HighScorePanel highScorePanel;
    private TetrisGamePanel tetrisGamePanel;

    public MenuFacade() {
        // Initialize JFrame
        mainFrame = new JFrame("Tetris Menu");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(900, 950);
        mainFrame.setLayout(new CardLayout());

        // Initialize Panels
        mainMenuPanel = new MainMenuPanel(this);
        configMenuPanel = new ConfigMenuPanel(this);
        highScorePanel = new HighScorePanel(this);
        tetrisGamePanel =  new TetrisGamePanel(this);

        // Add Panels to the Frame
        mainFrame.add(mainMenuPanel, "Main Menu Panel");
        mainFrame.add(tetrisGamePanel, "Tetris Game Panel");
        mainFrame.add(configMenuPanel, "Config Menu Panel");
        mainFrame.add(highScorePanel, "Highscore Panel");

        // Center the window on the screen
        mainFrame.setLocationRelativeTo(null);

        // Display main menu
        showMainMenu();
    }
    // Start the Game Instance
    public void showGame(){
        CardLayout c1 = (CardLayout) mainFrame.getContentPane().getLayout();
        c1.show(mainFrame.getContentPane(), "Tetris Game Panel");
        mainFrame.setVisible(true);
    }

    // Display Main Menu Method
    public void showMainMenu() {
        CardLayout c1 = (CardLayout) mainFrame.getContentPane().getLayout();
        c1.show(mainFrame.getContentPane(), "Main Menu Panel");
        mainFrame.setVisible(true);
    }

    // Display Config Menu Panel Method
    public void showConfigMenu() {
        CardLayout c1 = (CardLayout) mainFrame.getContentPane().getLayout();
        c1.show(mainFrame.getContentPane(), "Config Menu Panel");
    }

    public void showHighScore() {
        highScorePanel.refreshScores();  // Refresh scores before showing the panel
        CardLayout c1 = (CardLayout) mainFrame.getContentPane().getLayout();
        c1.show(mainFrame.getContentPane(), "Highscore Panel");
    }

    // Quit Menu Method
    public void quitGame() {
        System.exit(0);
    }

    public JFrame getMainFrame() {
        return mainFrame;
    }
}