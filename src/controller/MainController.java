package controller;
import model.User;
import view.*;

import javax.swing.JOptionPane;

import DAO.UserDAO;
import DAO.impl.UserDAOImpl;

public class MainController {
    private MainView mainView;
    private LoginView loginView;
    private UserDAO userDAO = new UserDAOImpl();
    private BookingController bookingController;
    private CustomerController customerController;
    private UserController userController;
    private User currentUser;
    private BookingView bookingView = new BookingView();
    private BookingListView bookingListView = new BookingListView();
    private CustomerView customerView = new CustomerView();
    private CustomerListView customerListView = new CustomerListView();
    private ManageFieldsView manageFieldsView = new ManageFieldsView();
    private UserListView userListView = new UserListView();
    private BranchListView branchListView = new BranchListView();
    private SettingView settingView = new SettingView();
    private PitchListView pitchListView = new PitchListView();
    private SaleView saleView = new SaleView();
    private TransactionListView transactionListView= new TransactionListView();
    private BranchController branchController;
    private PitchController pitchController;
    private SaleController saleController;
    private ReportController reportController;
    private ProductController productController;
    private ProductView productView = new ProductView();
    private ProductListView productListView = new ProductListView();
    private ReportView reportView = new ReportView();
    
    public MainController(MainView mainView,LoginView loginView) {
        this.mainView = mainView;
        this.loginView = loginView;
        this.userDAO = new UserDAOImpl();
    }
    public MainController(){
        this.loginView = new LoginView();
        this.userDAO = new UserDAOImpl();
    }
    
    
    public void start() {
        loginView.setVisible(true);
        loginView.setLoginAction(e -> {
            if (authenticate()) {
                // If authentication is successful, show the main view
                currentUser = userDAO.findByUsername(loginView.getUsername());
                loginView.setVisible(false); 
                this.mainView = new MainView(currentUser.getRole());
                loadpanel(mainView);
                mainView.setVisible(true);
                loginView.showWelcomeMessage(currentUser.getRole());
                handleProductManagement();
                handleBookingManagement();
                handleCustomerManagement();
                handlePitchManagement();
                handleUserManagement();
                handleBranchManagement();
                handleSetting();
                handeSalesManagement();
                handleReportManagement();
            } else {
                // Show an error message if authentication fails
                loginView.showError("Tên đăng nhập hoặc mật khẩu không đúng.");
            }
        });
    }

    public void start(String username ) {
                currentUser = userDAO.findByUsername(username);
                loginView.setVisible(false); 
                this.mainView = new MainView(currentUser.getRole());
                loadpanel(mainView);
                mainView.setVisible(true);
                handleProductManagement();
                
                handleBookingManagement();
                handleCustomerManagement();
                handlePitchManagement();
                handleUserManagement();
                handleBranchManagement();
                handleSetting();
                handeSalesManagement();
                handleReportManagement();
    }
    
    //khoi tai panel va khoi tao controler ung voi tung view
    private void loadpanel(MainView mainView) {
            mainView.addPanel(bookingView, "bookingview");
            mainView.addPanel(bookingListView, "bklist");
            mainView.addPanel(customerView, "customerView");
            mainView.addPanel(customerListView, "customerListView");
            mainView.addPanel(manageFieldsView, "manageFieldsView");
            mainView.addPanel(bookingView, "bookingview");
            mainView.addPanel(bookingListView, "bklist");
            mainView.addPanel(userListView, "userview");
            mainView.addPanel(branchListView, "branchListView");
            mainView.addPanel(settingView, "settingview");
            mainView.addPanel(pitchListView,"pitchListView");
            mainView.addPanel(saleView, "saleview");
            mainView.addPanel(transactionListView, "transactionListView");
            mainView.addPanel(productListView, "productListView");
            mainView.addPanel(productView, "productView");
            mainView.addPanel(reportView, "reportView");
            mainView.setBookingAction(e->{
                mainView.showPanel("bookingview");
            });
            mainView.setBookingListAction(e->{
                mainView.showPanel("bklist");
            });
            mainView.setCustomerAction(e->{
                mainView.showPanel("customerView");
            });
            mainView.setCustomerListAction(e->{
                mainView.showPanel("customerListView");
            });
            mainView.setManageFieldsAction(e->{
                mainView.showPanel("manageFieldsView");                             
            });
            mainView.setUserAction(e->{
                mainView.showPanel("userview");
            });
            mainView.setBranchAction(e->{
                mainView.showPanel("branchListView");       
            });
            mainView.setSettingsAction(e->{
                mainView.showPanel("settingview"); 
            });
            mainView.setFieldStatusAction(e->{
                mainView.showPanel("pitchListView");
            });
            mainView.setSalesAction(e->{
                //saleController.loadProducts();
                mainView.showPanel("saleview");
            });
            mainView.setTransactionAction(e->{
                mainView.showPanel("transactionListView");
            });
            mainView.setProductAction(e->{
                mainView.showPanel("productListView");
            });
            mainView.setRevenueReportAction(e->{
                mainView.showPanel("reportView");
            });
            mainView.showPanel("saleview");
        
        
        this.customerController = new CustomerController(customerView,customerListView);
        this.bookingController = new BookingController(bookingView,bookingListView);
        this.userController = new UserController(userListView,settingView);
        this.branchController = new BranchController(branchListView);
        this.pitchController = new PitchController(pitchListView,manageFieldsView);
        this.saleController = new SaleController(saleView);
        this.reportController = new ReportController(transactionListView,reportView);
        this.productController = new ProductController(productView, productListView);
    }

