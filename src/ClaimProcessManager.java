/**
 * @author <Ly Minh Hanh - s3979290>
 */

import java.util.List;

public interface ClaimProcessManager {
    public boolean add(Claim claim);
    public boolean update(Claim claim);
    public boolean delete(Claim claim);
    public Claim getOne(String fID);
    public List<Claim> getAll();


}
