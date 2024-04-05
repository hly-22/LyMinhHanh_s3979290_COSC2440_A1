/**
 * @author <Ly Minh Hanh - s3979290>
 */

import java.time.LocalDate;

public class InsuranceCard {
    private String cardNumber;
    private Customer cardHolder;
    private String policyOwner;
    private LocalDate expirationDate;
    private static int nextCardNumber = 1;

    public InsuranceCard(Customer cardHolder) {
        this.cardNumber = generateCardNumber();
        this.cardHolder = cardHolder;
        this.cardNumber = generateCardNumber();
    }

    protected synchronized String generateCardNumber() {
        String cardNumber = String.format("%010d", nextCardNumber);
        nextCardNumber++;
        return cardNumber;
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
