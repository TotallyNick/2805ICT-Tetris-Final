import menu.MenuFacade;
import menu.SplashScreen;
import menu.BackgroundMusic;
import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Main {


    public static void main(String[] args) {
        // Display Splash Screen
        new SplashScreen();

        // Create and display the menu using the facade pattern
        MenuFacade menu = new MenuFacade();
        menu.showMainMenu();

        // Play music
        BackgroundMusic backgroundMusic = new BackgroundMusic();
        backgroundMusic.playMusic("assets/music.wav");  // Provide the correct file path
        backgroundMusic.setVolume(0.2f);  // Set volume to 50%
        while(true){


        }

    }

}
