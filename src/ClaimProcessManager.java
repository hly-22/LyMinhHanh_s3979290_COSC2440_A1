import java.util.List;

/**
 * @author <Ly Minh Hanh - s3979290>
 */

public interface ClaimProcessManager {
    public boolean add(Claim claim);
    public void update(Claim claim);
    public boolean delete(Claim claim);
    public Claim getOne(String fID);
    public List<Claim> getAll();


}
