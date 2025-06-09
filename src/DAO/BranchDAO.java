package DAO;
import model.Branch;
import java.util.List;

public interface BranchDAO extends GenericDAO<Branch> {
    List<Branch> findActive();
    Branch findByPitch(int pitchId);
}
