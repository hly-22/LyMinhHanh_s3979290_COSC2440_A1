/**
 * @author <Ly Minh Hanh - s3979290>
 */

public class Dependent extends Customer {
    private PolicyHolder policyHolder;

    public Dependent(String fullName, PolicyHolder policyHolder) {
        super(fullName);
        this.policyHolder = policyHolder;
    }

    public PolicyHolder getPolicyHolder() {
        return policyHolder;
    }
}
