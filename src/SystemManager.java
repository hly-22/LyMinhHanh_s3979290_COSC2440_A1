/**
 * @author <Ly Minh Hanh - s3979290>
 */

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;


public class SystemManager implements ClaimProcessManager{
    private final FileManager fileManager;
    private List<Customer> customers;
    private List<InsuranceCard> insuranceCards;
    private List<Claim> claims;
    CustomerViewText customerViewText;
    InsuranceCardViewText insuranceCardViewText;
    ClaimViewText claimViewText;
    private final Scanner scanner;

    // Constructor to initialize the system
    public SystemManager() {
        this.fileManager = new FileManager();
        this.customerViewText = new CustomerViewText();
        this.insuranceCardViewText = new InsuranceCardViewText();
        this.claimViewText = new ClaimViewText();
        this.scanner = DataInput.getDataInput().getScanner();
    }

    // Initializing the system
    public void initializeSystem() {
        this.customers = fileManager.readAllCustomers();
        this.insuranceCards = fileManager.readAllInsuranceCards();
        this.claims = fileManager.readAllClaims();
    }
    // Shut down system
    public void shutDownSystem() {
        fileManager.writeAllCustomers(customers);
        fileManager.writeAllInsuranceCards(insuranceCards);
        fileManager.writeAllClaims(claims);
    }

    // customer management operations
    private boolean isValidCIDFormat(String cID) {
        String regex = "^c-[0-9]{7}$"; // c- followed by exactly 7 digits
        return cID.matches(regex);
    }
    public Customer findCustomerByID(String cID) {
        for (Customer customer : customers) {
            if (customer.getCID().equals(cID)) {
                return customer;
            }
        }
        return null;
    }
    public void addCustomer() {
        System.out.println();
        System.out.println("Enter a valid cID (c-xxxxxxx): ");
        String cID = scanner.nextLine();
        if (!isValidCIDFormat(cID)) {
            System.out.println("Invalid customer ID format.");
            return;
        }
        if (findCustomerByID(cID) != null) {
            System.out.println("CID already exists.");
            return;
        }

        System.out.println("Enter full name: ");
        String fullName = scanner.nextLine();

        System.out.println("Enter a customer type (PolicyHolder or Dependent): ");
        String customerType = scanner.nextLine();
        if (!customerType.equalsIgnoreCase("PolicyHolder") && !customerType.equalsIgnoreCase("Dependent")) {
            System.out.println("Invalid customer type.");
            return;
        }
        customerType = customerType.toLowerCase();
        if (customerType.equals("dependent")) {
            System.out.println();
            System.out.println("Enter your PolicyHolder's cID (c-xxxxxxx): ");
            String pCID = scanner.nextLine();
            Customer policyHolderDependent = findCustomerByID(pCID);
            if (policyHolderDependent == null) {
                System.out.println("cID [" + pCID + "] is not found.");
                return;
            }
            if (policyHolderDependent instanceof Dependent) {
                System.out.println("cID [" + cID + "] is not a Policy Holder.");
                return;
            }
            String policyHolder = policyHolderDependent.getCID();

            Customer newDependent = new Dependent(cID, fullName, policyHolder);
            ((PolicyHolder) policyHolderDependent).addDependent((Dependent) newDependent);
            customers.add(newDependent);

            customerViewText.view((Dependent) newDependent);
            customerViewText.view((PolicyHolder) policyHolderDependent);
            return;
        }

        Customer newPolicyHolder = new PolicyHolder(cID, fullName);
        customers.add(newPolicyHolder);

        customerViewText.view((PolicyHolder) newPolicyHolder);
    }
    public void deleteCustomer() {
        System.out.println();
        System.out.println("Enter the customer cID you want to delete (c-xxxxxxx): ");
        String cID = scanner.nextLine();

        Customer customerToDelete = findCustomerByID(cID);
        if (customerToDelete != null) {
            if (customerToDelete instanceof PolicyHolder policyHolder) {
                System.out.println("Deleting this policy holder will also delete all their dependents. Proceed? (yes or no)");
                String response = scanner.nextLine().trim().toLowerCase();

                if (!response.equals("yes")) {
                    System.out.println("Deletion cancelled.");
                    return;
                }

                for (String dependentCID : policyHolder.getDependentList()) {
                    Customer dependent = findCustomerByID(dependentCID);
                    customers.remove(dependent);
                }
            }

            if (customerToDelete instanceof Dependent) {
                PolicyHolder policyHolderDependent = (PolicyHolder) findCustomerByID(((Dependent) customerToDelete).getPolicyHolder());
                policyHolderDependent.removeDependent((Dependent) customerToDelete);
            }

            customers.remove(customerToDelete);
            System.out.println("Customer with cID [" + cID + "] has been deleted.");
        } else {
            System.out.println("Customer with cID [" + cID + "] is not found.");
        }
    }

