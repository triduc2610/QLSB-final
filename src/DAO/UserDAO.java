package DAO;
import model.User;
import java.util.List;

public interface UserDAO extends GenericDAO<User> {
    User findByUsername(String username);
    List<User> findByRole(String role);
    List<User> findByBranch(int branchId);
    boolean authenticate(String username, String password);
    boolean changePassword(int id, String newPassword);
}

