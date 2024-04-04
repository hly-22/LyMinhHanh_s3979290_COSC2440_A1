import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author <Ly Minh Hanh - s3979290>
 */

public class SystemManager implements ClaimProcessManager{
    private List<Customer> customers;
    private List<InsuranceCard> insuranceCards;
    private List<Claim> claims;
    CustomerViewText customerViewText;
    InsuranceCardViewText insuranceCardViewText;
    Scanner scanner;
    public SystemManager(CustomerViewText customerViewText, InsuranceCardViewText insuranceCardViewText) {
        this.customers = new ArrayList<>();
        this.insuranceCards = new ArrayList<>();
        this.claims = new ArrayList<>();
        this.customerViewText = customerViewText;
        this.insuranceCardViewText = insuranceCardViewText;
        this.scanner = DataInput.getDataInput().getScanner();
    }

    // customer management operations
    public void addCustomer() {

        System.out.println("Enter full name: ");
        String fullName = scanner.nextLine();

        System.out.println("Enter a customer type (PolicyHolder or Dependent): ");
        String customerType = scanner.nextLine();

        while (!customerType.equalsIgnoreCase("PolicyHolder") && !customerType.equalsIgnoreCase("Dependent")) {
            System.out.println("Invalid customer type. Please enter 'PolicyHolder' or 'Dependent'.");
            customerType = scanner.nextLine();
        }
        customerType = customerType.toLowerCase();


        if (customerType.equals("dependent")) {
            Customer policyHolder = null;
            boolean isValidCID = false;

            while (!isValidCID) {
                System.out.println("Enter your PolicyHolder's cID (c-xxxxxxx): ");
                String pCID = scanner.nextLine();

                for (Customer customer : customers) {
                    if (customer instanceof PolicyHolder && customer.getCID().equals(pCID)) {
                        policyHolder = customer;
                        isValidCID = true;
                        break;
                    }
                }

                if (!isValidCID) {
                    System.out.println("cID [" + pCID + "] is not found in the system. Please enter a different cID.");
                }
            }

            Customer newDependent = new Dependent(fullName, (PolicyHolder) policyHolder);
            customerViewText.view((Dependent) newDependent);
            customers.add(newDependent);
            return;
        }

        Customer newPolicyHolder = new PolicyHolder(fullName);
        customerViewText.view((PolicyHolder) newPolicyHolder);
        customers.add(newPolicyHolder);
    }

    public boolean deleteCustomer(String cID) {
        for (Customer customer : customers) {
            if (customer.getCID().equals(cID)) {
                customers.remove(customer);
                System.out.println("Customer with cID [" + cID + "] has been deleted.");
                return true;
            }
        }

        System.out.println("Customer with cID [" + cID + "] is not found. Please enter a different cID.");
        return false;
    }
    private Customer findCustomerByID(String cID) {
        for (Customer customer : customers) {
            if (customer.getCID().equals(cID)) {
                return customer;
            }
        }
        return null;
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

        Customer customer = findCustomerByID(cID);

        if (customer == null) {
            System.out.println("Customer with cID [" + cID + "] is not found.");
            return false;
        }

        if (customer.getInsuranceCard() != null) {
            System.out.println("Customer with cID [" + cID + "] already has an insurance card.");
            return false;
        }

        InsuranceCard insuranceCard = new InsuranceCard(customer);

        if (customer instanceof PolicyHolder) {
            System.out.println("Enter the policy owner:");
            String policyOwner = scanner.nextLine();
            System.out.println("Enter the expiration date (yyyy-MM-dd):");
            LocalDate expirationDate = LocalDate.parse(scanner.nextLine());

            insuranceCard.setPolicyOwner(policyOwner);
            insuranceCard.setExpirationDate(expirationDate);
        } else if (customer instanceof Dependent) {
            String policyOwner = ((Dependent) customer).getPolicyHolder().getInsuranceCard().getPolicyOwner();
            insuranceCard.setPolicyOwner(policyOwner);

            System.out.println("Enter the expiration date (yyyy-MM-dd):");
            LocalDate expirationDate = LocalDate.parse(scanner.nextLine());
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
    public boolean add(Claim claim) {
        return claims.add(claim);
    }

    @Override
    public boolean update(Claim claim) {
        return false;
    }

    @Override
    public boolean delete(Claim claim) {
        return claims.remove(claim);
    }

    @Override
    public Claim getOne(String fID) {
        for (Claim claim: claims) {
            if (claim.getFID().equals(fID)) {
                return claim;
            }
        }

        return null;
    }

    @Override
    public List<Claim> getAll() {
        return claims;
    }
}
