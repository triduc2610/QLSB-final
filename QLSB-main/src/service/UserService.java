package service;

import DAO.UserDAO;
import DAO.impl.UserDAOImpl;
import model.User;

import java.util.List;

public class UserService {

    private UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAOImpl();
    }

    public User getUserById(int id) {
        return userDAO.findById(id);
    }

    public User getUserByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    public List<User> getUsersByRole(String role) {
        return userDAO.findByRole(role);
    }

    public List<User> getUsersByBranch(int branchId) {
        return userDAO.findByBranch(branchId);
    }

    public List<User> getAllUsers() {
        return userDAO.findAll();
    }

    public boolean addUser(User user) {
        return userDAO.save(user);
    }

    public boolean updateUser(User user) {
        return userDAO.update(user);
    }

    public boolean deleteUser(int id) {
        return userDAO.delete(id);
    }

    public boolean authenticate(String username, String password) {
        return userDAO.authenticate(username, password);
    }
    
    public boolean changePassword(int id, String newPassword) {
        User user = userDAO.findById(id);
        if (user != null) {
            return userDAO.changePassword(id,newPassword);
        }
        return false;
}

    public boolean resetPassword(int id) {
        User user = userDAO.findById(id);
        if (user != null) {
            user.setPassword(user.getUsername()); // Set to a default password or generate a new one
            return userDAO.update(user);
        }
        return false;
    }
}
