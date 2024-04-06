import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileManager {
    private static final String CUSTOMER_FILE_PATH = System.getProperty("user.dir") + "/src/customers.txt";
    private static final String INSURANCE_CARD_FILE_PATH = System.getProperty("user.dir") + "/src/insurance_cards.txt";
    private static final String CLAIM_FILE_PATH = System.getProperty("user.dir") + "/src/claims.txt";
    private static final String ATTRIBUTE_DELIMITER = ",";
    private static final String LIST_DELIMITER = "/";


    // Customers File Read and Write Methods
    public List<Customer> readAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(CUSTOMER_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("#") || line.isEmpty()) {
                    continue;
                }

                Customer customer = parseCustomer(line);
                if (customer != null) {
                    customers.add(customer);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return customers;
    }

    private Customer parseCustomer(String line) {
        String[] tokens = line.split(ATTRIBUTE_DELIMITER);
        if (tokens.length < 6) {
            System.err.println("Invalid customer data: " + line);
            return null;
        }

        String cID = tokens[1];
        String fullName = tokens[2];
        String cardNumber = tokens[3];
//      List<Claim> claimList = getClaimListFromString(tokens[4]);

        if (tokens[0].equals("Policy Holder")) {
            List<String> dependentList = getDependentsFromString(tokens[5]);
            return new PolicyHolder(cID, fullName, cardNumber, null, dependentList);
        } else {
            String policyHolder = tokens[5];
            return new Dependent(cID, fullName, cardNumber, null, policyHolder);
        }
    }

    public void writeAllCustomers(List<Customer> customers) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CUSTOMER_FILE_PATH))) {
            bw.write("""
                    # Customers File
                    # Format of Policy Holder: CID, FullName, InsuranceCard, ClaimList, DependentList
                    # Format of Dependent: CID, FullName, InsuranceCard, ClaimList, PolicyHolderCID
                    
                    """);
            for (Customer customer : customers) {
                if (customer instanceof PolicyHolder) {
                    PolicyHolder policyHolder = (PolicyHolder) customer;
                    String customerLine = String.join(ATTRIBUTE_DELIMITER,
                            "Policy Holder",policyHolder.getCID(),
                            policyHolder.getFullName(),
                            policyHolder.getInsuranceCardNumber(),
                            getClaimListAsString(policyHolder.getClaimList()),
                            getDependentsAsString(policyHolder.getDependentList()));
                    bw.write(customerLine + "\n");
                }
                if (customer instanceof Dependent) {
                    Dependent dependent = (Dependent) customer;
                    String customerLine = String.join(ATTRIBUTE_DELIMITER,
                            "Dependent",
                            dependent.getCID(),
                            dependent.getFullName(),
                            dependent.getInsuranceCardNumber(),
                            getClaimListAsString(dependent.getClaimList()),
                            dependent.getPolicyHolder());
                    bw.write(customerLine + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Insurance Cards File Read and Write Methods
    public List<InsuranceCard> readAllInsuranceCards() {
        List<InsuranceCard> insuranceCards = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(INSURANCE_CARD_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("#") || line.isEmpty()) {
                    continue;
                }

                String[] tokens = line.split(ATTRIBUTE_DELIMITER);
                if (tokens.length < 4) {
                    System.err.println("Invalid insurance card data: " + line);
                    return null;
                }

                String cardNumber = tokens[0];
                String cardHolderCID = tokens[1];
                String policyOwner = tokens[2];
                LocalDate expirationDate = LocalDate.parse(tokens[3]);

                InsuranceCard insuranceCard = new InsuranceCard(cardNumber, cardHolderCID, policyOwner, expirationDate);

                insuranceCards.add(insuranceCard);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return insuranceCards;
    }

    public void writeAllInsuranceCards(List<InsuranceCard> insuranceCards) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(INSURANCE_CARD_FILE_PATH))) {
            bw.write("""
                    # Insurance Cards File
                    # Format: CardNumber, CardHolder, PolicyOwner, ExpirationDate
                                    
                    """);
            for (InsuranceCard insuranceCardObject : insuranceCards) {
                InsuranceCard insuranceCard = insuranceCardObject;
                String cardLine = String.join(ATTRIBUTE_DELIMITER,
                        insuranceCard.getCardNumber(),
                        insuranceCard.getCardHolder(),
                        insuranceCard.getPolicyOwner(),
                        insuranceCard.getExpirationDate().toString());
                bw.write(cardLine + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Claims File Read and Write Methods
    public List<Claim> readAllClaims() {
        List<Claim> claims = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(CLAIM_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("#") || line.isEmpty()) {
                    continue;
                }

                String[] tokens = line.split(ATTRIBUTE_DELIMITER);
                if (tokens.length < 7) {
                    System.err.println("Invalid insurance card data: " + line);
                    return null;
                }

                String fID = tokens[0];
                LocalDate claimDate = LocalDate.parse(tokens[1]);
                String insuredPerson = tokens[2];
                LocalDate examDate = LocalDate.parse(tokens[4]);
                List<String> documentList = getDocumentListFromString(tokens[5]);

                String claimAmountString = tokens[6];
                String numericValue = claimAmountString.replaceAll("[^\\d.]", "");
                BigDecimal claimAmount = new BigDecimal(numericValue);

                ClaimStatus status = ClaimStatus.valueOf(tokens[7]);
                String receiverBankingInfo = tokens[8];

                Claim claim = new Claim(fID, claimDate, insuredPerson, examDate, documentList, claimAmount, status, receiverBankingInfo);

                String cardNumber = tokens[3];
                List<InsuranceCard> allInsuranceCards= readAllInsuranceCards();

                for (InsuranceCard insuranceCard : allInsuranceCards) {
                    if (insuranceCard.getCardNumber().equals(cardNumber)) {
                        claim.setCardNumber(insuranceCard);
                    }
                }

                claims.add(claim);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return claims;
    }
    public void writeAllClaims(List<Claim> claims) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CLAIM_FILE_PATH))) {
            bw.write("""
                    # Claims File
                    # Format: FID, ClaimDate, InsuredPerson, CardNumber, ExamDate, DocumentList, ClaimAmount, Status, ReceiverBankingInfo   
                             
                    """);
            for (Claim claimObject : claims) {
                Claim claim = claimObject;
                String claimLine = String.join(ATTRIBUTE_DELIMITER,
                        claim.getFID(),
                        claim.getClaimDate().toString(),
                        claim.getInsuredPerson(),
                        claim.getCardNumber().getCardNumber(),
                        claim.getExamDate().toString(),
                        claim.getDocumentList().toString(),
                        "USD " + claim.getClaimAmount().toString(),
                        claim.getStatus().toString(),
                        claim.getReceiverBankingInfo());
                bw.write(claimLine + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Helper Methods
    private static String getClaimListAsString(List<Claim> claimList) {
        if (claimList == null || claimList.isEmpty()) {
            return "null";
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (Claim claim : claimList) {
            stringBuilder.append(claim.getFID()).append(LIST_DELIMITER);
        }

        if (stringBuilder.length() > 0) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        return stringBuilder.toString();
    }

    private static String getDependentsAsString(List<String> dependentList) {
        if (dependentList == null || dependentList.isEmpty()) {
            return "null";
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (String dependent : dependentList) {
            stringBuilder.append(dependent).append(LIST_DELIMITER);
        }

        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

//    private List<Claim> getClaimListFromString(String claimListString) {
//        List<Claim> claims = new ArrayList<>();
//        if (claimListString.isEmpty() || claimListString == null) {
//            return claims;
//        }
//        String[] claimIDs = claimListString.split(LIST_DELIMITER);
//        for (String claimID : claimIDs) {
//            // Assuming there's a method to find claim by ID
//            Claim claim = findClaimByID(claimID);
//            if (claim != null) {
//                claims.add(claim);
//            }
//        }
//        return claims;
//    }

    private List<String> getDependentsFromString(String dependentListString) {
        List<String> dependents = new ArrayList<>();
        if (dependentListString == null || dependentListString.isEmpty() || dependentListString.equals("null")) {
            return dependents;
        }

        String[] dependentList = dependentListString.split(LIST_DELIMITER);
        dependents.addAll(Arrays.asList(dependentList));

        return dependents;
    }
    private List<String> getDocumentListFromString(String documentListString) {
        List<String> documentList = new ArrayList<>();
        if (documentListString == null || documentListString.isEmpty() || documentListString.equals("null")) {
            return documentList;
        }

        String[] dependentList = documentListString.split(LIST_DELIMITER);
        documentList.addAll(Arrays.asList(dependentList));

        return documentList;
    }
}
