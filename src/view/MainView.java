package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainView extends JFrame {
    private JMenuBar menuBar;
    private JMenu fieldMenu, bookingMenu, customerMenu, salesMenu, reportMenu, systemMenu;
    private JMenuItem fieldStatusItem, manageFieldsItem;
    private JMenuItem bookingItem, bookingListItem, monthlyBookingItem;
    private JMenuItem customerItem, customerListItem;
    private JMenuItem salesItem, productItem, inventoryItem;
    private JMenuItem revenueReportItem, transactionItem;
    private JMenuItem branchItem, userItem, settingsItem;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    public MainView(String role) {
        setTitle("Hệ thống Quản lý Sân Bóng");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        //setBackground(Color.cyan);
        // Initialize layout
        mainPanel = new JPanel();
        cardLayout = new CardLayout();
        mainPanel.setLayout(cardLayout);
        
        // Setup menu bar
        setupMenuBar(role);
        
        // Add main content panel
        add(mainPanel);
    }

    private void setupMenuBar(String role) {
        menuBar = new JMenuBar();

        // Field Menu
        fieldMenu = new JMenu("Quản lý Sân");
        fieldStatusItem = new JMenuItem("Tình trạng Sân");
        manageFieldsItem = new JMenuItem("Quản lý Sân");

        // Booking Menu
        bookingMenu = new JMenu("Đặt Sân");
        bookingItem = new JMenuItem("Đặt Sân");
        bookingListItem = new JMenuItem("Danh sách Đặt Sân");
        //monthlyBookingItem = new JMenuItem("Đặt Sân Theo Tháng");

        // Customer Menu
        customerMenu = new JMenu("Khách Hàng");
        customerItem = new JMenuItem("Thêm Khách Hàng");
        customerListItem = new JMenuItem("Danh sách Khách Hàng");

        // Sales Menu
        salesMenu = new JMenu("Bán Hàng & Kho");
        salesItem = new JMenuItem("Bán Hàng");
        productItem = new JMenuItem("Quản lý Sản Phẩm");
        //inventoryItem = new JMenuItem("Quản lý Kho");

        // Report Menu
        reportMenu = new JMenu("Báo Cáo");
        revenueReportItem = new JMenuItem("Báo Cáo Doanh Thu");
        transactionItem = new JMenuItem("Quản lý Thu Chi");

        // System Menu
        systemMenu = new JMenu("Hệ Thống");
        branchItem = new JMenuItem("Quản lý Chi Nhánh");
        userItem = new JMenuItem("Quản lý Người Dùng");
        settingsItem = new JMenuItem("Thiết Lập");//thay doi mk


        // Add all menus to menu bar
        fieldMenu.add(fieldStatusItem);
        fieldMenu.add(manageFieldsItem);

        bookingMenu.add(bookingItem);
        bookingMenu.add(bookingListItem);

        customerMenu.add(customerItem);
        customerMenu.add(customerListItem);

        salesMenu.add(salesItem);
        salesMenu.add(productItem);

        reportMenu.add(revenueReportItem);
        reportMenu.add(transactionItem);

        systemMenu.add(branchItem);
        systemMenu.add(userItem);
        systemMenu.add(settingsItem);

        // Add menu vào menuBar theo role
        switch (role) {
            case "ADMIN":
                menuBar.add(fieldMenu);
                menuBar.add(bookingMenu);
                menuBar.add(customerMenu);
                menuBar.add(salesMenu);
                menuBar.add(reportMenu);
                menuBar.add(systemMenu);
                break;
            case "MANAGER":
                menuBar.add(fieldMenu);
                menuBar.add(bookingMenu);
                menuBar.add(customerMenu);
                menuBar.add(salesMenu);
                menuBar.add(reportMenu);
                // Ẩn menu "Quản lý Người Dùng"
                systemMenu.remove(userItem);
                menuBar.add(systemMenu);
                break;
            case "STAFF":
                menuBar.add(fieldMenu);
                menuBar.add(bookingMenu);
                menuBar.add(customerMenu);
                menuBar.add(salesMenu);
                // Không add reportMenu cho STAFF
                // Ẩn "Quản lý Chi Nhánh" và "Quản lý Người Dùng"
                systemMenu.remove(branchItem);
                systemMenu.remove(userItem);
                menuBar.add(systemMenu);
                break;
            default:
                menuBar.add(fieldMenu);
                menuBar.add(bookingMenu);
                menuBar.add(customerMenu);
                menuBar.add(salesMenu);
                menuBar.add(reportMenu);
                menuBar.add(systemMenu);
                break;
        }

        setJMenuBar(menuBar);
    }
    
    public void addPanel(JPanel panel, String name) {
        mainPanel.add(panel, name);
    }
    
    public void showPanel(String name) {
        cardLayout.show(mainPanel, name);
    }
    
    // Action listeners for menu items
    public void setFieldStatusAction(ActionListener listener) {
        fieldStatusItem.addActionListener(listener);
    }
    
    public void setManageFieldsAction(ActionListener listener) {
        manageFieldsItem.addActionListener(listener);
    }
    
    public void setBookingAction(ActionListener listener) {
        bookingItem.addActionListener(listener);
    }
    
    public void setBookingListAction(ActionListener listener) {
        bookingListItem.addActionListener(listener);
    }
    
    public void setMonthlyBookingAction(ActionListener listener) {
        monthlyBookingItem.addActionListener(listener);
    }
    
    // Add more action listeners for other menu items...
    public void setCustomerAction(ActionListener listener) {
        customerItem.addActionListener(listener);
    }
    
    public void setCustomerListAction(ActionListener listener) {
        customerListItem.addActionListener(listener);
    }
    
    // Action listeners for sales menu
    public void setSalesAction(ActionListener listener) {
        salesItem.addActionListener(listener);
    }
    
    public void setProductAction(ActionListener listener) {
        productItem.addActionListener(listener);
    }
    
    public void setInventoryAction(ActionListener listener) {
        inventoryItem.addActionListener(listener);
    }
    
    // Action listeners for report menu
    public void setRevenueReportAction(ActionListener listener) {
        revenueReportItem.addActionListener(listener);
    }
    
    public void setTransactionAction(ActionListener listener) {
        transactionItem.addActionListener(listener);
    }
    
    // Action listeners for system menu
    public void setBranchAction(ActionListener listener) {
        branchItem.addActionListener(listener);
    }
    
    public void setUserAction(ActionListener listener) {
        userItem.addActionListener(listener);
    }
    
    public void setSettingsAction(ActionListener listener) {
        settingsItem.addActionListener(listener);
    }
    // public static void main(String[] args) {
    //    SwingUtilities.invokeLater(() -> {
    //         MainView mainView = new MainView("ADMIN");
    //         mainView.setVisible(true);
    //     });
    // }
}