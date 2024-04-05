import java.util.List;

/**
 * @author <Ly Minh Hanh - s3979290>
 */

public class Dependent extends Customer {
    private String policyHolder;

    public Dependent(String cID, String fullName, String policyHolder) {
        super(cID, fullName);
        this.policyHolder = policyHolder;
    }

    public Dependent(String cID, String fullName, InsuranceCard insuranceCard, List<Claim> claimList, String policyHolder) {
        super(cID, fullName, insuranceCard, claimList);
        this.policyHolder = policyHolder;
    }

    public String getPolicyHolder() {
        return policyHolder;
    }

    public void setPolicyHolder(String policyHolder) {
        this.policyHolder = policyHolder;
    }
}
