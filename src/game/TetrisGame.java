package game;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

enum Shape {
    I, J, L, O, S, T, Z // Enum representing different Tetromino shapes
}

class Tetromino {
    private int[][] coordinates;  // Holds the shape's coordinates in a 2D array
    private final Color color;    // The color of the Tetromino

    // Predefined 2D arrays representing the block structures of the Tetromino shapes
    private static final int[][][] SHAPES = {
            {{1, 1, 1, 1}},  // Shape I (line)
            {{1, 1, 1}, {0, 0, 1}},  // Shape J (L)
            {{1, 1, 1}, {1, 0, 0}},  // Shape L
            {{1, 1}, {1, 1}},  // Shape O (square)
            {{0, 1, 1}, {1, 1, 0}},  // Shape S (Z)
            {{0, 1, 0}, {1, 1, 1}},  // Shape T
            {{1, 1, 0}, {0, 1, 1}}   // Shape Z (reverse Z)
    };

    // Constructor that initializes a random Tetromino shape with a random color
    public Tetromino() {
        Random rand = new Random();
        Shape shape = Shape.values()[rand.nextInt(Shape.values().length)];
        this.coordinates = SHAPES[shape.ordinal()];
        this.color = assignColor(shape);
    }

    // assign color spefic to the shape
    private Color assignColor(Shape shape){
        return switch (shape) {
            case I -> new Color(0, 0, 255);  // Blue (I)
            case J -> new Color(0, 0, 139);  // Dark Blue (J)
            case L -> new Color(255, 165, 0);  // Orange (L)
            case O -> new Color(255, 255, 0);  // Yellow (O)
            case S -> new Color(0, 255, 0);  // Green (S)
            case T -> new Color(128, 0, 128);  // Purple (T)
            case Z -> new Color(255, 0, 0);  // Red (Z)
        };
    }

    // Getter for the Tetromino's shape coordinates
    public int[][] getCoordinates() {
        return coordinates;
    }

    // Getter for the Tetromino's color
    public Color getColor() {
        return color;
    }

    // Rotates the Tetromino 90 degrees clockwise
    public void rotate() {
        int[][] newCoordinates = new int[coordinates[0].length][coordinates.length];
        for (int i = 0; i < coordinates.length; i++) {
            for (int j = 0; j < coordinates[i].length; j++) {
                newCoordinates[j][coordinates.length - 1 - i] = coordinates[i][j];
            }
        }
        coordinates = newCoordinates; // Update the Tetromino's shape with the rotated coordinates
    }
}

class Board {
    private final int width = 10;  // The width of the board (in blocks)
    private final int height = 20; // The height of the board (in blocks)
    private final Color[][] grid;  // 2D grid to store the state (color) of each block on the board

    // Initializes an empty grid of the board
    public Board() {
        grid = new Color[height][width];
    }

