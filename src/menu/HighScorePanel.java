package menu;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.swing.*;
import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class HighScorePanel extends JPanel {
    private MenuFacade menuFacade;

    // Class to represent a high score entry with playerName and score fields
    private static class HighScore {
        String playerName;
        int score;
    }

    public HighScorePanel(MenuFacade menuFacade) {
        this.menuFacade = menuFacade;
        setLayout(new BorderLayout());

        // Title Label for High Scores
        JLabel highScoreLabel = new JLabel("High Scores");
        highScoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
        highScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(highScoreLabel, BorderLayout.NORTH); // Add title at the top

        // Load high scores from JSON and add table
        add(createScoreTable(), BorderLayout.CENTER);

        // Back Button to return to Main Menu
        JButton backButton = new JButton("Back to Main Menu");
        backButton.setPreferredSize(new Dimension(150, 50));

        // Panel to center the back button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(backButton);

        // Add the button panel to the bottom of the main panel
        add(buttonPanel, BorderLayout.SOUTH);

        // Back Button action
        backButton.addActionListener(e -> menuFacade.showMainMenu());
    }

    // Method to load high scores from a JSON file and create a JTable
    static JScrollPane createScoreTable() {
        String[] columnNames = {"Player Name", "Score"}; // Define table columns
        Object[][] data = loadHighScores(); // Load the high scores as a 2D array

        // Create JTable with the data
        JTable scoreTable = new JTable(data, columnNames);
        scoreTable.setEnabled(false); // Disable editing
        scoreTable.setFillsViewportHeight(true); // Ensure table fills available space

        // Set table properties for better appearance
        scoreTable.getTableHeader().setReorderingAllowed(false); // Disable column reordering
        scoreTable.setRowHeight(25); // Set row height

        // Wrap the table in a scroll pane for better usability
        return new JScrollPane(scoreTable);
    }


    // Method to load high scores from a JSON file using Gson and convert to 2D array
    private static Object[][] loadHighScores() {
        try {
            // Create a Gson instance
            Gson gson = new Gson();

            // Define the type for the list of HighScore objects
            Type highScoreListType = new TypeToken<List<HighScore>>() {}.getType();

            // Read the JSON file and parse it into a list of HighScore objects
            List<HighScore> highScores = gson.fromJson(new FileReader("highscores.json"), highScoreListType);

            // Convert the list of high scores to a 2D array for JTable
            Object[][] data = new Object[highScores.size()][2];
            for (int i = 0; i < highScores.size(); i++) {
                HighScore highScore = highScores.get(i);
                data[i][0] = highScore.playerName; // First column: player name
                data[i][1] = highScore.score;      // Second column: score
            }
            return data;
        } catch (IOException e) {
            e.printStackTrace();
            // Return an empty array if there's an error loading the scores
            return new Object[][]{{"Error", "Unable to load scores"}};
        }
    }

    public void refreshScores() {
        // Remove the old score table
        this.removeAll();

        // Recreate the layout and reload the high scores
        setLayout(new BorderLayout());

        // Title Label for High Scores
        JLabel highScoreLabel = new JLabel("High Scores");
        highScoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
        highScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(highScoreLabel, BorderLayout.NORTH); // Add title at the top

        // Load high scores from JSON and add table
        add(createScoreTable(), BorderLayout.CENTER);

        // Back Button to return to Main Menu
        JButton backButton = new JButton("Back to Main Menu");
        backButton.setPreferredSize(new Dimension(150, 50));

        // Panel to center the back button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(backButton);

        // Add the button panel to the bottom of the main panel
        add(buttonPanel, BorderLayout.SOUTH);

        // Back Button action
        backButton.addActionListener(e -> menuFacade.showMainMenu());

        // Revalidate and repaint to apply changes
        revalidate();
        repaint();
    }

}
