package view;

import model.Pitch;
import model.Customer;
import model.Booking;
import utils.DateTimeUtils;

import com.toedter.calendar.JDateChooser;
import view.components.TimeSlotTablePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Date;

public class ManageFieldsView extends JPanel {
    private JComboBox<Pitch> pitchComboBox;
    private JComboBox<Customer> customerComboBox;
    private JDateChooser weekChooser;
    private TimeSlotTablePanel timeSlotTablePanel;
    private JLabel statusLabel;
    private List<LocalDate> weekDates;
    private List<LocalTime> timeSlots;

    public ManageFieldsView() {
        setLayout(new BorderLayout(10, 10));
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        pitchComboBox = new JComboBox<>();
        customerComboBox = new JComboBox<>();
        weekChooser = new JDateChooser();
        weekChooser.setDateFormatString("dd/MM/yyyy");
        weekChooser.setPreferredSize(new Dimension(160, 32));
        weekChooser.setFont(new Font("Arial", Font.PLAIN, 14));
        weekChooser.setDate(new java.util.Date()); 
        statusLabel = new JLabel("Nhấn vào khung giờ để chọn, có thể kéo thả để đăt khung giờ mong muốn");

        topPanel.add(new JLabel("Chọn sân:"));
        topPanel.add(pitchComboBox);
        topPanel.add(new JLabel("Khách hàng:"));
        topPanel.add(customerComboBox);
        topPanel.add(new JLabel("Chọn tuần:"));
        topPanel.add(weekChooser);
        add(topPanel, BorderLayout.NORTH);
        add(statusLabel, BorderLayout.SOUTH);

        // Khởi tạo danh sách khung giờ và ngày trong tuần
        timeSlots = TimeSlotTablePanel.defaultTimeSlots();
        weekDates = getCurrentWeekDates();

        // Lấy booking ban đầu
        

        //loadPitches();
        //loadCustomers();
        //DataTableInit();

        // List<Map<String,Object>> data = bookingService.getAllBookingsMap()
        // .stream().filter(b->(int) b.get("pitchId") == getselectedPitch().getId())
        // .collect(Collectors.toList());
        // timeSlotTablePanel = new TimeSlotTablePanel(weekDates, timeSlots, data);
        timeSlotTablePanel = new TimeSlotTablePanel();
        JScrollPane scrollPane = new JScrollPane(timeSlotTablePanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(40);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(40);
        add(scrollPane, BorderLayout.CENTER);

        

        //pitchComboBox.addActionListener(e -> reloadTable());
        //customerComboBox.addActionListener(e -> statusLabel.setText(""));
        //calendar.getDayChooser().addPropertyChangeListener("day", evt -> reloadTable());//no need property name
    }

    public void setAddAction(TimeSlotTablePanel.SlotClickListener listener) {//set action khi tha chuot timeslot
        timeSlotTablePanel.setSlotClickListener(listener);
    }

    public Booking getSelectedBooking(){
        Booking booking = new Booking();
        Pitch selectedPitch = (Pitch) pitchComboBox.getSelectedItem();
        Customer selectedCustomer = (Customer) customerComboBox.getSelectedItem();
        if (selectedPitch == null || selectedCustomer == null ) {
            statusLabel.setText("Vui lòng chọn đầy đủ thông tin!");
            return null;
        }
        LocalTime start = timeSlotTablePanel.getSelectedStartTime();
        LocalTime end = timeSlotTablePanel.getSelectedEndTime();
        if (start == null || end == null) {
            statusLabel.setText("Vui lòng chọn khung giờ!");
            return null;
        }
        LocalDateTime startTime = DateTimeUtils.parseTime(start.toString());
        LocalDateTime endTime =  DateTimeUtils.parseTime(end.toString());
        booking.setPitchId(selectedPitch.getId());
        booking.setCustomerId(selectedCustomer.getId());
        booking.setDate(DateTimeUtils.parseDate(DateTimeUtils.getDateFromDate(weekChooser.getDate())));
        booking.setStartTime(startTime);
        booking.setEndTime(endTime);
        booking.setTotalPrice(DateTimeUtils.calculateHoursBetween(startTime, endTime) * selectedPitch.getPricePerHour());
        booking.setNote("");
        System.out.println("getSelectedBooking : " + booking);
        return booking;
    }

    public void loadPitches(List<Pitch> pitches) {
        pitchComboBox.removeAllItems();
        for (Pitch p : pitches) {
            pitchComboBox.addItem(p);
        }
        pitchComboBox.setSelectedIndex(0);
        System.out.println("loadPitches : " + pitchComboBox.getSelectedItem());
    }

    public void loadCustomers(List<Customer> customers) {
        customerComboBox.removeAllItems();
        for (Customer c : customers) {
            customerComboBox.addItem(c);
        }
    }

    private List<LocalDate> getCurrentWeekDates() {
        Date selected = weekChooser.getDate();
        LocalDate selectedDate = selected.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        LocalDate monday = selectedDate;
        while (monday.getDayOfWeek() != DayOfWeek.MONDAY) {
            monday = monday.minusDays(1);
        }
        List<LocalDate> week = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            week.add(monday.plusDays(i));
        }
        return week;
    }

    public void reloadTable(List<Map<String,Object>> data) {
        weekDates = getCurrentWeekDates();
        // List<Map<String,Object>> data = bookingService.getAllBookingsMap()
        // .stream().filter(b->(int) b.get("pitchId") == getselectedPitch().getId()).collect(Collectors.toList());
        timeSlotTablePanel.updateData(weekDates, timeSlots, data);
        statusLabel.setText("Nhấn vào khung giờ để chọn, có thể kéo thả để đăt khung giờ mong muốn");
    }

    public boolean isEmpty(){
        return pitchComboBox.getItemCount() == 0 || customerComboBox.getItemCount() == 0;
    }

    public  Pitch getselectedPitch(){
        return (Pitch) pitchComboBox.getSelectedItem();
    }

    public void setComboBoxAction(ActionListener listener) {
        pitchComboBox.addActionListener(listener);
    }

    public void setCalendarAction(PropertyChangeListener listener) {
        weekChooser.getDateEditor().addPropertyChangeListener(listener);
    }
}