    private boolean authenticate() {

        //this.userDAO = new UserDAOImpl();
        String username = loginView.getUsername();
        String password = loginView.getPassword();
        
        return userDAO.authenticate(username, password);
    }

    private void handlePitchManagement() {
        pitchController.loadDataForManageFieldsView();
        pitchController.loadComboBoxData();
        pitchController.loadPitchStatusdata();

        pitchListView.setComboBoxAction(e->{
            pitchController.loadPitchStatusdata();
        });
        pitchListView.setAddAction(e->{
            int selectedBranchId = pitchListView.getSelectedBranchId();
            pitchListView.initDialogForAdding(String.valueOf(selectedBranchId));
            pitchListView.showDialog(true);
        });
        pitchListView.setSaveAddAction(e->{
            pitchController.processAddPitch();
        });
        pitchListView.setCancelAction(e->{
            pitchListView.showDialog(false);
        });
        pitchListView.setEditAction(e->{
            if(pitchListView.getSelectedPitchId() != -1){
                pitchController.displayEditPitch();
                pitchListView.showDialog(true);
            }
            else pitchListView.showMessage("Vui lòng chọn một sân để chỉnh sửa.");
            
        });
        pitchListView.setSaveEditAction(e->{
            pitchController.processUpdatePitch();
        });
        pitchListView.setDeleteAction(e->{
            if(pitchListView.getSelectedPitchId() == -1){
                pitchListView.showMessage("Vui lòng chọn một sân để xóa.");
                return;
            }
            pitchController.processDeletePitch();
        });
        pitchListView.setRefreshAction(e->{
            pitchController.loadPitchStatusdata();
        });

        manageFieldsView.setAddAction(()->{
            bookingView.setData(manageFieldsView.getSelectedBooking());
            mainView.showPanel("bookingview");
        });
        manageFieldsView.setComboBoxAction(e->{
            System.out.println("cbbox");
            pitchController.reloadTimeslotTable();
        });
        manageFieldsView.setCalendarAction(evt->{
            System.out.println("calendar");
            pitchController.reloadTimeslotTable();
        }); 
    }

    private void handleBookingManagement() {
        bookingController.loadBookingData();
        bookingController.loadBookingListData();
        bookingView.setAddCustomerAction(e->{
            mainView.showPanel("customerView");
        });
        
        bookingView.setSaveAction(e->{
            if(bookingController.processNewBooking()){
                bookingView.displaySucess();
            }
        });

        bookingView.setCancelAction(e->{
            mainView.showPanel("bklist");
            bookingView.setButtonForEdit(false);
        });


        bookingListView.setAddAction(e->{
            mainView.showPanel("bookingview");
        });
        bookingListView.setSeclectionBookingTableAction(e->{
            if (!e.getValueIsAdjusting()) {
                int selectedRow = bookingListView.getSelectedBookingIndex();
                if (selectedRow != -1) {
                    // Lấy giá trị từng cột của dòng đang chọn

                    // Ví dụ: Ẩn/hiện nút In nếu không phải đặt định kỳ
                    if ((boolean)bookingListView.isPeriodic().equals(true)) {
                        bookingController.setInfoTextField();
                    } else {
                        bookingListView.setinfoTextFieldVisible(false);
                    }
                    // Thực hiện các logic khác tùy ý bạn ở đây
                    //System.out.println("Selected bookingId: " + bookingId + ", pitch: " + pitch);
                } 
            }
        });
        bookingListView.setEditAction(e->{
            bookingController.displayEditData();
            bookingView.setButtonForEdit(true);
            mainView.showPanel("bookingview");
        });
        bookingView.setSaveEditAction(e->{
            if(bookingController.processEditBooking()){//processEdit booking la luu booking sua thanh booking moi va xoa booking cu di
                bookingView.displayError("Cập nhật thông tin đặt sân thành công.");
                mainView.showPanel("bklist");
            }
        });
        bookingListView.setRefreshAction(e->{
            bookingController.loadBookingListData();
        });
        bookingListView.setDeleteAction(e->{
            bookingController.processDeleteBooking();
        });
    }

