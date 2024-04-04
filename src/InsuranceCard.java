/**
 * @author <Ly Minh Hanh - s3979290>
 */

import java.time.LocalDate;

public class InsuranceCard {
    private String cardNumber;
    private Customer cardHolder;            // only one cardholder
    private String policyOwner;
    private LocalDate expirationDate;
    private static int nextCardNumber = 1;

    public InsuranceCard(Customer cardHolder) {
        this.cardNumber = generateCardNumber();
        this.cardHolder = cardHolder;
        this.cardNumber = generateCardNumber();
    }

    public InsuranceCard(Customer cardHolder, String policyOwner, LocalDate expirationDate) {
        this.cardNumber = generateCardNumber();
        this.cardHolder = cardHolder;
        this.policyOwner = policyOwner;
        this.expirationDate = expirationDate;
    }

    protected synchronized String generateCardNumber() {
        String cardNumber = String.format("%010d", nextCardNumber);
        nextCardNumber++;
        return cardNumber;
    }

    public void setCardHolder(Customer cardHolder) {
        this.cardHolder = cardHolder;
    }

    public void setPolicyOwner(String policyOwner) {
        this.policyOwner = policyOwner;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public Customer getCardHolder() {
        return cardHolder;
    }

    public String getPolicyOwner() {
        return policyOwner;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }
}
