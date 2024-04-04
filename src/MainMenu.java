import javax.xml.crypto.Data;
import java.util.Scanner;

/**
 * @author <Ly Minh Hanh - s3979290>
 */

public class MainMenu {
    SystemManager systemManager;
    Scanner scanner;
    public MainMenu(SystemManager systemManager) {
        this.systemManager = systemManager;
        this.scanner = DataInput.getDataInput().getScanner();
    }
    public void displayMenu() {
        System.out.println("---Welcome to the system---");
        System.out.println("Please choose an option.");
        System.out.println("1. Add customer");
        System.out.println("2. Delete customer");
        System.out.println("3. Add insurance card");
        System.out.println("4. Delete insurance card");
        System.out.println("5. Add claim");
        System.out.println("6. Delete claim");
        System.out.println("7. Update claim");
        System.out.println("8. Read a claim");
        System.out.println("9. Read all claims");
        System.out.println("0. Exit");

        int response = Integer.parseInt(scanner.nextLine());
        switch (response) {
            case 1:
                systemManager.addCustomer();
                break;
            case 2:
                System.out.println("Enter the customer cID you want to delete: ");
                String cID = scanner.nextLine();
                systemManager.deleteCustomer(cID);
                break;
            case 3:
                boolean a = systemManager.addInsuranceCard();
                break;
            case 4:
                System.out.println("Enter the insurance card number you want to delete: ");
                String cardNumber = scanner.nextLine();
                systemManager.deleteInsuranceCard(cardNumber);
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
            case 9:
                break;
            case 0:
                System.out.println("Exiting...");
                System.exit(0);
            default:
                System.out.println("Invalid option. Please try again");
        }
    }
}
