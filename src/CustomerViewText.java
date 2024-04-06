/**
 * @author <Ly Minh Hanh - s3979290>
 */

public class CustomerViewText {
    public void view(Dependent dependent) {
        System.out.println("==============DEPENDENT==============");
        System.out.println("CID: " + dependent.getCID());
        System.out.println("Full name: " + dependent.getFullName());
        System.out.println("Policy holder: " + dependent.getPolicyHolder());
        System.out.println("=====================================");
        System.out.println();
    }
    public void view(PolicyHolder policyHolder) {
        System.out.println("============POLICY-HOLDER============");
        System.out.println("CID: " + policyHolder.getCID());
        System.out.println("Full name: " + policyHolder.getFullName());
        System.out.println("Dependents: " + policyHolder.getDependentList());
        System.out.println("=====================================");
        System.out.println();
    }


}
