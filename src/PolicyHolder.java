/**
 * @author <Ly Minh Hanh - s3979290>
 */

import java.util.List;

public class PolicyHolder extends Customer {
    private List<Dependent> dependentList;

    public PolicyHolder(String fullName) {
        super(fullName);
    }

    public boolean addDependent(Dependent dependent) {
        return false;
    }
    public boolean removeDependent(Dependent dependent) {
        return false;
    }
}
