/**
 * @author <Ly Minh Hanh - s3979290>
 */

import java.util.List;

public abstract class Customer{
    private String cID;
    private String fullName;
    private InsuranceCard insuranceCard;
    private List<Claim> claimList;
    private static int nextCID = 1;

    public Customer(String cID, String fullName) {
        this.cID = cID;
        this.fullName = fullName;
        this.insuranceCard = null;
        this.claimList = null;
    }

    public Customer(String cID, String fullName, InsuranceCard insuranceCard, List<Claim> claimList) {
        this.cID = cID;
        this.fullName = fullName;
        this.insuranceCard = null;
        this.claimList = null;
    }

    public void setInsuranceCard(InsuranceCard insuranceCard) {
        this.insuranceCard = insuranceCard;
    }

    public String getCID() {
        return cID;
    }

    public String getFullName() {
        return fullName;
    }


    public InsuranceCard getInsuranceCard() {
        return insuranceCard;
    }

    public String getCardNumber() {
        if (insuranceCard != null) {
            return insuranceCard.getCardNumber();
        }
        return null;
    }
    public List<Claim> getClaimList() {
        return claimList; // work on this
    }
}
