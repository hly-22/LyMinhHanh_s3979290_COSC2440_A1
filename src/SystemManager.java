import java.util.List;

/**
 * @author <Ly Minh Hanh - s3979290>
 */

public class SystemManager implements ClaimProcessManager{

    @Override
    public boolean add(Claim claim) {
        return false;
    }

    @Override
    public boolean update(Claim claim) {

    }

    @Override
    public boolean delete(Claim claim) {
        return false;
    }

    @Override
    public Claim getOne(String fID) {
        return null;
    }

    @Override
    public List<Claim> getAll() {
        return null;
    }
}
