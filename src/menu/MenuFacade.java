package menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MenuFacade {
    private JFrame mainFrame;
    private MainMenuPanel mainMenuPanel;
    private ConfigMenuPanel configMenuPanel;


    public MenuFacade(){
        //Initialise JFrame
       mainFrame = new JFrame("Tetris Menu");
       mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       mainFrame.setSize(550,800);
       mainFrame.setLayout(new CardLayout());

       // Initialise Menu Panels
        mainMenuPanel = new MainMenuPanel(this);
        configMenuPanel = new ConfigMenuPanel(this);

        // Add Panels to the Frame
        mainFrame.add(mainMenuPanel, "Main Menu Panel");
        mainFrame.add(configMenuPanel, "Config Menu Panel");

        // Display main menu
        showMainMenu();
    }

    // Display Main Menu Method
    public void showMainMenu(){
        CardLayout c1 = (CardLayout) mainFrame.getContentPane().getLayout();
        c1.show(mainFrame.getContentPane(), "Main Menu");
        mainFrame.setVisible(true);
    }

    // Display Config Menu Panel Method
    public void showConfigMenu(){
    CardLayout c1 =(CardLayout) mainFrame.getContentPane().getLayout();
    c1.show(mainFrame.getContentPane(), "Config Menu");
    }

    // quit Menu Method
    public void quitGame(){
        JOptionPane.showMessageDialog(mainFrame, "qutting the game... ");
        System.exit(0);
    }

}
