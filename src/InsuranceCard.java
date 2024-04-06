/**
 * @author <Ly Minh Hanh - s3979290>
 */

import java.time.LocalDate;

public class InsuranceCard {
    private String cardNumber;
    private String cardHolder;
    private String policyOwner;
    private LocalDate expirationDate;

    public InsuranceCard(String cardNumber,String cardHolder) {
        this.cardNumber = cardNumber;
        this.cardHolder = cardHolder;
    }

    public InsuranceCard(String cardNumber, String cardHolder, LocalDate expirationDate) {
        this.cardNumber = cardNumber;
        this.cardHolder = cardHolder;
        this.policyOwner = null;
        this.expirationDate = expirationDate;
    }

    public InsuranceCard(String cardNumber, String cardHolder, String policyOwner, LocalDate expirationDate) {
        this.cardNumber = cardNumber;
        this.cardHolder = cardHolder;
        this.policyOwner = policyOwner;
        this.expirationDate = expirationDate;
    }

    public InsuranceCard() {
    }


    public void setPolicyOwner(String policyOwner) {
        this.policyOwner = policyOwner;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public String getPolicyOwner() {
        if (policyOwner == null) {
            return null;
        }
        return policyOwner;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }
}
