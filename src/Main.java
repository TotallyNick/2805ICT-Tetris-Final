import menu.MenuFacade;

public class Main {
    public static void main(String[] args) {
        // Create and display the menu using the facade pattern
        MenuFacade menu = new MenuFacade();
        menu.showMainMenu();
    }
}
