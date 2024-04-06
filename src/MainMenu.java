import java.sql.SQLOutput;
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

    public void displayWelcomeMessage() {
        System.out.println("=========================================");
        System.out.println("=   WELCOME TO CLAIM MANAGEMENT SYSTEM  =");
        System.out.println("=========================================");
        System.out.println();
    }
    public void displayMenu() {
        boolean exit = false;

        while (!exit) {
            System.out.println();
            System.out.println("--------------------");
            System.out.println("- Choose an option -");
            System.out.println("--------------------");
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
                case 2 -> systemManager.deleteCustomer();
                case 3 -> systemManager.addInsuranceCard();
                case 4 -> systemManager.deleteInsuranceCard();
                case 5 -> systemManager.addClaim();
                case 6 -> systemManager.updateClaim();
                case 7 -> systemManager.deleteClaim();
                case 8 -> systemManager.getOneClaim();
                case 9 -> systemManager.getAllClaims();
                case 0 -> {
                    System.out.println();
                    System.out.println("=====================");
                    System.out.println("= SEE YOU NEXT TIME =");
                    System.out.println("=====================");
                    exit = true;
                }
                default -> System.out.println("Invalid option. Please try again");
            }
        }

        systemManager.shutDownSystem();
        System.exit(0);
    }
}
