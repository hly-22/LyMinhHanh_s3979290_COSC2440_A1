/**
 * @author <Ly Minh Hanh - s3979290>
 */

import java.util.List;

public interface ClaimProcessManager {
    void addClaim();
    boolean updateClaim(String fID);
    void deleteClaim();
    void getOneClaim();
    void getAllClaims();


}