    private void handleCustomerManagement() {
        customerController.loadCustomerList();
        customerView.setCancelAction(e->{
            mainView.showPanel("bookingview");
        });
        customerView.setSaveAction(e->{
            customerController.processNewCustomer();
        });        
        customerListView.setAddAction(e->{
            mainView.showPanel("customerView");
        });
        customerListView.setEditAction(e->{            
            int selectedIndex = customerListView.getSelectedCustomerIndex();
            if (selectedIndex == -1) {
                customerListView.showError("Vui lòng chọn một khách hàng để chỉnh sửa.");
                return;
            } 
            else{
                customerController.displayUpdateCustomer();
                customerListView.showDialog(true);                
            }
        });
        customerListView.setSaveEditAction(e->{
            customerController.processUpdateCustomer();
        });
        customerListView.setCancelEditAction(e->{
            customerListView.showDialog(false);
        });
        customerListView.setDeleteAction(e->{
        customerController.processDeleteCustomer();
        });
        customerListView.setRefreshAction(e->{
        customerController.loadCustomerList();
        });
        customerListView.setSearchAction(e->{
        customerController.processSearchCustomer();
        });
    }

    private void handleUserManagement() {
        userController.loadcbdata();
        userController.loadData();
        userListView.setCBaction(e->{
            userController.loadData();
        });
        userListView.setEditAction(e->{
            userController.processEditUser();
        });
        userListView.setSaveEditAction(e->{
            userController.processUpdateUser();
            userListView.showDialog(false);
        });
        userListView.setCancelAction(e->{
            userListView.showDialog(false);
        });
        userListView.setRefreshAction(e->{
            userController.loadData();
        });
        userListView.setDeleteAction(e->{
            userController.processDeleteCustomer();
        });
        userListView.setAddUserAction(e->{
            userListView.initdialogforadding();
            userListView.showDialog(true);
        });
        userListView.setSaveAddAction(e->{
            userController.processAddUser();
            userListView.showDialog(false);
        });      
    }   

    private void handleBranchManagement() {
        branchController.loadData();
        branchListView.setAddBranchAction(e->{
            branchListView.initdialogforadding();
            branchListView.showDialog(true);
        });
        branchListView.setEditAction(e->{
            branchController.processEditBranch();
        });
        branchListView.setSaveEditAction(e->{
            branchController.processUpdateBranch();
            branchController.loadData();
            branchListView.showDialog(false);
        });
        branchListView.setCancelEditAction(e->{
            branchListView.showDialog(false);
        });
        branchListView.setDeleteAction(e->{
            branchController.processDeleteBranch();
            branchController.loadData();
        });
        branchListView.setSaveAddAction(e->{
            branchController.processAddBranch();
            branchController.loadData();
            branchListView.showDialog(false);
        });
        branchListView.setRefeshAction(e->{
            branchController.loadData();
        });
    }

    private void handleSetting(){
        userController.loadSettingData(currentUser);
        settingView.setChangePasswordAction(e->{
            userController.processChangePassword(currentUser);
            userController.loadSettingData(currentUser);
        });
        settingView.setChangeEmailAction(e->{
            userController.processChangeEmail(currentUser);
            userController.loadSettingData(currentUser);
        });
        settingView.setChangePhoneAction(e->{
            String newPhone = JOptionPane.showInputDialog(settingView,"Nhập số điện thoại mới :",currentUser.getPhone());
            if(newPhone != null && !newPhone.trim().isEmpty()){
            userController.processChangePhone(currentUser,newPhone);
            userController.loadSettingData(currentUser);}
        });
        settingView.setLogoutAction(e->{
            this.mainView.dispose();
            new MainController().start();
        });
        settingView.setDeleteAccountAction(e->{
            if(userController.processDeleteUser(currentUser)){
                this.mainView.dispose();
                new MainController().start();
            }
        });
    }

