package view;
import model.Branch;
import java.util.List;
import view.components.TableComponent;
import view.components.DialogComponent;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class BranchListView extends JPanel {
    private JTextField searchField;
    private JButton searchButton;
    private JButton addBranchButton;
    private TableComponent<Object> branchTable;
    private JButton editButton;
    private JButton deleteButton;
    private JButton RefeshButton;
    private JButton saveAddButton = new JButton("Thêm");
    private JButton saveChangeButton = new JButton("Lưu");
    private JButton cancelButton = new JButton("Hủy");
    private DialogComponent dialog;

    public BranchListView() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Title
        JLabel titleLabel = new JLabel("DANH SÁCH CHI NHÁNH", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        // Search panel
        // JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        // searchPanel.add(new JLabel("Tìm kiếm:"));
        // searchField = new JTextField(20);
        // searchPanel.add(searchField);
        // searchButton = new JButton("Tìm");
        // searchPanel.add(searchButton);

        addBranchButton = new JButton("Thêm Chi Nhánh");
        JPanel addButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        addButtonPanel.add(addBranchButton);
        

        JPanel topPanel = new JPanel(new BorderLayout());
        //topPanel.add(searchPanel, BorderLayout.WEST);
        topPanel.add(addButtonPanel, BorderLayout.EAST);
        topPanel.add(titleLabel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        // Branch table
        String[] columnNames = {"Mã chi nhánh", "Tên chi nhánh", "Địa chỉ", "Số điện thoại", "Active"};
        branchTable = new TableComponent<>(columnNames);
        branchTable.setFillsViewportHeight(true);
        JScrollPane tableScrollPane = new JScrollPane(branchTable);
        add(tableScrollPane, BorderLayout.CENTER);

        // Button panel (move to bottom)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        editButton = new JButton("Sửa");
        deleteButton = new JButton("Xóa");
        RefeshButton = new JButton("Làm mới");
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(RefeshButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Dialog sửa/thêm chi nhánh
        dialog = new DialogComponent("Thông tin chi nhánh", 400, 300);
    }

    public void removefrom(String[] buttons){
        for(String button : buttons){
            switch (button) {
                case "edit":
                    editButton.setVisible(false);
                    break;
                case "delete":
                    deleteButton.setVisible(false);
                    break;    
                default:
                    break;
            }
        }
    }

    public int getSelectedBranchIndex() {
        return branchTable.getSelectedRow();
    }

    public String getSearchText() {
        return searchField.getText();
    }

    public void setAddBranchAction(ActionListener listener) {
        addBranchButton.addActionListener(listener);
    }

    public void setEditAction(ActionListener listener) {
        editButton.addActionListener(listener);
    }

    public void setDeleteAction(ActionListener listener) {
        deleteButton.addActionListener(listener);
    }

    public void setRefeshAction(ActionListener listener) {
        RefeshButton.addActionListener(listener);
    }

    public void setSearchAction(ActionListener listener) {
        searchButton.addActionListener(listener);
    }

    public String getSelectedBranchId() {
        int selectedRow = branchTable.getSelectedRow();
        if (selectedRow != -1) {
            return branchTable.getValueAt(selectedRow, 0).toString(); // Mã chi nhánh
        }
        return null;
    }

    public String getSelectedBranchName() {
        int selectedRow = branchTable.getSelectedRow();
        if (selectedRow != -1) {
            return branchTable.getValueAt(selectedRow, 1).toString(); // Tên chi nhánh
        }
        return null;
    }

    public String getSelectedBranchAddress() {
        int selectedRow = branchTable.getSelectedRow();
        if (selectedRow != -1) {
            return branchTable.getValueAt(selectedRow, 2).toString(); // Địa chỉ
        }
        return null;
    }

    public String getSelectedBranchPhone() {
        int selectedRow = branchTable.getSelectedRow();
        if (selectedRow != -1) {
            return branchTable.getValueAt(selectedRow, 3).toString(); // Số điện thoại
        }
        return null;
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

   

    public void initdialog(String nameString, String addressString, String phoneString, boolean active) {
        JPanel dialogPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        dialogPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField name = new JTextField();
        JTextField address = new JTextField();
        JTextField phone = new JTextField();
        JCheckBox activeCheck = new JCheckBox("Hoạt động");
        name.setText(nameString);
        address.setText(addressString);
        phone.setText(phoneString);
        activeCheck.setSelected(active);

        dialogPanel.add(new JLabel("Tên chi nhánh:"));
        dialogPanel.add(name);
        dialogPanel.add(new JLabel("Địa chỉ:"));
        dialogPanel.add(address);
        dialogPanel.add(new JLabel("Số điện thoại:"));
        dialogPanel.add(phone);
        dialogPanel.add(new JLabel("Trạng thái:"));
        dialogPanel.add(activeCheck);

        dialog.registerComponent("name", name);
        dialog.registerComponent("address", address);
        dialog.registerComponent("phone", phone);
        dialog.registerComponent("active", activeCheck);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(saveChangeButton);
        buttonPanel.add(cancelButton);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(dialogPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setContentPane(mainPanel);
    }

    public void showDialog(boolean value) {
        this.dialog.setVisible(value);
    }

    public String getUpdatedName() {
        return dialog.getFieldValue("name");
    }
    public String getUpdatedAddress() {
        return dialog.getFieldValue("address");
    }
    public String getUpdatedPhone() {
        return dialog.getFieldValue("phone");
    }
    public boolean getUpdatedActive() {
        return dialog.getCheckBoxValue("active");
    }
    public void setSaveEditAction(ActionListener listener) {
        saveChangeButton.addActionListener(listener);
    }
    public void setCancelEditAction(ActionListener listener) {
        cancelButton.addActionListener(listener);
    }

    public void loadDataToTable(List<Branch> branches) {
        branchTable.clearTable();
        for (Branch branch : branches) {
            Object[] rowData = {
                branch.getId(),
                branch.getName(),
                branch.getAddress(),
                branch.getPhone(),
                branch.isActive()
            };
            branchTable.addRow(rowData);
        }
    }

    public Branch getUpdatedBranch() {
        return new Branch(
            Integer.parseInt(getSelectedBranchId()),
            getUpdatedName(),
            getUpdatedAddress(),
            getUpdatedPhone()
        );
    }

    public Branch getNewBranch() {
        return new Branch(
            0,
            dialog.getFieldValue("name"),
            dialog.getFieldValue("address"),
            dialog.getFieldValue("phone")
        );
    }

    public void setSaveAddAction(ActionListener listener) {
        saveAddButton.addActionListener(listener);
    }
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    public void initdialogforadding() {
        JPanel dialogPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        dialogPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JTextField name = new JTextField();
        JTextField address = new JTextField();
        JTextField phone = new JTextField();
        JCheckBox activeCheck = new JCheckBox("Hoạt động");
        activeCheck.setSelected(true);

        dialogPanel.add(new JLabel("Tên chi nhánh:"));
        dialogPanel.add(name);
        dialogPanel.add(new JLabel("Địa chỉ:"));
        dialogPanel.add(address);
        dialogPanel.add(new JLabel("Số điện thoại:"));
        dialogPanel.add(phone);
        dialogPanel.add(new JLabel("Trạng thái:"));
        dialogPanel.add(activeCheck);

        dialog.registerComponent("name", name);
        dialog.registerComponent("address", address);
        dialog.registerComponent("phone", phone);
        dialog.registerComponent("active", activeCheck);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(saveAddButton);
        buttonPanel.add(cancelButton);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(dialogPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setContentPane(mainPanel);
    }

    // public static void main(String[] args) {
    //     SwingUtilities.invokeLater(() -> {
    //         MainView mainView = new MainView("ADMIN");
    //         mainView.addPanel(new BranchListView(), "branch");
    //         mainView.setVisible(true);
    //         mainView.showPanel("branch");
    //     });
    // }
}
