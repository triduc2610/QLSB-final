package view;

import javax.swing.*;

// import model.User;
// import DAO.UserDAO;
// import DAO.impl.UserDAOImpl;

import java.awt.*;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel statusLabel;

    public LoginView() {
        setTitle("Đăng Nhập - Hệ thống Quản lý Sân Bóng");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Title
        JLabel titleLabel = new JLabel("ĐĂNG NHẬP HỆ THỐNG", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(titleLabel, gbc);
        
        // Username
        JLabel usernameLabel = new JLabel("Tên đăng nhập:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(usernameLabel, gbc);
        
        usernameField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(usernameField, gbc);
        
        // Password
        JLabel passwordLabel = new JLabel("Mật khẩu:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(passwordLabel, gbc);
        
        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy = 2;
        mainPanel.add(passwordField, gbc);
        
        // Login button
        loginButton = new JButton("Đăng Nhập");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(loginButton, gbc);
        
        // Status label
        statusLabel = new JLabel("", JLabel.CENTER);
        statusLabel.setForeground(Color.RED);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        mainPanel.add(statusLabel, gbc);

        add(mainPanel);
    }
    
    public String getUsername() {
        return usernameField.getText();
    }
    
    public String getPassword() {
        return new String(passwordField.getPassword());
    }
    
    public void setLoginAction(ActionListener listener) {
        loginButton.addActionListener(listener);
    }
    
    public void showWelcomeMessage(String userrole) {
        JLabel welcomeLabel = new JLabel("Đăng nhập thành công với vai trò " + userrole, JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        JOptionPane.showMessageDialog(null,welcomeLabel,"Thông báo",JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void showError(String message) {
        statusLabel.setText(message);
    }
    
    public void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
        statusLabel.setText("");
    }
}