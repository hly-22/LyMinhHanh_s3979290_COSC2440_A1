/**
 * @author <Ly Minh Hanh - s3979290>
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Customer {
    private String cID;
    private String fullName;
    private String insuranceCardNumber;
    private List<Claim> claimList;

    public Customer(String cID, String fullName) {
        this.cID = cID;
        this.fullName = fullName;
        this.insuranceCardNumber = null;
        this.claimList = new ArrayList<>();
    }

    public Customer(String cID, String fullName, String insuranceCardNumber, List<Claim> claimList) {
        this.cID = cID;
        this.fullName = fullName;
        this.insuranceCardNumber = insuranceCardNumber;
        this.claimList = claimList;
    }

    public void setInsuranceCard(String insuranceCardNumber) {
        this.insuranceCardNumber = insuranceCardNumber;
    }

    public String getCID() {
        return cID;
    }

    public String getFullName() {
        return fullName;
    }

    public String getInsuranceCardNumber() {
        return insuranceCardNumber;
    }

    public List<Claim> getClaimList() {
        return claimList; // work on this
    }

    public void setInsuranceCardNumber(String String) {
        this.insuranceCardNumber = String;
    }

    public boolean addToClaimList(Claim claim) {
        return claimList.add(claim);
    }

    public boolean removeClaim(Claim claim) {
        return claimList.remove(claim);
    }
//        int indexToRemove = -1;
//        for (int i = 0; i < claimList.size(); i++) {
//            if (claimList.get(i).equals(claim)) {
//                indexToRemove = i;
//                break;
//            }
//        }
//        if (indexToRemove != -1) {
//            claimList.remove(indexToRemove);
//            return true;
//        } else {
//            return false;
//        }
//    }
}
