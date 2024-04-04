/**
 * @author <Ly Minh Hanh - s3979290>
 */

import java.util.List;

public interface ClaimProcessManager {
    public void addClaim();
    public boolean updateClaim(String fID);
    public boolean deleteClaim(String fID);
    public void getOneClaim(String fID);
    public void getAllClaims();


}
