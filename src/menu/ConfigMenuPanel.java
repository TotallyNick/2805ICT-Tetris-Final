package menu;

import javax.swing.*;
import java.awt.*;

public class ConfigMenuPanel extends JPanel {

    public ConfigMenuPanel(MenuFacade menuFacade) {

        // Set GridBagLayout for structured component placement
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding around components
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0; // Set all components to be in the center column

        // Title Label for Configuration
        JLabel configLabel = new JLabel("Configuration");
        configLabel.setFont(new Font(configLabel.getFont().getName(), Font.BOLD, 16)); // Set title size to 16
        gbc.gridy = 0; // First row
        gbc.gridwidth = 2; // Span across two columns for better centering
        add(configLabel, gbc);

        // Field Width
        JLabel fieldWidthLabel = new JLabel("Field Width:");
        JSlider fieldWidthSlider = new JSlider(JSlider.HORIZONTAL, 5, 15, 10);
        fieldWidthSlider.setMajorTickSpacing(5);
        fieldWidthSlider.setMinorTickSpacing(1);
        fieldWidthSlider.setPaintTicks(true);
        fieldWidthSlider.setPaintLabels(true);
        fieldWidthSlider.setPreferredSize(new Dimension(300, 50));
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(fieldWidthLabel, gbc);

        gbc.gridy = 2;
        add(fieldWidthSlider, gbc);

        // Field Height
        JLabel fieldHeightLabel = new JLabel("Field Height:");
        JSlider fieldHeightSlider = new JSlider(JSlider.HORIZONTAL, 15, 30, 20);
        fieldHeightSlider.setMajorTickSpacing(5);
        fieldHeightSlider.setMinorTickSpacing(1);
        fieldHeightSlider.setPaintTicks(true);
        fieldHeightSlider.setPaintLabels(true);
        fieldHeightSlider.setPreferredSize(new Dimension(300, 50));
        gbc.gridy = 3;
        add(fieldHeightLabel, gbc);

        gbc.gridy = 4;
        add(fieldHeightSlider, gbc);

        // Game Level
        JLabel gameLevelLabel = new JLabel("Game Level:");
        JSlider gameLevelSlider = new JSlider(JSlider.HORIZONTAL, 1, 10, 2);
        gameLevelSlider.setMajorTickSpacing(1);
        gameLevelSlider.setMinorTickSpacing(1);
        gameLevelSlider.setPaintTicks(true);
        gameLevelSlider.setPaintLabels(true);
        gameLevelSlider.setPreferredSize(new Dimension(300, 50));
        gbc.gridy = 5;
        add(gameLevelLabel, gbc);

        gbc.gridy = 6;
        add(gameLevelSlider, gbc);

        // Music Toggle
        JLabel musicLabel = new JLabel("Music:");
        JToggleButton musicToggle = new JToggleButton("On");
        musicToggle.setPreferredSize(new Dimension(100, 40));
        musicToggle.addActionListener(e -> musicToggle.setText(musicToggle.isSelected() ? "Off" : "On"));
        gbc.gridy = 7;
        add(musicLabel, gbc);

        gbc.gridy = 8;
        add(musicToggle, gbc);

        // Sound Effect Toggle
        JLabel soundEffectLabel = new JLabel("Sound Effect:");
        JToggleButton soundEffectToggle = new JToggleButton("On");
        soundEffectToggle.setPreferredSize(new Dimension(100, 40));
        soundEffectToggle.addActionListener(e -> soundEffectToggle.setText(soundEffectToggle.isSelected() ? "Off" : "On"));
        gbc.gridy = 9;
        add(soundEffectLabel, gbc);

        gbc.gridy = 10;
        add(soundEffectToggle, gbc);

        // AI Play Toggle
        JLabel aiPlayLabel = new JLabel("AI Play:");
        JToggleButton aiPlayToggle = new JToggleButton("On");
        aiPlayToggle.setPreferredSize(new Dimension(100, 40));
        aiPlayToggle.addActionListener(e -> aiPlayToggle.setText(aiPlayToggle.isSelected() ? "Off" : "On"));
        gbc.gridy = 11;
        add(aiPlayLabel, gbc);

        gbc.gridy = 12;
        add(aiPlayToggle, gbc);

        // Extend Mode Toggle
        JLabel extendModeLabel = new JLabel("Extend Mode:");
        JToggleButton extendModeToggle = new JToggleButton("On");
        extendModeToggle.setPreferredSize(new Dimension(100, 40));
        extendModeToggle.addActionListener(e -> extendModeToggle.setText(extendModeToggle.isSelected() ? "Off" : "On"));
        gbc.gridy = 13;
        add(extendModeLabel, gbc);

        gbc.gridy = 14;
        add(extendModeToggle, gbc);

        // Back Button to return to Main Menu
        JButton backButton = new JButton("Back to Main Menu");
        backButton.setPreferredSize(new Dimension(150, 40)); // Match preferred button size
        gbc.gridy = 15;
        add(backButton, gbc);

        // Back button action listener
        backButton.addActionListener(e -> menuFacade.showMainMenu()); // Return to the main menu
    }
}
