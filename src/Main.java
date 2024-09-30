import menu.MenuFacade;
import menu.SplashScreen;

public class Main {
    public static void main(String[] args) {
        // Display Splash Screen
        new SplashScreen();

        // Create and display the menu using the facade pattern
        MenuFacade menu = new MenuFacade();
        menu.showMainMenu();
    }
}