    private void handeSalesManagement() {
        // Load dữ liệu sản phẩm và khách hàng
        int currentBranchId = currentUser.getBranchId();
        saleController.loadProducts(currentBranchId);

        //Nút Thanh toán: nếu chọn YES thì gọi hành động Yes
        saleView.setCheckoutAction(e -> {
            //System.out.println("ma : "+ saleView.checkout());
            if (saleView.checkout(currentUser.getFullName()) == 1) {
                 saleController.processSaveInvoice();     // Lưu hóa đơn
                 saleController.loadProducts(currentBranchId);           // Làm mới lại sau khi thanh toán
             }
             else System.out.println("Hủy thanh toán");
        });

        // Nút Thêm khách hàng → chuyển sang view quản lý khách hàng
        saleView.setAddCustomerAction(e -> {
            mainView.showPanel("customerView");
        });
    }

    private void handleReportManagement() {
        reportController.LoadTransactionListData(0);
        reportController.loadReportData();
        transactionListView.setFilterAction(e->{
            int selectedIndex = transactionListView.getSelectedFilterIndex();
            transactionListView.setBtnforExpense(selectedIndex == 3);//neu la expense thi se hien btn
            reportController.LoadTransactionListData(selectedIndex);
        });
        transactionListView.setAddAction(e->{
            reportController.initdialog();
            transactionListView.showDialog(true);
        });
        transactionListView.setDeleteAction(e->{
            int selectedId = transactionListView.getSelectedTransactionId();
            if(selectedId != -1){
                int reply = JOptionPane.showConfirmDialog(null, "Xác nhận xóa ?"
                , "Xác nhận", JOptionPane.YES_NO_OPTION);
                if(reply == JOptionPane.YES_OPTION){
                    if(reportController.processDeleteExpense(selectedId)){
                    reportController.LoadTransactionListData(transactionListView.getSelectedFilterIndex());
                    }
                }
            }
        });
        transactionListView.setRefreshAction(e->{
            reportController.LoadTransactionListData(transactionListView.getSelectedFilterIndex());
        });
        transactionListView.setCancelAction(e->{
            transactionListView.showDialog(false);
        });
        transactionListView.setSaveAction(e->{
            if(reportController.processSaveExpense()){
                transactionListView.showDialog(false);
                reportController.LoadTransactionListData(transactionListView.getSelectedFilterIndex());
            }
        });   
        
        reportView.setFilterDateAction(e->{
            if(reportView.getStartDate() != null && reportView.getEndDate() != null){
                reportController.processFilter();
            } else {
                reportView.showMessage("Vui lòng chọn ngày bắt đầu và kết thúc.");
            }
        });
        reportView.setResetPitch(e-> reportController.loadReportData());
        
    }

    private void handleProductManagement() {
        // Load dữ liệu sản phẩm
        productController.loadProductList();

        // Gán action cho các nút trên ProductListView
        productListView.setAddAction(e -> {
            mainView.showPanel("productView");
        });

        productListView.setEditAction(e -> {
            int selectedIndex = productListView.getSelectedProductIndex();
            if (selectedIndex == -1) {
                productListView.showError("Vui lòng chọn một sản phẩm để chỉnh sửa.");
            } else {
                productController.displayUpdateProduct();
                productListView.showDialog(true);
            }
        });

        productListView.setDeleteAction(e -> {
            productController.processDeleteProduct();
        });

        productListView.setRefreshAction(e -> {
            productController.refreshProduct();
        });

        productListView.setSearchAction(e -> {
            productController.processSearchProduct();
        });

        // Gán action cho các nút trên ProductView (thêm mới)
        productView.setCancelAction(e -> {
            mainView.showPanel("productListView");
        });

        productView.setSaveAction(e -> {
            productController.processSaveProduct();
        });

        // Gán action cho các nút trên ProductListView (chỉnh sửa)
        productListView.setSaveEditAction(e -> {
            productController.processUpdateProduct();
        });

        productListView.setCancelEditAction(e -> {
            productListView.showDialog(false);
        });
    }

}
