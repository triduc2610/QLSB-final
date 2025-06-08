package view;

import javax.swing.*;

import model.Customer;

import java.awt.*;
import java.awt.event.ActionListener;

public class CustomerView extends JPanel {
    private JTextField idField;
    private JTextField nameField;
    private JTextField phoneField;
    private JTextField emailField;
    private JComboBox<String> customerTypeComboBox;
    private JTextArea notesArea;
    private JButton saveButton;
    private JButton cancelButton;
    
    public CustomerView() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        //setBackground();
        // Title
        JLabel titleLabel = new JLabel("THÔNG TIN KHÁCH HÀNG", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);
        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setPreferredSize(new Dimension(600, 420));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 10, 6, 10);
        gbc.anchor = GridBagConstraints.WEST;
        Font labelFont = new Font("Arial", Font.BOLD, 16);
        Font fieldFont = new Font("Arial", Font.PLAIN, 16);
        // ID field
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel idLabel = new JLabel("Mã khách hàng:");
        idLabel.setFont(labelFont);
        formPanel.add(idLabel, gbc);
        idField = new JTextField(20);
        idField.setEditable(false);
        idField.setFont(fieldFont);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(idField, gbc);
        // Name field
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        JLabel nameLabel = new JLabel("Tên khách hàng:");
        nameLabel.setFont(labelFont);
        formPanel.add(nameLabel, gbc);
        nameField = new JTextField(20);
        nameField.setFont(fieldFont);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(nameField, gbc);
        // Phone field
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        JLabel phoneLabel = new JLabel("Số điện thoại:");
        phoneLabel.setFont(labelFont);
        formPanel.add(phoneLabel, gbc);
        phoneField = new JTextField(20);
        phoneField.setFont(fieldFont);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(phoneField, gbc);
        // Email field
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(labelFont);
        formPanel.add(emailLabel, gbc);
        emailField = new JTextField(20);
        emailField.setFont(fieldFont);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(emailField, gbc);
        // Notes (Địa chỉ)
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        JLabel addressLabel = new JLabel("Địa chỉ:");
        addressLabel.setFont(labelFont);
        formPanel.add(addressLabel, gbc);
        notesArea = new JTextArea(4, 20);
        notesArea.setLineWrap(true);
        notesArea.setFont(fieldFont);
        JScrollPane notesScrollPane = new JScrollPane(notesArea);
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.BOTH;
        formPanel.add(notesScrollPane, gbc);
        add(formPanel, BorderLayout.CENTER);
        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        cancelButton = new JButton("Hủy");
        saveButton = new JButton("Lưu");
        cancelButton.setFont(fieldFont);
        saveButton.setFont(fieldFont);
        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    public String getId() {
        return idField.getText();
    }
    
    public void setId(String id) {
        idField.setText(id);
    }
    
    public String getName() {
        return nameField.getText();
    }
    
    public void setName(String name) {
        nameField.setText(name);
    }
    
    public String getPhone() {
        return phoneField.getText();
    }
    
    public void setPhone(String phone) {
        phoneField.setText(phone);
    }
    
    public String getEmail() {
        return emailField.getText();
    }
    
    public void setEmail(String email) {
        emailField.setText(email);
    }
    
    public String getCustomerType() {
        return (String) customerTypeComboBox.getSelectedItem();
    }
    
    public void setCustomerType(String type) {
        customerTypeComboBox.setSelectedItem(type);
    }
    
    public String getNotes() {
        return notesArea.getText();
    }
    
    public void setNotes(String notes) {
        notesArea.setText(notes);
    }
    
    public void setSaveAction(ActionListener listener) {
        saveButton.addActionListener(listener);
    }
    
    public void setCancelAction(ActionListener listener) {
        cancelButton.addActionListener(listener);
    }
    
    public Customer getCustomerData() {
        Customer customer = new Customer();
        //customer.setId(idField.getText());
        customer.setName(nameField.getText());
        customer.setPhone(phoneField.getText());
        customer.setEmail(emailField.getText());
        //customer.setCustomerType((String) customerTypeComboBox.getSelectedItem());
        customer.setAddress(notesArea.getText());
        System.out.println("Name: " + nameField.getText());
        System.out.println("Phone: " + phoneField.getText());
        System.out.println("Email: " + emailField.getText());
        System.out.println("Address: " + emailField.getText());

    System.out.println("Customer data: " + customer.toString());
        System.out.println("Customer data: " + customer.toString());
        return customer;
    }

    public void displayCustomerCreationSuccess(Customer customer) {
        JOptionPane.showMessageDialog(this, "Khách hàng " + customer.getName() + " đã được tạo thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
    }

    public void displayError(String message) {
        JOptionPane.showMessageDialog(this, message, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }

    public void clear() {
        idField.setText("heeh");
        nameField.setText("hehe");
        phoneField.setText("hehe");
        emailField.setText("hehe");
        //customerTypeComboBox.setSelectedIndex(0);
        notesArea.setText("hehe");
    }
}

