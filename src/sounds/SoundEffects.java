package sounds;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundEffects {

    private Clip clip;
    private FloatControl volumeControl;
    private long clipTimePosition;

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
}
