/**
 * @author <Ly Minh Hanh - s3979290>
 */

import java.util.List;

public abstract class Customer {
    private String cID;
    private String fullName;
    private InsuranceCard insuranceCard;
    private List<Claim> claimList;
    private static int nextID = 1;

    public Customer(String fullName) {
        this.cID = generateCID();
        this.fullName = fullName;
        this.insuranceCard = null;
        this.claimList = null;
    }

    // generate unique cID
    protected synchronized String generateCID() {
        String CID = "c-" + String.format("%07d", nextID);
        return CID;
    }

    // check if customer already has an insurance card
    public boolean hasInsuranceCard() {
        // logic to add card or create card --> true
        return false;
    }

}