    // insurance card management operations
    private boolean isValidCardFormat(String cardNumber) {
        String regex = "^[0-9]{10}$";
        return cardNumber.matches(regex);
    }
    public InsuranceCard findInsuranceCardByCardNumber(String cardNumber) {
        for (InsuranceCard insuranceCard : insuranceCards) {
            if (insuranceCard.getCardNumber().equals(cardNumber)) {
                return insuranceCard;
            }
        }
        return null;
    }
    public void addInsuranceCard() {
        System.out.println();
        System.out.println("Does this customer exist in this system? (yes/no)");
        String response = scanner.nextLine();

        if (response.equalsIgnoreCase("no")) {
            System.out.println("Must create a customer first. Directing to add customer...");
            addCustomer();
        }

        System.out.println("Enter cID: ");
        String cID = scanner.nextLine();

        Customer customer = findCustomerByID(cID);

        if (customer == null) {
            System.out.println("Customer with cID [" + cID + "] is not found.");
            return;
        }
        if (customer.getInsuranceCardNumber() == null) {
            customer.setInsuranceCardNumber("null");
        }
        if (!customer.getInsuranceCardNumber().equals("null")) {
            System.out.println("Customer with cID [" + cID + "] already has an insurance card.");
            return;
        }

        System.out.println("Enter a 10-digit card number: ");
        String cardNumber = scanner.nextLine();

        if (!isValidCardFormat(cardNumber)) {
            System.out.println("Invalid card number format.");
            return;
        }
        if (findInsuranceCardByCardNumber(cardNumber) != null) {
            System.out.println("Card number already exists.");
            return;
        }

        System.out.println("Enter the expiration date (yyyy-MM-dd):");
        LocalDate expirationDate;
        try {
            expirationDate = LocalDate.parse(scanner.nextLine());
            if (expirationDate.isBefore(LocalDate.now())) {
                System.out.println("Expiration date cannot be in the past.");
                return;
            }
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date input.");
            return;
        }

        InsuranceCard insuranceCard = new InsuranceCard(cardNumber, customer.getCID(), expirationDate);

        if (customer instanceof PolicyHolder) {
            System.out.println("Enter the policy owner:");
            String policyOwner = scanner.nextLine();
            insuranceCard.setPolicyOwner(policyOwner);
        } else if (customer instanceof Dependent) {
            String policyHolderDependent = ((Dependent) customer).getPolicyHolder();
            Customer policyOwnerObject = findCustomerByID(policyHolderDependent);
            String policyOwnerObjectInsuranceCard = policyOwnerObject.getInsuranceCardNumber();
            if (policyOwnerObjectInsuranceCard.equals("null")) {
                System.out.println("Your policy holder must have an insurance card first.");
                return;
            }
            String policyOwner = findInsuranceCardByCardNumber(policyOwnerObjectInsuranceCard).getPolicyOwner();
            insuranceCard.setPolicyOwner(policyOwner);
        }

        insuranceCards.add(insuranceCard);
        customer.setInsuranceCard(insuranceCard.getCardNumber());
        insuranceCardViewText.view(insuranceCard);
    }
    public void deleteInsuranceCard() {
        System.out.println();
        System.out.println("Enter the insurance card number you want to delete: ");
        String cardNumber = scanner.nextLine();

        InsuranceCard insuranceCardToDelete = findInsuranceCardByCardNumber(cardNumber);
        if (insuranceCardToDelete != null) {
            insuranceCards.remove(insuranceCardToDelete);
            Customer customer = findCustomerByID(insuranceCardToDelete.getCardHolder());
            if (customer != null) {
                customer.setInsuranceCard(null);
            }
            System.out.println("Insurance card [" + cardNumber + "] has been deleted.");
        } else {
            System.out.println("Insurance card [" + cardNumber + "] is not found.");
        }
    }