    // Checks if the Tetromino can be placed at the specified (x, y) position
    public boolean canPlaceTetrimino(Tetromino tetromino, int x, int y) {
        for (int i = 0; i < tetromino.getCoordinates().length; i++) {
            for (int j = 0; j < tetromino.getCoordinates()[i].length; j++) {
                if (tetromino.getCoordinates()[i][j] == 1) {  // Only check cells with value 1
                    // Check if it's out of bounds or colliding with an existing block
                    if (x + j < 0 || x + j >= width || y + i >= height || (y + i >= 0 && grid[y + i][x + j] != null)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    // Places the Tetromino on the board at the specified (x, y) position
    public void placeTetrimino(Tetromino tetromino, int x, int y) {
        for (int i = 0; i < tetromino.getCoordinates().length; i++) {
            for (int j = 0; j < tetromino.getCoordinates()[i].length; j++) {
                if (tetromino.getCoordinates()[i][j] == 1) {  // Only place blocks with value 1
                    grid[y + i][x + j] = tetromino.getColor();  // Set the color on the grid
                }
            }
        }
    }

    // Clears all completed lines from the board
    public int clearLines() {
        int linesCleared = 0;
        for (int i = 0; i < height; i++) {
            if (isLineFull(i)) {  // Check if a line is complete
                clearLine(i);  // Clear the full line
                linesCleared++;
                i--;  // Recheck the current line after clearing
            }
        }
        return linesCleared; // Return number of lines clared
    }

    // Checks if a row (y) is fully filled
    private boolean isLineFull(int y) {
        for (int j = 0; j < width; j++) {
            if (grid[y][j] == null) {
                return false;
            }
        }
        return true;
    }

    // Clears a specific line (y) and shifts the above lines down
    private void clearLine(int y) {
        for (int i = y; i > 0; i--) {
            System.arraycopy(grid[i - 1], 0, grid[i], 0, width);  // Shift rows down
        }
        clearLineTop();  // Clear the topmost row after shifting
    }

    // Clears the top row by setting all its cells to null
    private void clearLineTop() {
        Arrays.fill(grid[0], null);
    }

    // Getter for the board's grid
    public Color[][] getGrid() {
        return grid;
    }

    // Getter for the board's width
    public int getWidth() {
        return width;
    }

    // Getter for the board's height
    public int getHeight() {
        return height;
    }
}

public class TetrisGame extends JPanel implements ActionListener {
    private final Board board;
    private Tetromino currentTetromino;
    private int currentX, currentY;
    private final Timer timer;
    private boolean isPaused = false;
    private int score = 0;
    private int linesRemoved = 0;
    private int difficulty = 1;
    public List<HighScore> highScores = new ArrayList<>();
    // Constructor to initialize the game
    public TetrisGame() {
        // Set layout and initialize board and game settings
        board = new Board();
        setLayout(null); // No layout manager needed for the game area

        // Set the preferred size for the game area (this is important for display)
        setPreferredSize(new Dimension(300, 600));

        // Initialize the game by spawning a Tetromino and starting the timer
        spawnNewTetrimino();
        timer = new Timer(500, this);
        timer.start();

        // Enable key input handling for controlling the Tetromino
        setFocusable(true);

        // Bind key inputs using InputMap and ActionMap
        bindKeyInputs();

        // Request focus when the component is visible
        requestFocusInWindow();

        loadHighScores(); // load existing high scores

    }

    private void updateScore(int linesCleared){
        linesRemoved += linesCleared;
        switch(linesCleared){
            case 1 -> score += 100;
            case 2 -> score += 300;
            case 3 -> score += 600;
            case 4 -> score += 1000; // tetris
            default -> {
            } // add nothing
        }
        adjustGameSpeed();
    }

    private void adjustGameSpeed() {
        // Increase difficulty every multiple of 10 lines removed
        if (linesRemoved / 10 > difficulty - 1) {
            difficulty = linesRemoved / 10 + 1;  // Update difficulty
            int newDelay = Math.max(200 - (difficulty * 20), 100);  // Adjust the delay based on difficulty
            timer.setDelay(newDelay);
        }
    }

    @Override
    public void addNotify() {
        super.addNotify();
        requestFocusInWindow();  // Ensure the component gains focus when shown
    }

    // Bind key inputs using InputMap and ActionMap
    private void bindKeyInputs() {
        InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();

        // Left arrow key -> move left
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "moveLeft");
        actionMap.put("moveLeft", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (board.canPlaceTetrimino(currentTetromino, currentX - 1, currentY)) {
                    currentX--;
                    repaint();
                }
            }
        });

        // Right arrow key -> move right
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "moveRight");
        actionMap.put("moveRight", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (board.canPlaceTetrimino(currentTetromino, currentX + 1, currentY)) {
                    currentX++;
                    repaint();
                }
            }
        });

