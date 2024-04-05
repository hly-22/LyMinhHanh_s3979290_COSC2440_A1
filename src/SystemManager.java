/**
 * @author <Ly Minh Hanh - s3979290>
 */

import java.math.BigDecimal;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class SystemManager implements ClaimProcessManager{
    private List<InsuranceCard> insuranceCards;
    private List<Claim> claims;
    CustomerViewText customerViewText;
    InsuranceCardViewText insuranceCardViewText;
    ClaimViewText claimViewText;
    private Scanner scanner;
    private FileManager fileManager;
    public SystemManager(CustomerViewText customerViewText, InsuranceCardViewText insuranceCardViewText, ClaimViewText claimViewText) {
        this.insuranceCards = new ArrayList<>();
        this.claims = new ArrayList<>();
        this.customerViewText = customerViewText;
        this.insuranceCardViewText = insuranceCardViewText;
        this.claimViewText = claimViewText;
        this.scanner = DataInput.getDataInput().getScanner();
        this.fileManager = new FileManager();
    }

    // customer management operations
    private boolean isValidCIDFormat(String cID) {
        String regex = "^c-[0-9]{7}$"; // c- followed by exactly 7 digits
        return cID.matches(regex);
    }
    public void addCustomer() {

        System.out.println("Enter a valid cID (c-xxxxxxx): ");
        String cID = scanner.nextLine();
        if (!isValidCIDFormat(cID)) {
            System.out.println("Invalid customer ID format.");
            return;
        }
        if (fileManager.findCustomerByID(cID) != null) {
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
            System.out.println("Enter your PolicyHolder's cID (c-xxxxxxx): ");
            String pCID = scanner.nextLine();

            Customer policyHolderDependent = fileManager.findCustomerByID(pCID);
            if (policyHolderDependent == null) {
                System.out.println("cID [" + pCID + "] is not found.");
                return;
            }

            String policyHolder = policyHolderDependent.getCID();

            Customer newDependent = new Dependent(cID, fullName, policyHolder);
            customerViewText.view((Dependent) newDependent);
            ((PolicyHolder) policyHolderDependent).addDependent((Dependent) newDependent);
            customerViewText.view((PolicyHolder) policyHolderDependent);
            fileManager.addCustomer(newDependent, policyHolderDependent);
            return;
        }

        Customer newPolicyHolder = new PolicyHolder(cID, fullName);
        customerViewText.view((PolicyHolder) newPolicyHolder);
        fileManager.addCustomer(newPolicyHolder, null);
    }

    public void deleteCustomer() {
        System.out.println("Enter the customer cID you want to delete (c-xxxxxxx): ");
        String cID = scanner.nextLine();

        Customer customerToDelete = fileManager.findCustomerByID(cID);
        if (customerToDelete != null) {
            if (customerToDelete instanceof PolicyHolder) {
                PolicyHolder policyHolder = (PolicyHolder) customerToDelete;
                System.out.println("Deleting this policy holder will also delete all their dependents. Proceed? (yes or no)");
                String response = scanner.nextLine().trim().toLowerCase();

                if (!response.equals("yes")) {
                    System.out.println("Deletion cancelled.");
                    return;
                }

                for (String dependentCID : policyHolder.getDependentList()) {
                    fileManager.deleteCustomer(dependentCID);
                }
            }
            fileManager.deleteCustomer(cID);
            System.out.println("Customer with cID [" + cID + "] has been deleted.");
        } else {
            System.out.println("Customer with cID [" + cID + "] is not found.");
        }
    }

    // insurance card management operations
    public boolean addInsuranceCard() {
        System.out.println("Does this customer exist in this system? (yes/no)");
        String response = scanner.nextLine();

        if (response.equalsIgnoreCase("no")) {
            System.out.println("Must create a customer first. Directing to add customer...");
            addCustomer();
        }

        System.out.println("Enter cID: ");
        String cID = scanner.nextLine();

        Customer customer = fileManager.findCustomerByID(cID);

        if (customer == null) {
            System.out.println("Customer with cID [" + cID + "] is not found.");
            return false;
        }

        if (customer.getInsuranceCard() != null) {
            System.out.println("Customer with cID [" + cID + "] already has an insurance card.");
            return false;
        }

        InsuranceCard insuranceCard = new InsuranceCard(customer);

        System.out.println("Enter the expiration date (yyyy-MM-dd):");
        LocalDate expirationDate;
        try {
            expirationDate = LocalDate.parse(scanner.nextLine());
            if (expirationDate.isBefore(LocalDate.now())) {
                System.out.println("Expiration date cannot be in the past.");
                return false;
            }
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date input. Please try again.");
            return false;
        }

        if (customer instanceof PolicyHolder) {
            System.out.println("Enter the policy owner:");
            String policyOwner = scanner.nextLine();

            insuranceCard.setPolicyOwner(policyOwner);
            insuranceCard.setExpirationDate(expirationDate);
        } else if (customer instanceof Dependent) {
            String policyHolderDependent = ((Dependent) customer).getPolicyHolder();
            Customer policyOwnerObject = fileManager.findCustomerByID(policyHolderDependent);
            String policyOwner = policyOwnerObject.getInsuranceCard().getPolicyOwner();

            insuranceCard.setPolicyOwner(policyOwner);
            insuranceCard.setExpirationDate(expirationDate);
        }

        customer.setInsuranceCard(insuranceCard);
        System.out.println("Insurance card added successfully.");
        insuranceCardViewText.view(insuranceCard);
        return true;
    }
    public boolean deleteInsuranceCard(String cardNumber) {
        InsuranceCard insuranceCardToRemove = null;
        for (InsuranceCard card : insuranceCards) {
            if (card.getCardNumber().equals(cardNumber)) {
                insuranceCardToRemove = card;
                break;
            }
        }

        if (insuranceCardToRemove == null) {
            System.out.println("Insurance card [" + cardNumber + "] is not found.");
            return false;
        }

        insuranceCards.remove(insuranceCardToRemove);

        Customer customer = insuranceCardToRemove.getCardHolder();
        if (customer != null) {
            customer.setInsuranceCard(null);
        }

        System.out.println("Insurance card [" + cardNumber + "] has been deleted.");
        return true;
    }

    // claim management operations
    @Override
    public void addClaim() {
        System.out.println("Enter insurance card number: ");
        String cardNumber = scanner.nextLine();

        InsuranceCard insuranceCard = fileManager.findInsuranceCardByCardNumber(cardNumber);
        if (insuranceCard == null) {
            System.out.println("Insurance Card [" + cardNumber + "] is not found.");
            System.out.println("Please enter a valid insurance card number or create one to add claim.");
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

        System.out.println("Enter the claim amount (USD):");
        BigDecimal claimAmount;
        try {
            claimAmount = new BigDecimal(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid claim amount format.");
            return;
        }

        Claim newClaim = new Claim(insuranceCard, examDate, claimAmount);
        claims.add(newClaim);
        claimViewText.view(newClaim);
        System.out.println("Claim added successfully.");
    }

    @Override
    public boolean updateClaim(String fID) {
        Claim claimToUpdate = fileManager.findClaimByID(fID);
        if (claimToUpdate == null) {
            System.out.println("Claim not found.");
            return false;
        }

        boolean updateAnother = true;
        while (updateAnother) {
            System.out.println("Choose what you want to update.");
            System.out.println("1. Exam date");
            System.out.println("2. Claim amount");
            System.out.println("3. Status");
            System.out.println("4. Receiver Banking Information");
            System.out.println("5. Add a document");
            System.out.println("6. Finish updating");

            int response = Integer.parseInt(scanner.nextLine());
            switch (response) {
                case 1:
                    System.out.println("Enter new exam date (yyyy-mm-dd): ");
                    LocalDate newDate = LocalDate.parse(scanner.nextLine());
                    claimToUpdate.setExamDate(newDate);
                    System.out.println("Exam date updated.");
                    break;
                case 2:
                    System.out.println("Enter new claim amount: ");
                    BigDecimal newClaimAmount = scanner.nextBigDecimal();
                    claimToUpdate.setClaimAmount(newClaimAmount);
                    System.out.println("Claim amount updated.");
                    break;
                case 3:
                    System.out.println("Please select new status: ");
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
                            System.out.println("Invalid input. Please try again.");
                            break;
                    }

                    if (newStatus != null) {
                        claimToUpdate.setStatus(newStatus);
                        System.out.println("Claim status updated.");
                    }
                    break;
                case 4:
                    System.out.println("Enter new receiver banking information (bank, name, number): ");
                    String[] bankingInfo = scanner.nextLine().split(",");
                    if (bankingInfo.length != 3) {
                        System.out.println("Invalid input format. Please provide bank, name, and number separated by commas.");
                        break;
                    }
                    String bank = bankingInfo[0].trim();
                    String name = bankingInfo[1].trim();
                    String number = bankingInfo[2].trim();
                    claimToUpdate.setReceiverBankingInfo(bank, name, number);
                    System.out.println("Receiver banking information updated.");
                    break;
                case 5:
                    System.out.println("Enter new document name: ");
                    String newDocumentName = scanner.nextLine();
                    claimToUpdate.addDocument(newDocumentName);
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
    public boolean deleteClaim(String fID) {
        Claim claim = fileManager.findClaimByID(fID);
        if (claim != null) {
            claims.remove(claim);
            return true;
        }

        return false;
    }

    @Override
    public void getOneClaim(String fID) {
        Claim claim = fileManager.findClaimByID(fID);
        claimViewText.view(claim);
    }

    @Override
    public void getAllClaims() {
        for (Claim claim : claims) {
            claimViewText.view(claim);
        }
    }

}
