package menu;

import javax.swing.*;
import java.awt.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import com.google.gson.Gson;
import config.GameConfig;

public class ConfigMenuPanel extends JPanel {

    // Declare sliders and toggle buttons as instance variables
    private JSlider fieldWidthSlider;
    private JSlider fieldHeightSlider;
    private JSlider gameLevelSlider;
    private JToggleButton musicToggle;
    private JToggleButton soundEffectToggle;
    private JToggleButton aiPlayToggle;
    private JToggleButton extendModeToggle;

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

        // Initialize all components (sliders and toggles)
        initializeComponents(gbc);

        // Load saved configurations if they exist
        loadConfigurations();  // <-- Load configurations here
        
        // Back Button to return to Main Menu
        JButton backButton = new JButton("Back to Main Menu");
        backButton.setPreferredSize(new Dimension(150, 40)); // Match preferred button size
        gbc.gridy = 15;
        add(backButton, gbc);
        
        // Back button action listener
        backButton.addActionListener(e -> {
            saveConfigurations();  // Save the settings before going back to the main menu
            menuFacade.showMainMenu();
        });
        

    }
        private void initializeComponents(GridBagConstraints gbc) {
            // Field Width
            JLabel fieldWidthLabel = new JLabel("Field Width:");
            fieldWidthSlider = new JSlider(JSlider.HORIZONTAL, 5, 15, 10);
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
            fieldHeightSlider = new JSlider(JSlider.HORIZONTAL, 15, 30, 20);
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
            gameLevelSlider = new JSlider(JSlider.HORIZONTAL, 1, 10, 2);
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
            musicToggle = new JToggleButton("On");
            musicToggle.setPreferredSize(new Dimension(100, 40));
            musicToggle.addActionListener(e -> musicToggle.setText(musicToggle.isSelected() ? "Off" : "On"));
            gbc.gridy = 7;
            add(musicLabel, gbc);

            gbc.gridy = 8;
            add(musicToggle, gbc);

            // Sound Effect Toggle
            JLabel soundEffectLabel = new JLabel("Sound Effect:");
            soundEffectToggle = new JToggleButton("On");
            soundEffectToggle.setPreferredSize(new Dimension(100, 40));
            soundEffectToggle.addActionListener(e -> soundEffectToggle.setText(soundEffectToggle.isSelected() ? "Off" : "On"));
            gbc.gridy = 9;
            add(soundEffectLabel, gbc);

            gbc.gridy = 10;
            add(soundEffectToggle, gbc);

            // AI Play Toggle
            JLabel aiPlayLabel = new JLabel("AI Play:");
            aiPlayToggle = new JToggleButton("On");
            aiPlayToggle.setPreferredSize(new Dimension(100, 40));
            aiPlayToggle.addActionListener(e -> aiPlayToggle.setText(aiPlayToggle.isSelected() ? "Off" : "On"));
            gbc.gridy = 11;
            add(aiPlayLabel, gbc);

            gbc.gridy = 12;
            add(aiPlayToggle, gbc);

            // Extend Mode Toggle
            JLabel extendModeLabel = new JLabel("Extend Mode:");
            extendModeToggle = new JToggleButton("On");
            extendModeToggle.setPreferredSize(new Dimension(100, 40));
            extendModeToggle.addActionListener(e -> extendModeToggle.setText(extendModeToggle.isSelected() ? "Off" : "On"));
            gbc.gridy = 13;
            add(extendModeLabel, gbc);

            gbc.gridy = 14;
            add(extendModeToggle, gbc);
            
        }

    // Method to save configurations to a JSON file
    private void saveConfigurations() {
        GameConfig config = new GameConfig(
                fieldWidthSlider.getValue(),
                fieldHeightSlider.getValue(),
                gameLevelSlider.getValue(),
                musicToggle.isSelected(),
                soundEffectToggle.isSelected(),
                aiPlayToggle.isSelected(),
                extendModeToggle.isSelected()
        );

        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter("game_config.json")) {
            gson.toJson(config, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadConfigurations() {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader("game_config.json")) {
            GameConfig config = gson.fromJson(reader, GameConfig.class);

            // Set the slider and toggle values from the config
            fieldWidthSlider.setValue(config.getFieldWidth());
            fieldHeightSlider.setValue(config.getFieldHeight());
            gameLevelSlider.setValue(config.getGameLevel());
            musicToggle.setSelected(config.isMusicOn());
            soundEffectToggle.setSelected(config.isSoundEffectOn());
            aiPlayToggle.setSelected(config.isAiPlayOn());
            extendModeToggle.setSelected(config.isExtendModeOn());

            // Update the toggle button text based on their selected state
            musicToggle.setText(musicToggle.isSelected() ? "Off" : "On");
            soundEffectToggle.setText(soundEffectToggle.isSelected() ? "Off" : "On");
            aiPlayToggle.setText(aiPlayToggle.isSelected() ? "Off" : "On");
            extendModeToggle.setText(extendModeToggle.isSelected() ? "Off" : "On");

        } catch (IOException e) {
            e.printStackTrace();
            // If there is no configuration file, use default settings (already set by default).
        }
    }
}
