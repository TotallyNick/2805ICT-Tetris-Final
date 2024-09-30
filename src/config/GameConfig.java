package config;

public class GameConfig {
    private int fieldWidth;
    private int fieldHeight;
    private int gameLevel;
    private boolean musicOn;
    private boolean soundEffectOn;
    private boolean aiPlayOn;
    private boolean extendModeOn;

    // Constructor
    public GameConfig(int fieldWidth, int fieldHeight, int gameLevel, boolean musicOn, boolean soundEffectOn, boolean aiPlayOn, boolean extendModeOn) {
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
        this.gameLevel = gameLevel;
        this.musicOn = musicOn;
        this.soundEffectOn = soundEffectOn;
        this.aiPlayOn = aiPlayOn;
        this.extendModeOn = extendModeOn;
    }

    // Getters and setters
    public int getFieldWidth() { return fieldWidth; }
    public void setFieldWidth(int fieldWidth) { this.fieldWidth = fieldWidth; }

    public int getFieldHeight() { return fieldHeight; }
    public void setFieldHeight(int fieldHeight) { this.fieldHeight = fieldHeight; }

    public int getGameLevel() { return gameLevel; }
    public void setGameLevel(int gameLevel) { this.gameLevel = gameLevel; }

    public boolean isMusicOn() { return musicOn; }
    public void setMusicOn(boolean musicOn) { this.musicOn = musicOn; }

    public boolean isSoundEffectOn() { return soundEffectOn; }
    public void setSoundEffectOn(boolean soundEffectOn) { this.soundEffectOn = soundEffectOn; }

    public boolean isAiPlayOn() { return aiPlayOn; }
    public void setAiPlayOn(boolean aiPlayOn) { this.aiPlayOn = aiPlayOn; }

    public boolean isExtendModeOn() { return extendModeOn; }
    public void setExtendModeOn(boolean extendModeOn) { this.extendModeOn = extendModeOn; }
}
