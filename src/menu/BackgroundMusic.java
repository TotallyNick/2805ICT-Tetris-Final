package menu;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class BackgroundMusic {

    private Clip clip;
    private FloatControl volumeControl;

    public void playMusic(String filePath) {
        try {
            File musicPath = new File(filePath);
            if (musicPath.exists()) {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicPath);
                clip = AudioSystem.getClip();
                clip.open(audioStream);

                // Access the volume control
                volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

                clip.loop(Clip.LOOP_CONTINUOUSLY);  // Loop the music
                clip.start();
            } else {
                System.out.println("Can't find the file");
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
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

    public void stopMusic() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    public static void main(String[] args) {

        BackgroundMusic backgroundMusic = new BackgroundMusic();
        backgroundMusic.playMusic("music.wav");  // Provide the correct file path
        backgroundMusic.setVolume(0.5f);  // Set volume to 50%
        while(true){}
    }
}
