package menu;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class BackgroundMusic {

    private Clip clip;
    private FloatControl volumeControl;
    private long clipTimePosition;

    public void playMusic(String filePath) {
        try {
            File musicPath = new File(filePath);
            if (musicPath.exists()) {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicPath);
                clip = AudioSystem.getClip();
                clip.open(audioStream);
                clip.loop(Clip.LOOP_CONTINUOUSLY);  // Loop the music
                clip.start();
            } else {
                System.out.println("Can't find the file");
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // Pause the music by stopping the clip and saving its position
    public void pauseMusic() {
        if (clip != null && clip.isRunning()) {
            clipTimePosition = clip.getMicrosecondPosition(); // Save current position
            clip.stop(); // Pause the clip
        }
    }

    // Resume the music by restarting the clip from the saved position
    public void resumeMusic() {
        if (clip != null) {
            clip.setMicrosecondPosition(clipTimePosition); // Set clip position to where it was paused
            clip.start(); // Resume playing
        }
    }


    // Set the volume, where value is between 0 (mute) and 1 (full volume)
    public void setVolume(float value) {
        if (volumeControl != null) {
            float min = volumeControl.getMinimum();
            float max = volumeControl.getMaximum();
            float newVolume = min + (max - min) * value;  // Scale between min and max
            volumeControl.setValue(newVolume);
        }
    }

    public static void main(String[] args) {

        BackgroundMusic backgroundMusic = new BackgroundMusic();
        backgroundMusic.playMusic("assets/music.wav");  // Provide the correct file path
        backgroundMusic.setVolume(0.5f);  // Set volume to 50%
        while(true){}
    }
}
