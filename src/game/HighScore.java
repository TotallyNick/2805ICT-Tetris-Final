package game;

public class HighScore {
    private final String playerName;
    private final int score;

    // Constructor
    public HighScore(String playerName, int score) {
        this.playerName = playerName;
        this.score = score;
    }

    // Getters =
    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }
}