        // Down arrow key -> move down faster
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "moveDown");
        actionMap.put("moveDown", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (board.canPlaceTetrimino(currentTetromino, currentX, currentY + 1)) {
                    currentY++;
                    repaint();
                }
            }
        });

        // Up arrow key -> rotate tetromino
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "rotate");
        actionMap.put("rotate", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentTetromino.rotate();
                if (!board.canPlaceTetrimino(currentTetromino, currentX, currentY)) {
                    // Undo rotation if placement fails
                    currentTetromino.rotate();
                    currentTetromino.rotate();
                    currentTetromino.rotate();
                }
                repaint();
            }
        });

        // P key -> Pause/Unpause the game
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_P, 0), "pause");
        actionMap.put("pause", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                togglePause();
            }
        });
    }

    // Spawns a new Tetromino at the top of the board
    private void spawnNewTetrimino() {
        currentTetromino = new Tetromino();
        currentX = 4;  // Start near the center of the board
        currentY = 0;  // Start at the top

        // End game if the Tetromino can't be placed at the initial position
        if (!board.canPlaceTetrimino(currentTetromino, currentX, currentY)) {
            timer.stop();
            gameOver();
        }
    }

    // Toggles the pause state of the game
    private void togglePause() {
        isPaused = !isPaused;
        if (isPaused) {
            timer.stop();
            JOptionPane.showMessageDialog(this, "Game Paused. Press 'P' to continue.");
        } else {
            timer.start();
        }
    }

    // Called every timer tick to move the Tetromino down if the game is not paused
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!isPaused) {
            if (board.canPlaceTetrimino(currentTetromino, currentX, currentY + 1)) {
                currentY++;
            } else {
                board.placeTetrimino(currentTetromino, currentX, currentY);
                int linesCleared = board.clearLines(); // finds number of lines cleared
                updateScore(linesCleared); // updates score
                spawnNewTetrimino();
            }
            repaint();
        }
    }

    // Paint the board and the current Tetromino
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard(g);
        drawTetrimino(g);
        drawGrid(g);
        drawStatus(g); // draw Score
    }

    // Draw the board's existing blocks
    private void drawBoard(Graphics g) {
        Color[][] grid = board.getGrid();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] != null) {
                    g.setColor(grid[i][j]);
                    g.fillRect(j * 30, i * 30, 30, 30);
                }
            }
        }
    }

    // Draw the falling Tetromino
    private void drawTetrimino(Graphics g) {
        g.setColor(currentTetromino.getColor());
        for (int i = 0; i < currentTetromino.getCoordinates().length; i++) {
            for (int j = 0; j < currentTetromino.getCoordinates()[i].length; j++) {
                if (currentTetromino.getCoordinates()[i][j] == 1) {
                    g.fillRect((currentX + j) * 30, (currentY + i) * 30, 30, 30);
                }
            }
        }
    }

    // Draw the grid lines for visual aid
    private void drawGrid(Graphics g) {
        g.setColor(Color.BLACK);
        for (int i = 0; i < board.getHeight(); i++) {
            for (int j = 0; j < board.getWidth(); j++) {
                g.drawRect(j * 30, i * 30, 30, 30);
            }
        }
    }

    private void drawStatus(Graphics g) {
        // Type of User
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("User: Human",350, 20);
        // Score
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Level: " + difficulty, 350, 40);
        // Score
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + score, 350, 60);
        // Lines Cleared
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Lines Cleared " + linesRemoved, 350, 80);
    }
    // Update and save the high scores after game over
    private void gameOver() {
        timer.stop();
        String playerName = JOptionPane.showInputDialog(this, "Game Over! Enter your name:");
        if (playerName != null && !playerName.isEmpty()) {
            updateHighScores(playerName, score);  // Only update with player name and score
        }
    }

    // Method to update the high score list
    private void updateHighScores(String playerName, int score) {
        highScores.add(new HighScore(playerName, score));  // Append new score
        highScores.sort((a, b) -> Integer.compare(b.getScore(), a.getScore())); // Sort scores in descending order

        if (highScores.size() > 10) {
            highScores = highScores.subList(0, 10); // Keep only the top 10 scores
        }

        saveHighScores(); // Save the updated high scores to a file
    }


    // Load high scores from JSON file
    private void loadHighScores() {
        try (Reader reader = new FileReader("highscores.json")) {
            Type highScoreListType = new TypeToken<ArrayList<HighScore>>() {}.getType();
            highScores = new Gson().fromJson(reader, highScoreListType);
        } catch (IOException e) {
            highScores = new ArrayList<>(); // If the file doesn't exist, create an empty list
        }
    }

    // Save high scores to JSON file
    private void saveHighScores() {
        try (Writer writer = new FileWriter("highscores.json")) {
            new Gson().toJson(highScores, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
