package view;
import model.Booking;
import utils.DateTimeUtils;
import view.components.TableComponent;
import javax.swing.*;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.List;

public class BookingListView extends JPanel {
    private JTextField searchField;
    private JButton searchButton;
    private JButton addButton;
    private TableComponent<Booking> bookingTable;
    private JButton editButton;
    private JButton deleteButton;
    private JButton printButton;
    private JButton refreshButton;
    private JTextArea infoTextField;

    public BookingListView() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Title
        JLabel titleLabel = new JLabel("DANH SÁCH ĐẶT SÂN", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(titleLabel, BorderLayout.NORTH);

        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Tìm kiếm:"));
        searchField = new JTextField(20);
        searchPanel.add(searchField);
        searchButton = new JButton("Tìm");
        searchPanel.add(searchButton);

        addButton = new JButton("Thêm Đặt Sân");
        JPanel addButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        addButtonPanel.add(addButton);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(searchPanel, BorderLayout.WEST);
        topPanel.add(addButtonPanel, BorderLayout.EAST);
        topPanel.add(titleLabel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        // Booking table
        String[] columnNames = {
            "Mã đặt sân", "Sân", "Ngày", "Giờ bắt đầu", "Giờ kết thúc", "Khách hàng", "isPeriodic"
        };
        bookingTable = new TableComponent<>(columnNames);
        bookingTable.setFillsViewportHeight(true);
        JScrollPane tableScrollPane = new JScrollPane(bookingTable);
        add(tableScrollPane, BorderLayout.CENTER);

        // Button panel (right side)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setAlignmentY(Component.TOP_ALIGNMENT);
        buttonPanel.setPreferredSize(new Dimension(200, 400)); 

        // Các nút chức năng
        editButton = new JButton("Sửa");
        deleteButton = new JButton("Xóa");
        printButton = new JButton("In");
        refreshButton = new JButton("Làm Mới");

        // Đặt cùng kích thước cho các nút để thẳng hàng
        Dimension btnSize = new Dimension(12000, 30);
        editButton.setMaximumSize(btnSize);
        deleteButton.setMaximumSize(btnSize);
        printButton.setMaximumSize(btnSize);
        refreshButton.setMaximumSize(btnSize);

        buttonPanel.add(editButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(deleteButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(printButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(refreshButton);
        buttonPanel.add(Box.createVerticalStrut(20)); // Khoảng cách lớn hơn trước infoTextField

        // Thêm text box bên dưới các nút, căn lề trái phải sát với nút
        infoTextField = new JTextArea();
        
        //infoTextField.setMaximumSize(new Dimension(120, 60));
        infoTextField.setPreferredSize(new Dimension(editButton.getWidth(), 60));
        infoTextField.setLineWrap(true);
        infoTextField.setWrapStyleWord(true);
        infoTextField.setAlignmentX(editButton.getAlignmentX());
        infoTextField.setVisible(false);
        buttonPanel.add(infoTextField);

        // Đẩy các thành phần lên trên cùng
        buttonPanel.add(Box.createVerticalGlue());

        add(buttonPanel, BorderLayout.EAST);

        // bookingTable.getSelectionModel().addListSelectionListener(e -> {
        //     if (!e.getValueIsAdjusting()) {
        //         int selectedRow = bookingTable.getSelectedRow();
        //         if (selectedRow != -1) {
        //             // Lấy giá trị từng cột của dòng đang chọn
        //             Object bookingId = bookingTable.getValueAt(selectedRow, 0);
        //             Object pitch = bookingTable.getValueAt(selectedRow, 1);
        //             Object isPeriodic = bookingTable.getValueAt(selectedRow, 6);

        //             // Ví dụ: Ẩn/hiện nút In nếu không phải đặt định kỳ
        //             if (isPeriodic instanceof Boolean && (Boolean) isPeriodic) {
        //                 infoTextField.setVisible(true);
        //             } else {
        //                 infoTextField.setVisible(false);
        //             }
        //             // Thực hiện các logic khác tùy ý bạn ở đây
        //             //System.out.println("Selected bookingId: " + bookingId + ", pitch: " + pitch);
        //         } 
        //     }
        // });
    }

    // public void setPrint(){
    //     printButton.setVisible(false);
    // }
    

    public void loadBookingList(List<Booking> bookings) {
        bookingTable.clearTable();
        
        for (Booking booking : bookings) {
            LocalDateTime date = booking.getDate();
            
            Object[] rowData = {
                booking.getId(),
                booking.getPitchId(),
                date != null ? date.toLocalDate() : null,
                booking.getStartTime(),
                booking.getEndTime(),
                booking.getCustomerId(),
                booking.isPeriodic() //? "Đặt định kỳ" : "Đặt một lần"
            };
            bookingTable.addRow(rowData);
        }
        bookingTable.revalidate();
    }

    public void setinfoTextFieldVisible(Boolean visibleValue){
        infoTextField.setVisible(visibleValue);
    }

    public void setMonthlyBookinginfo(String text){
        infoTextField.setText(text);
        infoTextField.setVisible(true);
    }

    public int getSelectedBookingIndex() {
        return bookingTable.getSelectedRow();
    }
    public Object getSelectedBookingValue(int col){
        return bookingTable.getValueAt(getSelectedBookingIndex(), col);
    }

    public int getSelectedBookingId() {
        int selectedRow = bookingTable.getSelectedRow();
        if (selectedRow != -1) {
            return (int)bookingTable.getValueAt(selectedRow, 0);
        }
        return -1;
    }

    public Booking getSelectedBooking() {
    int selectedRow = bookingTable.getSelectedRow();
    LocalDateTime date;
    LocalDateTime startTime;
    LocalDateTime endTime;
    if (selectedRow != -1) {
        int id = (int) bookingTable.getValueAt(selectedRow, 0);
        int pitchId = (int) bookingTable.getValueAt(selectedRow, 1);
        // Nếu bạn lưu customerId là int, còn nếu là String thì chuyển lại cho đúng
        int customerId = (int) bookingTable.getValueAt(selectedRow, 5);
        // Giả sử date, startTime, endTime là LocalDateTime, nếu không thì cần chuyển đổi lại
        Object dateObj = bookingTable.getValueAt(selectedRow, 2);
        Object startTimeObj = bookingTable.getValueAt(selectedRow, 3);
        Object endTimeObj = bookingTable.getValueAt(selectedRow, 4);
        startTime = (LocalDateTime)startTimeObj;
        endTime = (LocalDateTime)endTimeObj;
        // Chuyển đổi sang LocalDateTime nếu cần
        if(dateObj == null){
            date = LocalDateTime.now();
        }
        else date = DateTimeUtils.parseDate(dateObj.toString());
        boolean isPeriodic = false;
        Object periodicObj = bookingTable.getValueAt(selectedRow, 6);
        if (periodicObj instanceof Boolean) {
            isPeriodic = (Boolean) periodicObj;
        } else if (periodicObj instanceof String) {
            isPeriodic = Boolean.parseBoolean((String) periodicObj);
        }
        // Nếu có thêm các trường khác như totalPrice, status, note thì lấy tiếp
        double totalPrice = 0;
        String note = "";
        // Nếu bạn có các cột này trong bảng thì lấy ra, nếu không thì giữ mặc định

        return new Booking(id, pitchId, customerId, date, startTime, endTime, totalPrice, isPeriodic, note);
    }
    return null;
}

    public Object isPeriodic(){
        return bookingTable.getValueAt(bookingTable.getSelectedRow(),6);
    }

    public String getSearchText() {
        return searchField.getText();
    }

    //addaction field
    public void setAddAction(ActionListener listener) {
        addButton.addActionListener(listener);
    }
    public void setEditAction(ActionListener listener) {
        editButton.addActionListener(listener);
    }
    public void setDeleteAction(ActionListener listener) {
        deleteButton.addActionListener(listener);
    }
    public void setPrintAction(ActionListener listener) {
        printButton.addActionListener(listener);
    }
    public void setRefreshAction(ActionListener listener) {
        refreshButton.addActionListener(listener);
    }
    public void setSearchAction(ActionListener listener) {
        searchButton.addActionListener(listener);
    }

    public void setSeclectionBookingTableAction(ListSelectionListener listerner){
        bookingTable.getSelectionModel().addListSelectionListener(listerner);
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }



    // public static void main(String[] args) {
    //     SwingUtilities.invokeLater(() -> {
    //         MainView mainView = new MainView("ADMIN");
    //         mainView.addPanel(new BookingListView(), "1");
    //         mainView.setVisible(true);
    //         mainView.showPanel("1");
    //     });
    // }
}