package view;

import view.components.TableComponent;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import model.Branch;
import model.User;
import java.util.List;
import view.components.DialogComponent;

public class UserListView extends JPanel {
    private JButton addUserButton;
    private TableComponent<Object> userTable;
    private JButton editButton;
    private JButton deleteButton;
    private JButton refresh;
    private JComboBox<String> branchComboBox ;
    // Dialog sửa/thêm người dùng
    private JButton saveAddbutton = new JButton("Thêm");
    private JButton saveChangeButton = new JButton("Lưu");
    private JButton cancelButton = new JButton("Hủy");
    private DialogComponent dialog = new DialogComponent("Thông tin người dùng", 400, 350);

    public UserListView() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Title
        JLabel titleLabel = new JLabel("DANH SÁCH NGƯỜI DÙNG", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Chọn chi nhánh:"));
        branchComboBox = new JComboBox<>();
        // String[] branches = {"Chi nhánh 1", "Chi nhánh 2", "Chi nhánh 3"};
        // for (String branch : branches) {
        //     branchComboBox.addItem(branch);
        // }
        searchPanel.add(branchComboBox);

        addUserButton = new JButton("Thêm Người Dùng");
        JPanel addButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        addButtonPanel.add(addUserButton);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(searchPanel, BorderLayout.WEST);
        topPanel.add(addButtonPanel, BorderLayout.EAST);
        topPanel.add(titleLabel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        // User table
        String[] columnNames = {"Mã người dùng", "Tên người dùng", "Email", "Số điện thoại", "Role"};
        userTable = new TableComponent<>(columnNames);
        userTable.setFillsViewportHeight(true);
        JScrollPane tableScrollPane = new JScrollPane(userTable);
        add(tableScrollPane, BorderLayout.CENTER);

        // Button panel (right side)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        editButton = new JButton("Sửa");
        deleteButton = new JButton("Xóa");
        refresh = new JButton("Làm mới");
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refresh);
        add(buttonPanel, BorderLayout.EAST);

        dialog.setVisible(false);
    }

    public void loadDataToTable(List<User> users){
        userTable.clearTable();
        for(User user : users){
            Object[] rowData = {
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getPhone(),
                user.getRole(),
            };
            userTable.addRow(rowData);
            //System.out.println("User: " + user.getFullName() + ", Role: " + user.getRole());
        }
    }

    public void loadcbdata(List<Branch> branches){
        branchComboBox.removeAllItems();
        for(Branch branch : branches){
            branchComboBox.addItem(branch.toString());
        }
        branchComboBox.setSelectedIndex(0);
    }

    public String getSelectedBranch(){
        //get only branchid
        return branchComboBox.getSelectedItem().toString().split("-")[0].trim();
    }

