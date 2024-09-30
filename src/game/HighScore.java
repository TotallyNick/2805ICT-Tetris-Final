package game;

public class HighScore {
    private String playerName;
    private int score;

    // Constructor
    public HighScore(String playerName, int score) {
        this.playerName = playerName;
        this.score = score;
    }

    // Getters and Setters
    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }
}
