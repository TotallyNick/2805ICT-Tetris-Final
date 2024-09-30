package game;

import java.awt.*;
import java.util.Arrays;

public class Board {
    public int width = 10;  // The width of the board (in blocks)
    public int height = 20; // The height of the board (in blocks)
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
