package game;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import config.GameConfig;
import sounds.SoundEffects;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TetrisGame extends JPanel implements ActionListener {
    private Board board;
    private Tetromino currentTetromino;
    private int currentX, currentY;
    private final Timer timer;
    private boolean isPaused = false;
    private boolean isMusicPaused = false;
    private boolean isSoundPaused = false;
    private int score = 0;
    private int linesRemoved = 0;
    private int difficulty = 1;
    private static SoundEffects soundEffect;
    public List<HighScore> highScores = new ArrayList<>();
    private boolean hasStarted = false; // Flag to indicate wait
    private boolean isGameOver = false; // Flag to indicate gamestate
    private boolean musicOn = true;
    private boolean soundOn = true;
    private GameConfig config;
    // Constructor to initialize the game
    public TetrisGame() {
        // Set layout and initialize board and game settings
        board = new Board();
        setLayout(null); // No layout manager needed for the game area

        setPreferredSize(new Dimension(500, 750));

        // Initialize the game by spawning a Tetromino and starting the timer
        spawnNewTetrimino();
        timer = new Timer(500, this);

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
        switch(linesCleared) {
            case 1 :
                score += 100;
                soundEffect = new SoundEffects();
                soundEffect.playEffect("assets/erase-line.wav");  // Provide the correct file path
                SoundEffects.setVolume(0.45f);  // Set volume to 50%
            case 2 :
                score += 300;
                soundEffect = new SoundEffects();
                soundEffect.playEffect("assets/erase-line.wav");  // Provide the correct file path
                SoundEffects.setVolume(0.45f);  // Set volume to 50%
            case 3 :
                score += 600;
                soundEffect = new SoundEffects();
                soundEffect.playEffect("assets/erase-line.wav");  // Provide the correct file path
                SoundEffects.setVolume(0.45f);  // Set volume to 50%
            case 4 :
                score += 1000; // tetris
                soundEffect = new SoundEffects();
                soundEffect.playEffect("assets/erase-line.wav");  // Provide the correct file path
                SoundEffects.setVolume(0.45f);  // Set volume to 50%
        }
        adjustGameSpeed();
    }

    private void adjustGameSpeed() {
        // Increase difficulty every multiple of 10 lines removed
        if (linesRemoved / 10 > difficulty - 1) {
            difficulty = linesRemoved / 10 + 1;  // Update difficulty
            int newDelay = Math.max(200 - (difficulty * 2), 100);  // Adjust the delay based on difficulty
            timer.setDelay(newDelay);
            // Play Sound Effect
            soundEffect = new SoundEffects();
            soundEffect.playEffect("assets/level-up.wav");  // Provide the correct file path
            SoundEffects.setVolume(0.80f);  // Set volume to 50%

        }
    }

    @Override
    public void addNotify() {
        super.addNotify();
        requestFocusInWindow();  // Ensure the component gains focus when shown
    }
    // Apply loaded configurations to the game settings
    private void applyConfigurations() {
        if (config != null) {
            // Adjust game settings based on the loaded config
            difficulty = config.getGameLevel();  // Set game level
            musicOn = config.isMusicOn();  // Set music toggle state
            soundOn = config.isSoundEffectOn();  // Set sound effect toggle state
        }
    }
    // Bind key inputs using InputMap and ActionMap
    // Bind key inputs using InputMap and ActionMap
    private void bindKeyInputs() {
        InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();

        // Start the game only on spacebar press
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "startGame");
        actionMap.put("startGame", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!hasStarted) {
                    timer.start();  // Start the game timer on spacebar press
                    hasStarted = true;  // Mark that the game has started
                }
            }
        });

        // Left arrow key -> move left
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "moveLeft");
        actionMap.put("moveLeft", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (hasStarted && board.canPlaceTetrimino(currentTetromino, currentX - 1, currentY)) {
                    currentX--;
                    repaint();
                    // Play sound effect
                    soundEffect = new SoundEffects();
                    soundEffect.playEffect("assets/move-turn.wav");  // Provide the correct file path
                    soundEffect.setVolume(0.25f);  // Set volume to 50%
                }
            }
        });

        // Right arrow key -> move right
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "moveRight");
        actionMap.put("moveRight", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (hasStarted && board.canPlaceTetrimino(currentTetromino, currentX + 1, currentY)) {
                    currentX++;
                    repaint();
                    // Play sound effect
                    soundEffect = new SoundEffects();
                    soundEffect.playEffect("assets/move-turn.wav");  // Provide the correct file path
                    soundEffect.setVolume(0.25f);  // Set volume to 50%
                }
            }
        });

        // Down arrow key -> move down faster
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "moveDown");
        actionMap.put("moveDown", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (hasStarted && board.canPlaceTetrimino(currentTetromino, currentX, currentY + 1)) {
                    currentY++;
                    repaint();
                    // Play sound effect
                    soundEffect = new SoundEffects();
                    soundEffect.playEffect("assets/move-turn.wav");  // Provide the correct file path
                    soundEffect.setVolume(0.25f);  // Set volume to 50%
                }
            }
        });

        // Up arrow key -> rotate Tetromino
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "rotate");
        actionMap.put("rotate", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (hasStarted) {
                    currentTetromino.rotate();
                    // Play sound effect
                    soundEffect = new SoundEffects();
                    soundEffect.playEffect("assets/move-turn.wav");  // Provide the correct file path
                    soundEffect.setVolume(0.25f);  // Set volume to 50%

                    if (!board.canPlaceTetrimino(currentTetromino, currentX, currentY)) {
                        // Undo rotation if placement fails
                        currentTetromino.rotate();
                        currentTetromino.rotate();
                        currentTetromino.rotate();
                    }
                    repaint();
                }
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

        // M key -> Pause/Unpause the game
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_M, 0), "music");
        actionMap.put("music", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleMusic();
            }
        });

        // S key -> Pause/Unpause the game
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), "sound");
        actionMap.put("sound", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleSound();
            }
        });

        // B key -> Reset the game if it is over
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_B, 0), "resetGame");
        actionMap.put("resetGame", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isGameOver) {  // Check if the game is over
                    resetGame();   // Call the resetGame() method
                }
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
    // Toggle Music
    private void toggleMusic() {
        isMusicPaused = !isMusicPaused;
        if (isMusicPaused) {
            sounds.BackgroundMusic.pauseMusic();
            musicOn = false;
        } else {
            sounds.BackgroundMusic.resumeMusic();
            musicOn = true;
        }
    }
    // Toggle Sound Effects
    private void toggleSound() {
        isSoundPaused = !isSoundPaused;
        if (isSoundPaused) {
            sounds.SoundEffects.pauseSound();
            soundOn = false;
        } else {
            sounds.SoundEffects.resumeSound();
            soundOn = true;
        }

        // STILL WORKING ON IT
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
        // Music Toggle
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Music Toggle " + musicOn, 350, 100);

        // Sound Toggle
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Sound Toggle " + soundOn, 350, 120);

        // Controls 1
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 15));
        g.drawString("Left arrow = Left, Right arrow = Right,  Up arrow = Turn " , 350, 140);
        // Controls 2
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 15));
        g.drawString("Down arrow = Drop, P = Pause, Spacebar = Start" , 350, 160);
        // Control 3
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 15));
        g.drawString("M = Toggle Music, S = Toggle Sound, B = Restart (After Game Over)" , 350, 180);

    }
    // Update and save the high scores after game over
    private void gameOver() {
        timer.stop();
        isGameOver = true; // ste game over true
        // Play sound Effect
        soundEffect = new SoundEffects();
        soundEffect.playEffect("assets/game-finish.wav");  // Provide the correct file path
        soundEffect.setVolume(0.45f);  // Set volume to 50%

        String playerName = JOptionPane.showInputDialog(this, "Game Over! Enter your name:");
        if (playerName != null && !playerName.isEmpty()) {
            updateHighScores(playerName, score);  // Only update with player name and score
        }
    }
    public void resetGame() {
        isGameOver = false;  // Reset the game over flag
        score = 0;           // Reset score
        linesRemoved = 0;     // Reset lines removed
        difficulty = 1;       // Reset difficulty
        board = new Board();  // Clear the board
        spawnNewTetrimino();  // Start with a new Tetromino
        timer.setDelay(500);  // Reset game speed to default
        hasStarted = false;   // Reset the start flag
        repaint();            // Repaint the game screen
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
    // Getter for the tests




}
