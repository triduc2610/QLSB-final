package DAO;

// DAO cho Sân bóng
import model.Pitch;
import java.util.List;

public interface PitchDAO extends GenericDAO<Pitch> {
    List<Pitch> findByBranch(int branchId);
    List<Pitch> findByType(String type);
    List<Pitch> findActivePitchs(int branchId);
    List<Pitch> findAllActivePitchs();
}