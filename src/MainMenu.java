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
        System.out.println();
        System.out.println("1. Add customer");
        System.out.println("2. Delete customer");
        System.out.println("3. Add insurance card");
        System.out.println("4. Delete insurance card");
        System.out.println("5. Add claim");
        System.out.println("6. Update claim");
        System.out.println("7. Delete claim");
        System.out.println("8. Read a claim");
        System.out.println("9. Read all claims");
        System.out.println("0. Exit");

        int response = Integer.parseInt(scanner.nextLine());
        switch (response) {
            case 1 -> systemManager.addCustomer();
            case 2 -> {
                System.out.println("Enter the customer cID you want to delete (c-xxxxxxx): ");
                String cID = scanner.nextLine();
                systemManager.deleteCustomer(cID);
            }
            case 3 -> systemManager.addInsuranceCard();
            case 4 -> {
                System.out.println("Enter the insurance card number you want to delete: ");
                String cardNumber = scanner.nextLine();
                systemManager.deleteInsuranceCard(cardNumber);
            }
            case 5 -> systemManager.addClaim();
            case 6 -> {
                System.out.println("Enter the claim fID you want to update (f-xxxxxxxxxx): ");
                String updateFID = scanner.nextLine();
                systemManager.updateClaim(updateFID);
            }
            case 7 -> {
                System.out.println("Enter the claim fID you want to delete (f-xxxxxxxxxx): ");
                String deleteFID = scanner.nextLine();
                systemManager.deleteClaim(deleteFID);
            }
            case 8 -> {
                System.out.println("Enter the claim fID you want to display: ");
                String readFID = scanner.nextLine();
                systemManager.getOneClaim(readFID);
            }
            case 9 -> systemManager.getAllClaims();
            case 0 -> {
                System.out.println("Exiting...");
                System.exit(0);
            }
            default -> System.out.println("Invalid option. Please try again");
        }
    }
}
