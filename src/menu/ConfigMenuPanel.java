package menu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfigMenuPanel extends JPanel {
    private MenuFacade menuFacade;

    public ConfigMenuPanel(MenuFacade menuFacade) {
        this.menuFacade = menuFacade;

        // Set Layout
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Example settings like volume control
        JLabel settingsLabel = new JLabel("Settings: (More to come)");
        add(settingsLabel);

        // Back Button
        JButton backButton = new JButton("Back to Main Menu");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuFacade.showMainMenu();
            }
        });

        // Add Components to Panel
        add(backButton);
    }
}

