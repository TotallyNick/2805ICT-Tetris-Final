package menu;

import javax.swing.*;
import java.awt.*;

public class SplashScreen extends JWindow {
    private static final int SPLASH_WIDTH = 400;
    private static final int SPLASH_HEIGHT = 400;

    public SplashScreen() {
        // Create a transparent panel for the splash content
        JPanel content = (JPanel) getContentPane();
        content.setOpaque(false);  // Make panel background transparent

        // Set the window size and position it in the center of the screen
        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        setBounds((screenWidth - SPLASH_WIDTH) / 2, (screenHeight - SPLASH_HEIGHT) / 2, SPLASH_WIDTH, SPLASH_HEIGHT);

        // Set window opacity (between 0.0f and 1.0f), where 0.0f is fully transparent
        setOpacity(0.85f);  // Slight transparency for the entire window

        // Add an image or game logo (it will be visible even if the background is transparent)
        JLabel label = new JLabel(new ImageIcon("assets/tetris_logo.png"));
        content.add(label, BorderLayout.CENTER);

        // Add a loading message or game title
        JLabel message = new JLabel("Loading Tetris...", JLabel.CENTER);
        message.setFont(new Font("Sans-Serif", Font.BOLD, 14));
        message.setForeground(Color.BLACK); // Keep text visible
        content.add(message, BorderLayout.SOUTH);

        // Make splash screen visible
        setVisible(true);

        // Delay the splash screen display (e.g., 3 seconds)
        try {
            Thread.sleep(3000); // Show splash screen for 3 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Close the splash screen after the delay
        setVisible(false);
        dispose(); // Release splash screen resources
    }
}