    public int getSelectedUserId() {
        try{
            return (int)userTable.getValueAt((int)userTable.getSelectedRow(),0);
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(this, "Hãy chọn một người dùng !", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return -1;
        }
    }

    public void setCBaction(ActionListener listener) {
        branchComboBox.addActionListener(listener);
    }

    public void setAddUserAction(ActionListener listener) {
        addUserButton.addActionListener(listener);
    }

    public void setEditAction(ActionListener listener) {
        editButton.addActionListener(listener);
    }

    public void setDeleteAction(ActionListener listener) {
        deleteButton.addActionListener(listener);
    }

    public void setRefreshAction(ActionListener listener) {
        refresh.addActionListener(listener);
    }

    public void initdialog(String nameString, String emailString, String phoneString, String roleString) {
        JPanel dialogPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        dialogPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField name = new JTextField();
        JTextField email = new JTextField();
        JTextField phone = new JTextField();
        JComboBox<String> role = new JComboBox<>();
        String[] roles = {"ADMIN", "STAFF", "MANAGER"};
        //JCheckBox activeCheck = new JCheckBox("Hoạt động");
        name.setText(nameString);
        email.setText(emailString);
        phone.setText(phoneString);
        for(String roleitem : roles){
            role.addItem(roleitem);
        }
        role.setSelectedItem(roleString);
        //activeCheck.setSelected(active);

        dialogPanel.add(new JLabel("Tên người dùng:"));
        dialogPanel.add(name);
        dialogPanel.add(new JLabel("Email:"));
        dialogPanel.add(email);
        dialogPanel.add(new JLabel("Số điện thoại:"));
        dialogPanel.add(phone);
        dialogPanel.add(new JLabel("Role:"));
        dialogPanel.add(role);
        //dialogPanel.add(new JLabel("Trạng thái:"));
        //dialogPanel.add(activeCheck);

        dialog.registerComponent("name", name);
        dialog.registerComponent("email", email);
        dialog.registerComponent("phone", phone);
        dialog.registerComponent("role", role);
        //dialog.registerComponent("active", activeCheck);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(saveChangeButton);
        buttonPanel.add(cancelButton);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(dialogPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setContentPane(mainPanel);
    }

    public void initdialogforadding() {
        JPanel dialogPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        dialogPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JTextField name = new JTextField();
        JTextField username = new JTextField();
        JTextField password = new JTextField();
        JTextField email = new JTextField();
        JTextField phone = new JTextField();
        JComboBox<String> role = new JComboBox<>();
        JTextField branchId = new JTextField();
        branchId.setText(getSelectedBranch());
        branchId.setEditable(false);

        String[] roles = {"ADMIN", "STAFF", "MANAGER"};
        for (String roleItem : roles) {
            role.addItem(roleItem);
        }
        dialogPanel.add(new JLabel("Tên người dùng:"));
        dialogPanel.add(name);
        dialogPanel.add(new JLabel("Tài khoản:"));
        dialogPanel.add(username);
        dialogPanel.add(new JLabel("Mật khẩu:"));
        dialogPanel.add(password);
        dialogPanel.add(new JLabel("Email:"));
        dialogPanel.add(email);
        dialogPanel.add(new JLabel("Số điện thoại:"));
        dialogPanel.add(phone);
        dialogPanel.add(new JLabel("Role:"));
        dialogPanel.add(role);
        dialogPanel.add(new JLabel("Mã Chi nhánh:"));
        dialogPanel.add(branchId);

        dialog.registerComponent("name", name);
        dialog.registerComponent("username", username);
        dialog.registerComponent("password", password);
        dialog.registerComponent("email", email);
        dialog.registerComponent("phone", phone);
        dialog.registerComponent("role", role);
        dialog.registerComponent("branch", branchId);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(saveAddbutton);
        buttonPanel.add(cancelButton);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(dialogPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setContentPane(mainPanel);
    }

    public void showDialog(boolean value) {
        this.dialog.setVisible(value);
    }

    private String getUpdatedName() {
        return dialog.getFieldValue("name");
    }
    private String getUpdatedEmail() {
        return dialog.getFieldValue("email");
    }
    private String getUpdatedPhone() {
        return dialog.getFieldValue("phone");
    }
    private String getUpdatedRole() {
        return (String)dialog.getComboBoxValue("role");
    }

    public User getUpdatedUser(){
        //hack to update only name,phone,email,role
        return new User(getSelectedUserId(),"1","1",
         getUpdatedName(), getUpdatedEmail(), getUpdatedPhone(), getUpdatedRole(),1);
    }

    public User getNewUser(){
        
        return new User(0,dialog.getFieldValue("username"),dialog.getFieldValue("password"),getUpdatedName(), getUpdatedEmail(), getUpdatedPhone(), getUpdatedRole(),Integer.parseInt(dialog.getFieldValue("branch")));
    }
    public void setSaveAddAction(ActionListener listener) {
        saveAddbutton.addActionListener(listener);
    }
    public void setSaveEditAction(ActionListener listener) {
        saveChangeButton.addActionListener(listener);
    }
    public void setCancelAction(ActionListener listener) {
        cancelButton.addActionListener(listener);
    }

    public void showMessage(String message){
        JOptionPane.showMessageDialog(this, message, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainView mainView = new MainView("ADMIN"    );
            mainView.addPanel(new UserListView(), "user");
            mainView.setVisible(true);
            mainView.showPanel("user");
        });
    }
}
