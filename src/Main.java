/**
 * @author <Ly Minh Hanh - s3979290>
 */

public class Main {
    public static void main(String[] args) {
        SystemManager systemManager = new SystemManager();
        MainMenu mainMenu = new MainMenu(systemManager);

        systemManager.initializeSystem();
        mainMenu.displayWelcomeMessage();
        mainMenu.displayMenu();
    }
}
