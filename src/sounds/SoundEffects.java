package sounds;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundEffects {

    private static Clip clip;
    private FloatControl volumeControl;
    private static float volume = 0.25f; // Default volume
    private static int pausePosition = 0; // To save the pause position


    public void playEffect(String filePath) {
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
                clip.start();
            } else {
                System.out.println("Can't find the file");
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }


    // Set the volume, where value is between 0 (mute) and 1 (full volume)
    public static void setVolume(float value) {
        if (clip != null && clip.isControlSupported(javax.sound.sampled.FloatControl.Type.MASTER_GAIN)) {
            javax.sound.sampled.FloatControl volumeControl = (javax.sound.sampled.FloatControl)
                    clip.getControl(javax.sound.sampled.FloatControl.Type.MASTER_GAIN);
            float min = volumeControl.getMinimum();
            float max = volumeControl.getMaximum();
            float newVolume = min + (max - min) * value;  // Scale between min and max
            volumeControl.setValue(newVolume);
            volume = value; // Save the volume setting
        } else {
            System.out.println("Volume control is not supported or clip is not initialized.");
        }
    }

    // Pause the music by stopping the clip and saving its position
    public static void pauseSound() {
        if (clip != null && clip.isRunning()) {
            pausePosition = clip.getFramePosition();  // Save the current position
            clip.stop();  // Pause the sound
        }
    }

    // Resume the music by restarting the clip from the saved position
    public static void resumeSound() {
        if (clip != null && !clip.isRunning()) {
            clip.setFramePosition(pausePosition);  // Resume from the saved position
            clip.start();  // Start playing the sound again
            setVolume(volume);  // Restore the volume setting
        }
    }
}
