package sounds;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class BackgroundMusic {

    private static Clip clip;
    private FloatControl volumeControl;
    private static long clipTimePosition;

    public void playMusic(String filePath) {
        try {
            File musicPath = new File(filePath);
            if (musicPath.exists()) {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicPath);
                clip = AudioSystem.getClip();
                clip.open(audioStream);

                // Initialize volume control AFTER the clip is opened
                if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                    volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                } else {
                    System.out.println("Volume control not supported.");
                }

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
    public static void pauseMusic() {
        if (clip != null && clip.isRunning()) {
            clipTimePosition = clip.getMicrosecondPosition(); // Save current position
            clip.stop(); // Pause the clip
        }
    }

    // Resume the music by restarting the clip from the saved position
    public static void resumeMusic() {
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
        } else {
            System.out.println("Volume control is not supported or not initialized.");
        }
    }

    // Stop the music entirely
    public void stopMusic() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }
}
