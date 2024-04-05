/**
 * @author <Ly Minh Hanh - s3979290>
 */

import java.util.List;

public interface ClaimProcessManager {
    void addClaim();
    boolean updateClaim(String fID);
    boolean deleteClaim(String fID);
    void getOneClaim(String fID);
    void getAllClaims();


}
