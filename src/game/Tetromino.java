package game;

import java.awt.*;
import java.util.Random;

public class Tetromino {
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
    private Color assignColor(Shape shape) {
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
