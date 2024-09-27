package menu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuPanel extends JPanel {
    private MenuFacade menuFacade;

    public MainMenuPanel(MenuFacade menuFacade) {
        this.menuFacade = menuFacade;

        // Set Layout
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Start Game Button
        JButton startButton = new JButton("Start Game");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Start the game logic via GameManager
                JOptionPane.showMessageDialog(null, "Starting the Game...");
                menuFacade.quitGame(); // Placeholder for now
            }
        });

        // Configuration Button
        JButton settingsButton = new JButton("Configuration");
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuFacade.showConfigMenu();
            }
        });

        // Quit Button
        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuFacade.quitGame();
            }
        });

        // Add Buttons to Panel
        add(startButton);
        add(settingsButton);
        add(quitButton);
    }
}
