/**
 * @author <Ly Minh Hanh - s3979290>
 */

public class CustomerViewText {
    public void view(Dependent dependent) {
        System.out.println();
        System.out.println("------------------------");
        System.out.println("Dependent Information");
        System.out.println();
        System.out.println("cID: " + dependent.getCID());
        System.out.println("full name: " + dependent.getFullName());
        System.out.println("policy holder: " + dependent.getPolicyHolder().getFullName());
        System.out.println("------------------------");
        System.out.println();
    }
    public void view(PolicyHolder policyHolder) {
        System.out.println();
        System.out.println("------------------------");
        System.out.println("Policy Holder Information");
        System.out.println();
        System.out.println("cID: " + policyHolder.getCID());
        System.out.println("full name: " + policyHolder.getFullName());
        System.out.println("dependents: " + policyHolder.getDependentList());
        System.out.println("------------------------");
        System.out.println();
    }


}
