package view;

import model.Customer;
import utils.ConvertToVnd;
import view.components.TableComponent;
import javax.swing.*;
//import javax.swing.table.TableCellRenderer;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import view.components.DialogComponent;

public class CustomerListView extends JPanel {
    private JTextField searchField;
    private JButton searchButton;
    private JButton addButton;
    private TableComponent<Customer> customerTable;
    private JButton editButton ;
    private JButton deleteButton;
    private JButton viewRefreshButton;
    private JButton saveChangeButton = new JButton("Lưu");
    private JButton cancelButton = new JButton("Hủy");
    private DialogComponent dialog = new DialogComponent("Sửa thông tin khách hàng",400, 300);
    //private CustomerController customerController = new CustomerController(this);
    
    public CustomerListView() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        // Title
        JLabel titleLabel = new JLabel("DANH SÁCH KHÁCH HÀNG", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(titleLabel, BorderLayout.NORTH);
        
        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        searchPanel.add(new JLabel("Tìm kiếm:"));
        
        searchField = new JTextField(20);
        searchPanel.add(searchField);
        
        searchButton = new JButton("Tìm");
        searchPanel.add(searchButton);
        
        addButton = new JButton("Thêm Khách Hàng");
        JPanel addButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        addButtonPanel.add(addButton);
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(searchPanel, BorderLayout.WEST);
        topPanel.add(addButtonPanel, BorderLayout.EAST);
        topPanel.add(titleLabel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);
        
        // Customer table
        
        String[] columnNames = {"Mã KH", "Tên khách hàng", "Số điện thoại", "Email","Tổng tiền đã chi"};
        customerTable = new TableComponent<Customer>(columnNames);
        //customerTable.setColumnNames(columnNames);
        customerTable.setFillsViewportHeight(true);
        JScrollPane tableScrollPane = new JScrollPane(customerTable);
        //tableScrollPane.setPreferredSize(new Dimension(800, 400));
        add(tableScrollPane, BorderLayout.CENTER);

        // Button panel (move to bottom)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        editButton = new JButton("Sửa");
        deleteButton = new JButton("Xóa");
        viewRefreshButton = new JButton("Làm Mới");
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(viewRefreshButton);
        add(buttonPanel, BorderLayout.SOUTH);
        //dialog = new DialogComponent("Sửa thông tin khách hàng",400, 300);
        dialog.setLocationRelativeTo(this); 
        dialog.setVisible(false);
        //saveChangeButton = new JButton("Lưu");
        //cancelButton = new JButton("Hủy");
        //initdialog();// Center the dialog relative to the main view
        //JPanel 
        //loadCustomerList();
    }
    
    public void initdialog(String nameString,String phoneString, String emailString) {
        JPanel dialogPanel = new JPanel(new GridLayout(5,2,10,10));
        dialogPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField name = new JTextField();
        JTextField phone = new JTextField();
        JTextField email = new JTextField();
        name.setText(nameString);
        phone.setText(phoneString);
        email.setText(emailString);
        //JTextField totalSpent = new JTextField();
        dialogPanel.add(new JLabel("Tên khách hàng:"));
        dialogPanel.add(name);
        dialogPanel.add(new JLabel("Số điện thoại:"));
        dialogPanel.add(phone);
        dialogPanel.add(new JLabel("Email:"));
        dialogPanel.add(email);

        dialog.registerComponent("name", name);
        dialog.registerComponent("phone", phone);
        dialog.registerComponent("email", email);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(saveChangeButton);
        buttonPanel.add(cancelButton);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(dialogPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setContentPane(mainPanel);
        //dialog.setVisible(false);
        /*for (ActionListener al : cancelButton.getActionListeners()) {
            cancelButton.removeActionListener(al);
        }*/
        //saveChangeButton.addActionListener(e -> dialog.setVisible(false));
        //saveChangeButton.addActionListener(e->setSaveEditAction(e));
        //cancelButton.addActionListener(e -> dialog.setVisible(false));
    }
    public void showDialog(boolean value) {
        this.dialog.setVisible(value);
    }
    
    public void loadCustomerList(List<Customer> customers) {
        customerTable.clearTable(); // Clear existing rows
        //CustomerService customerService = new CustomerService(); // Assuming you have a service class to handle customer data
        //List<Customer> customers = customerService.getAllCustomers(); // Assuming you have a method to get all customers
        for (Customer customer : customers) {
            Object[] rowData = {
                customer.getId(),
                customer.getName(),
                customer.getPhone(),
                customer.getEmail(),
                //customer.getCustomerType()
                ConvertToVnd.formatCurrency((long)customer.getTotalSpent()) 
            };
            customerTable.addRow(rowData);
        }
    customerTable.revalidate();
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }
    public String getUpdatedName() {
        return dialog.getFieldValue("name");
    }
    public String getUpdatedPhone() {
        return dialog.getFieldValue("phone");
    }
    public String getUpdatedEmail() {
        return dialog.getFieldValue("email");
    }
    public int getSelectedCustomerIndex() {
        return customerTable.getSelectedRow();
    }

    public String getSelectedCustomerId() {
        int selectedRow = customerTable.getSelectedRow();
        if (selectedRow != -1) {
            String id = Integer.toString((int) customerTable.getValueAt(selectedRow, 0)); // Assuming the ID is in the first column
            return id; // Assuming the ID is in the first column
        }
        return null;
    }

    public String getSelectedCustomerName() {
        int selectedRow = customerTable.getSelectedRow();
        if (selectedRow != -1) {
            return (String) customerTable.getValueAt(selectedRow, 1); // Assuming the name is in the second column
        }
        return null;
        
    }
    public String getSelectedCustomerPhone() {
        int selectedRow = customerTable.getSelectedRow();
        if (selectedRow != -1) {
            return (String) customerTable.getValueAt(selectedRow, 2); // Assuming the phone is in the third column
        }
        return null;
    }
    
    public String getSearchText() {
        return searchField.getText();
    }
    
    public void setAddAction(ActionListener listener) {
        addButton.addActionListener(listener);
    }
    public void setEditAction(ActionListener listener) {
        editButton.addActionListener(listener);
    }
    
    public void setSaveEditAction(ActionListener listener) {
        saveChangeButton.addActionListener(listener);
    }
    public void setCancelEditAction(ActionListener listener) {
        cancelButton.addActionListener(listener);
    }
    
    public void setDeleteAction(ActionListener listener) {
        deleteButton.addActionListener(listener);
    }
    
    public void setRefreshAction(ActionListener listener) {
        viewRefreshButton.addActionListener(listener);
    }
    
    public void setSearchAction(ActionListener listener) {
        searchButton.addActionListener(listener);
    }
    // public static void main(String[] args) {
    //     SwingUtilities.invokeLater(() -> {
    //         MainView mainView = new MainView("ADMIN");
    //         mainView.addPanel(new CustomerListView(), "1");
    //         mainView.setVisible(true);
    //         mainView.showPanel("1");
    // });
    // }
}