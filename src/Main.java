/**
 * @author <Ly Minh Hanh - s3979290>
 */

// this is where the system starts, contain the text-based GUI

public class Main {
    public static void main(String[] args) {
        MainMenu mainMenu = new MainMenu(new SystemManager(new CustomerViewText(), new InsuranceCardViewText(), new ClaimViewText()));
        mainMenu.displayWelcomeMessage();
        mainMenu.displayMenu();
    }
}
