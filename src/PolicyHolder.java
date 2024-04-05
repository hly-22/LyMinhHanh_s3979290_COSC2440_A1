/**
 * @author <Ly Minh Hanh - s3979290>
 */

import java.util.ArrayList;
import java.util.List;

public class PolicyHolder extends Customer {
    private List<String> dependentList;

    public PolicyHolder(String cID, String fullName) {
        super(cID, fullName);
        this.dependentList = new ArrayList<>();
    }

    public PolicyHolder(String cID, String fullName, InsuranceCard insuranceCard, List<Claim> claimList, List<String> dependentList) {
        super(cID, fullName, insuranceCard, claimList);
        this.dependentList = dependentList;

    }

    public boolean addDependent(Dependent dependent) {
        return dependentList.add(dependent.getCID());
    }
    public boolean removeDependent(Dependent dependent) {
        int indexToRemove = -1;
        for (int i = 0; i < dependentList.size(); i++) {
            if (dependentList.get(i).equals(dependent.getCID())) {
                indexToRemove = i;
                break;
            }
        }
        if (indexToRemove != -1) {
            dependentList.remove(indexToRemove);
            return true;
        } else {
            return false;
        }
    }

    public List<String> getDependentList() {
        return dependentList;
    }
}
