/**
 * @author <Ly Minh Hanh - s3979290>
 */

// this is where the system starts, contain the text-based GUI

public class Main {
    public static void main(String[] args) {
        SystemManager systemManager = new SystemManager();
        MainMenu mainMenu = new MainMenu(systemManager);

        systemManager.initializeSystem();
        mainMenu.displayWelcomeMessage();
        mainMenu.displayMenu();
    }
}
