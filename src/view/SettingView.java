package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SettingView extends JPanel {
    public JTextField nameField, usernameField, emailField, phoneField, roleField, branchField;
    public JButton changePasswordButton, changeEmailButton, changePhoneButton;
    public JButton logoutButton, deleteAccountButton;

    public SettingView() {
        setLayout(new BorderLayout());
        //setBackground(new Color(245, 245, 245));
        //setBackground(new Color(200, 220, 255));

        // Title
        JLabel titleLabel = new JLabel("Thiết lập người dùng", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 50));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(80, 0, 30, 0));
        add(titleLabel, BorderLayout.NORTH);

        // Main content panel
        JPanel contentPanel = new JPanel(new GridBagLayout());
        //contentPanel.setBackground(new Color(200, 220, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Left form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints fgbc = new GridBagConstraints();
        fgbc.insets = new Insets(10, 10, 10, 10);
        fgbc.anchor = GridBagConstraints.WEST;
        fgbc.fill = GridBagConstraints.HORIZONTAL;
        fgbc.gridx = 0;
        fgbc.gridy = 0;

        Font labelFont = new Font("Arial", Font.PLAIN, 18);
        Font fieldFont = new Font("Arial", Font.PLAIN, 18);

        // Tên người dùng
        JLabel nameLabel = new JLabel("Tên người dùng", JLabel.LEFT);
        nameLabel.setFont(labelFont);
        formPanel.add(nameLabel, fgbc);
        fgbc.gridx = 1;
        nameField = new JTextField(18);
        nameField.setFont(fieldFont);
        nameField.setEditable(false);
        formPanel.add(nameField, fgbc);
        fgbc.gridx = 0;
        fgbc.gridy++;

        // Tên tài khoản
        JLabel usernameLabel = new JLabel("Tên tài khoản", JLabel.LEFT);
        usernameLabel.setFont(labelFont);
        formPanel.add(usernameLabel, fgbc);
        fgbc.gridx = 1;
        usernameField = new JTextField(18);
        usernameField.setFont(fieldFont);
        usernameField.setEditable(false);
        formPanel.add(usernameField, fgbc);
        fgbc.gridx = 0;
        fgbc.gridy++;

        // Mật khẩu
        JLabel passwordLabel = new JLabel("Mật Khẩu", JLabel.LEFT);
        passwordLabel.setFont(labelFont);
        formPanel.add(passwordLabel, fgbc);
        fgbc.gridx = 1;
        JPanel pwPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        pwPanel.setOpaque(false);
        changePasswordButton = new JButton("Thay đổi mật khẩu");
        changePasswordButton.setFont(fieldFont);
        pwPanel.add(changePasswordButton);
        formPanel.add(pwPanel, fgbc);
        fgbc.gridx = 0;
        fgbc.gridy++;

        // Email
        JLabel emailLabel = new JLabel("Email", JLabel.LEFT);
        emailLabel.setFont(labelFont);
        formPanel.add(emailLabel, fgbc);
        fgbc.gridx = 1;
        emailField = new JTextField(18);
        emailField.setFont(fieldFont);
        emailField.setEditable(false);
        formPanel.add(emailField, fgbc);
        fgbc.gridx = 1;
        fgbc.gridy++;
        changeEmailButton = new JButton("Thay đổi email");
        changeEmailButton.setFont(fieldFont);
        changeEmailButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        JPanel changeEmailPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        changeEmailPanel.setOpaque(false);
        changeEmailPanel.add(changeEmailButton);
        formPanel.add(changeEmailPanel, fgbc);
        fgbc.gridx = 0;
        fgbc.gridy++;

        // Số điện thoại
        JLabel phoneLabel = new JLabel("Số điện thoại", JLabel.LEFT);
        phoneLabel.setFont(labelFont);
        formPanel.add(phoneLabel, fgbc);
        fgbc.gridx = 1;
        JPanel phonePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        phonePanel.setOpaque(false);
        phoneField = new JTextField(10);
        phoneField.setFont(fieldFont);
        phoneField.setEditable(false);
        changePhoneButton = new JButton("Thay đổi sdt");
        changePhoneButton.setFont(fieldFont);
        phonePanel.add(phoneField);
        phonePanel.add(changePhoneButton);
        formPanel.add(phonePanel, fgbc);
        fgbc.gridx = 0;
        fgbc.gridy++;

        // Vai trò
        JLabel roleLabel = new JLabel("Vai trò", JLabel.LEFT);
        roleLabel.setFont(labelFont);
        formPanel.add(roleLabel, fgbc);
        fgbc.gridx = 1;
        roleField = new JTextField(18);
        roleField.setFont(fieldFont);
        roleField.setEditable(false);
        formPanel.add(roleField, fgbc);
        fgbc.gridx = 0;
        fgbc.gridy++;

        // Chi nhánh
        JLabel branchLabel = new JLabel("Chi nhánh", JLabel.LEFT);
        branchLabel.setFont(labelFont);
        formPanel.add(branchLabel, fgbc);
        fgbc.gridx = 1;
        branchField = new JTextField(18);
        branchField.setFont(fieldFont);
        branchField.setEditable(false);
        formPanel.add(branchField, fgbc);

        // Add form panel to content panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        contentPanel.add(formPanel, gbc);

        // Right button panel
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);
        GridBagConstraints bgbc = new GridBagConstraints();
        bgbc.insets = new Insets(20, 30, 20, 30);
        bgbc.gridx = 0;
        bgbc.gridy = 0;
        bgbc.fill = GridBagConstraints.BOTH;
        bgbc.weightx = 1;
        bgbc.weighty = 1;

        Font bigButtonFont = new Font("Arial", Font.BOLD, 24);
        logoutButton = new JButton("Đăng xuất");
        logoutButton.setPreferredSize(new Dimension(150, 100));
        logoutButton.setFont(bigButtonFont);
        buttonPanel.add(logoutButton, bgbc);
        bgbc.gridy++;
        deleteAccountButton = new JButton("Xóa tài khoản");
        deleteAccountButton.setPreferredSize(new Dimension(150, 100));
        deleteAccountButton.setFont(bigButtonFont);
        deleteAccountButton.setBackground(Color.RED);
        deleteAccountButton.setForeground(Color.WHITE);
        deleteAccountButton.setOpaque(true);
        deleteAccountButton.setBorderPainted(false);
        buttonPanel.add(deleteAccountButton, bgbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        contentPanel.add(buttonPanel, gbc);

        add(contentPanel, BorderLayout.CENTER);
    }

    public void setUserInfo(String name, String username, String email, String phone, String role, String branch) {
        nameField.setText(name);
        usernameField.setText(username);
        emailField.setText(email);
        phoneField.setText(phone);
        roleField.setText(role);
        branchField.setText(branch);
    }

    public void setChangePasswordAction(ActionListener listener) {
        changePasswordButton.addActionListener(listener);
    }

    public void setChangeEmailAction(ActionListener listener) {
        changeEmailButton.addActionListener(listener);
    }

    public void setChangePhoneAction(ActionListener listener) {
        changePhoneButton.addActionListener(listener);
    }

    public void setLogoutAction(ActionListener listener) {
        logoutButton.addActionListener(listener);
    }

    public void setDeleteAccountAction(ActionListener listener) {
        deleteAccountButton.addActionListener(listener);
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

} 