    // claim management operations
    private boolean isValidFIDFormat(String cID) {
        String regex = "^f-[0-9]{10}$"; // c- followed by exactly 7 digits
        return cID.matches(regex);
    }
    public Claim findClaimByID(String fID) {
        for (Claim claim : claims) {
            if (claim.getFID().equals(fID)) {
                return claim;
            }
        }
        return null;
    }
    @Override
    public void addClaim() {
        System.out.println();
        System.out.println("Enter a valid fID (f-xxxxxxxxxx): ");
        String fID = scanner.nextLine();
        if (!isValidFIDFormat(fID)) {
            System.out.println("Invalid claim ID format.");
            return;
        }
        if (findClaimByID(fID) != null) {
            System.out.println("FID already exists.");
            return;
        }

        System.out.println("Enter insurance card number: ");
        String cardNumber = scanner.nextLine();

        InsuranceCard insuranceCard = findInsuranceCardByCardNumber(cardNumber);
        if (insuranceCard == null) {
            System.out.println("Insurance Card [" + cardNumber + "] is not found.");
            return;
        }

        System.out.println("Enter the exam date (yyyy-mm-dd): ");
        LocalDate examDate;
        try {
            examDate = LocalDate.parse(scanner.nextLine());
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format.");
            return;
        }

        if (examDate.isAfter(LocalDate.now())) {
            System.out.println("The exam date cannot be in the future.");
            return;
        }

        System.out.println("Enter the claim amount (USD xx.xx):");
        BigDecimal claimAmount;
        try {
            claimAmount = new BigDecimal(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid claim amount format.");
            return;
        }

        Claim newClaim = new Claim(fID, insuranceCard, examDate, claimAmount);
        Customer customer = findCustomerByID(findInsuranceCardByCardNumber(cardNumber).getCardHolder());
        customer.addToClaimList(newClaim);

        System.out.println("Do you want to add any documents? (yes or no)");
        String response = scanner.nextLine().trim();

        if (response.equalsIgnoreCase("yes")) {
            boolean hasDocument = true;
            while (hasDocument) {
                System.out.println();
                System.out.println("Enter the name of the document with no space.");
                System.out.println("Or enter 'Done' when no more adding.");
                String documentName = scanner.nextLine();

                if (documentName.equalsIgnoreCase("done")) {
                    hasDocument = false;
                } else {
                    newClaim.addDocument(documentName);
                }
            }
        }

        System.out.println("Enter receiver banking information in the following format:");
        System.out.println("bank, name, number");

        try {
            String input = scanner.nextLine();
            String[] parts = input.split(",");
            if (parts.length < 3) {
                System.out.println("Invalid input. Please make sure you provide all required information.");
                return;
            }
            String bank = parts[0].trim();
            String name = parts[1].trim();
            String number = parts[2].trim();

            newClaim.setReceiverBankingInfo(bank, name, number);

        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Invalid input. Please make sure you provide all required information.");
            return;
        }

        claims.add(newClaim);
        claimViewText.view(newClaim);
    }
    @Override
    public boolean updateClaim() {
        System.out.println();
        System.out.println("Enter the claim fID you want to update (f-xxxxxxxxxx): ");
        String fID = scanner.nextLine();

        Claim claimToUpdate = findClaimByID(fID);
        if (claimToUpdate == null) {
            System.out.println("Claim not found.");
            return false;
        }

        boolean updateAnother = true;
        while (updateAnother) {
            System.out.println("-------------------------------");
            System.out.println("- What do you want to update? -");
            System.out.println("-------------------------------");
            System.out.println("1. Exam date");
            System.out.println("2. Claim amount");
            System.out.println("3. Status");
            System.out.println("4. Receiver Banking Information");
            System.out.println("5. Add a document");
            System.out.println("6. Finish updating");

            int response = Integer.parseInt(scanner.nextLine());
            switch (response) {
                case 1:
                    System.out.println();
                    System.out.println("Enter new exam date (yyyy-mm-dd): ");
                    try {
                        LocalDate newDate = LocalDate.parse(scanner.nextLine());
                        claimToUpdate.setExamDate(newDate);
                        System.out.println("Updated exam date: " + newDate);
                    } catch (DateTimeParseException e) {
                        System.out.println("Invalid date format.");
                        break;
                    }
                    break;
                case 2:
                    System.out.println();
                    System.out.println("Enter new claim amount (USD xx.xx):");
                    BigDecimal claimAmount;
                    try {
                        claimAmount = new BigDecimal(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid claim amount format.");
                        break;
                    }
                    claimToUpdate.setClaimAmount(claimAmount);
                    System.out.println("Updated claim amount: USD " + claimAmount);
                    break;
                case 3:
                    System.out.println();
                    System.out.println("Select a status to update: ");
                    System.out.println("1. PROCESSING");
                    System.out.println("2. DONE");

                    int choice = Integer.parseInt(scanner.nextLine());
                    ClaimStatus newStatus = null;

                    switch (choice) {
                        case 1:
                            newStatus = ClaimStatus.PROCESSING;
                            break;
                        case 2:
                            newStatus = ClaimStatus.DONE;
                            break;
                        default:
                            System.out.println("Invalid input.");
                            break;
                    }

                    if (newStatus != null) {
                        claimToUpdate.setStatus(newStatus);
                        System.out.println("Updated claim status: " + newStatus);
                    }
                    break;
                case 4:
                    System.out.println();
                    System.out.println("Enter new receiver banking information (bank, name, number): ");
                    try {
                        String input = scanner.nextLine();
                        String[] parts = input.split(",");
                        if (parts.length < 3) {
                            System.out.println("Invalid input. Please make sure you provide all required information.");
                            break;
                        }
                        String bank = parts[0].trim();
                        String name = parts[1].trim();
                        String number = parts[2].trim();

                        claimToUpdate.setReceiverBankingInfo(bank, name, number);
                        System.out.println("Updated receiver banking information: " + claimToUpdate.getReceiverBankingInfo());
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Invalid input. Please make sure you provide all required information.");
                        break;
                    }
                    break;
                case 5:
                    System.out.println();
                    System.out.println("Enter new document name: ");
                    String newDocumentName = scanner.nextLine();
                    claimToUpdate.addDocument(newDocumentName);
                    System.out.println("New document: " + claimToUpdate.getDocument(newDocumentName));
                    break;
                case 6:
                    updateAnother = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please choose again.");
            }
        }

        return true;
    }


    @Override
    public void deleteClaim() {
        System.out.println();
        System.out.println("Enter the claim fID you want to update (f-xxxxxxxxxx): ");
        String fID = scanner.nextLine();

        Claim claimToDelete = findClaimByID(fID);
        if (claimToDelete != null) {
            claims.remove(claimToDelete);

            Customer customer = findCustomerByID(claimToDelete.getInsuredPerson());
            if (customer != null) {
                customer.removeClaim(claimToDelete);
            }
            System.out.println("Claim [" + fID + "] has been deleted.");
        } else {
            System.out.println("Claim [" + fID + "] is not found.");
        }

    }

    @Override
    public void getOneClaim() {
        System.out.println();
        System.out.println("Enter the claim fID you want to update (f-xxxxxxxxxx): ");
        String fID = scanner.nextLine();
        Claim claim = findClaimByID(fID);
        claimViewText.view(claim);
    }

    @Override
    public void getAllClaims() {
        for (Claim claim : claims) {
            claimViewText.view(claim);
        }
    }

}
