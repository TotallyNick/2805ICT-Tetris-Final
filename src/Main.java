import menu.MenuFacade;
import menu.SplashScreen;
import sounds.BackgroundMusic;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Main {

    private static boolean isMusicPaused = false;  // Flag to track if the music is paused
    private static BackgroundMusic backgroundMusic;

    public static void main(String[] args) {
        // Display Splash Screen
        new SplashScreen();

        // Create and display the menu using the facade pattern
        MenuFacade menu = new MenuFacade();
        menu.showMainMenu();

        // Play music
        backgroundMusic = new BackgroundMusic();
        backgroundMusic.playMusic("assets/music.wav");  // Provide the correct file path
        backgroundMusic.setVolume(0.45f);  // Set volume to 50%

        // Set up the key listener for 'M' to pause and resume the music
        setupKeyListener(menu.getMainFrame());  // Assuming MenuFacade returns the main JFrame

        // Keep the program running to listen for key events
        while (true) {
            // Infinite loop to simulate game loop or application running
            try {
                Thread.sleep(100);  // Adding sleep to avoid busy waiting
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Method to set up a Key Listener for 'M'
    private static void setupKeyListener(JFrame frame) {
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_M) {
                    System.out.println("I HAVE PRESSED M");
                    toggleMusic();  // Toggle music pause/resume on pressing 'M'
                }
            }
        });
    }

    // Method to toggle pause and resume for music
    private static void toggleMusic() {
        if (isMusicPaused) {
            System.out.println("Resuming music...");
            backgroundMusic.resumeMusic();  // Call the method to resume music
            isMusicPaused = false;
        } else {
            System.out.println("Pausing music...");
            backgroundMusic.pauseMusic();  // Call the method to pause music
            isMusicPaused = true;
        }
    }
}
