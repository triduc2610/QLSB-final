package controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Booking;
import model.Customer;
import model.MonthlyBooking;
import service.PitchService;
import service.BookingService;
import service.MonthlyBookingService;
import service.CustomerService;
import utils.DateTimeUtils;
import view.BookingListView;
import view.BookingView;


public class BookingController {
    private BookingService bookingService;
    private BookingView bookingView;
    private BookingListView bookingListView;
    private MonthlyBookingService monthlyBookingService;
    private CustomerService customerService = new CustomerService();
    private PitchService pitchService = new PitchService();
    public BookingController(BookingView bookingView,BookingListView bookingListView) {
        this.bookingService = new BookingService();
        this.bookingListView = bookingListView;
        this.bookingView = bookingView;
        this.monthlyBookingService = new MonthlyBookingService();
    }
    public boolean processNewBooking() {
        //LocalDateTime now = LocalDateTime.now();
        Booking bookingData = bookingView.getBookingData();
        if (bookingView.getHoursBetween() < 0) {
            bookingView.displayError("thoi gian dat khong hop le");
            return false;
        } else if (bookingView.getMinutesBetween() < 30) {
            bookingView.displayError("Thoi gian toi thieu dat san la 30 phut!");
            return false;
        } else if (bookingData.getStartTime().toLocalTime().isBefore(LocalTime.of(5, 0)) ||
                bookingData.getEndTime().toLocalTime().isAfter(LocalTime.of(23, 0))) {
            bookingView.displayError("San chi hoat dong trong khoang thoi gian tu 5:00 den 23:00");
            return false;
        }

        boolean result;
        if (bookingView.isPeriodic()) {
            result = processMonthlyBooking(bookingData);
        } else {
            result = processRegularBooking(bookingData);
        }

        if (result) {
        Customer customer = bookingView.getSelectedCustomer();
        customer.addToTotalSpent(bookingData.getTotalPrice());
            try {
            customerService.updateCustomer(customer);
            } catch (Exception e) {
            System.out.println(e.getMessage());
            }
        }
        return result;
    }

    private boolean processRegularBooking(Booking bookingData) {
        System.out.println("bookingData: " + bookingData);
        boolean isAvailable = bookingService.checkConflict(bookingData.getPitchId(), bookingData.getDate(), bookingData.getStartTime(), bookingData.getEndTime());
        if (isAvailable) {
            try {
                bookingService.addBooking(bookingData);
                
                return true;
            } catch (Exception e) {
            bookingView.displayError("Error processing booking: " + e.getMessage());
                return false;
            }
        } else {
            bookingView.displayError("unavailable pitch!");
            return false;
        }
    }

    private boolean processMonthlyBooking(Booking bookingdata) {
        if (bookingView.getDaysBetween() < 7) {
            bookingView.displayError("thoi gian dat theo dinh ky phai tren 1 tuan");
            return false;
        } else if (bookingView.getStarDate().isAfter(bookingView.getEndDate())) {
            bookingView.displayError("ngay bat dau phai truoc ngay ket thuc");
            return false;
        } else if (bookingView.getStarDate().toLocalDate().isBefore(LocalDate.now().plusDays(1))) {
            bookingView.displayError("ngay bat dau phai sau ngay hien tai");
            return false;
        }
        MonthlyBooking monthlyBookingData = bookingView.getMonthlyBookingData();
        boolean isAvailable = true;

        List<Map<String, Object>> data = new ArrayList<>();
        List<LocalDate> dates = DateTimeUtils.getMatchingDays(monthlyBookingData.getStartDate(),
                            monthlyBookingData.getEndDate(), monthlyBookingData.getDaysOfWeek());
        
        for (LocalDate date : dates) {
            if (!bookingService.checkConflict(bookingdata.getPitchId(), DateTimeUtils.parseDate(date.toString()),
                    bookingdata.getStartTime(), bookingdata.getEndTime(), data)) {
               isAvailable = false;
            }
            Map<String, Object> obj = new HashMap<>();
            obj.put("pitchId", bookingdata.getPitchId());
            obj.put("date", date.toString());
            obj.put("startTime", bookingdata.getStartTime());
            obj.put("endTime", bookingdata.getEndTime());
            data.add(obj);
        }
        if (isAvailable) {
        try {
            bookingdata.setDate(null);
            bookingdata.setTotalPrice(bookingdata.getTotalPrice() - monthlyBookingData.getDiscount());
            bookingService.addBooking(bookingdata);
                bookingdata = bookingService.getAllBookings().get(bookingService.getAllBookings().size() - 1);
            monthlyBookingData.setId(bookingdata.getId());
                System.out.println("monthlyBookingData: " + monthlyBookingData);
            if (monthlyBookingService.addMonthlyBooking(monthlyBookingData)) {
                       
                    return true;
            } else {
                bookingView.displayError("loi dat monthy booking");
                    return false;
            }
        } catch (Exception e) {
            bookingView.displayError("Error processing booking: " + e.getMessage());
                return false;
            }
        } else {
            bookingView.displayError("unavailable pitch!");
            return false;
        }
    }

    public void loadBookingData() {
        bookingView.loadpitches(pitchService.getAllActivePitchs());
        bookingView.loadcustomers(customerService.getAllCustomers());
        bookingView.setTotalPrice();
    }

    public void loadBookingListData(){
        bookingListView.loadBookingList(bookingService.getAllBookings());
    }


    public MonthlyBooking getMonnthlyMatch(){
        return monthlyBookingService.getMonthlyBookingById(bookingListView.getSelectedBookingId());
    }
    public Booking getBookingMatch(){
        return bookingService.getBookingById(bookingListView.getSelectedBookingId());
    }

    public void setInfoTextField(){
        MonthlyBooking monthlyMatch = getMonnthlyMatch();
                String monthlyInfo = String.format("""
                        Thông tin booking theo tháng :

                        Id: %s

                        Ngày bắt đầu: %s

                        Ngày kết thúc: %s

                        Các thứ trong tuần: %s

                        Discount: %s 

                        """,
                        monthlyMatch.getId(),
                        monthlyMatch.getStartDate().toLocalDate(),
                        monthlyMatch.getEndDate().toLocalDate(),
                        monthlyMatch.getDaysOfWeek(),
                        monthlyMatch.getDiscount()
                );
                bookingListView.setMonthlyBookinginfo(monthlyInfo);//alredy set the text filed visible in this
    }

    public void displayEditData(){
        Booking selectedBooking = bookingListView.getSelectedBooking();
        MonthlyBooking monthlyMatch;//monthlyBookingService.getMonthlyBookingById(selectedBooking.getId());
        bookingView.setData(selectedBooking);
        if(selectedBooking.isPeriodic()){
            //setpitch,customer,starttime,endtime,date
        monthlyMatch = getMonnthlyMatch();
        bookingView.setStartDate(monthlyMatch.getStartDate());
        bookingView.setEndDate(monthlyMatch.getEndDate());
        bookingView.setDiscount(monthlyMatch.getDiscount());
        bookingView.setDayOfWeek(monthlyMatch.getDaysOfWeek());
        }
        if(selectedBooking.isPeriodic()) {
            bookingView.setPeriodic(true);
        } else {
            bookingView.setPeriodic(false);
        }
    }

    public boolean processEditBooking(){
        Booking bookingMatch = getBookingMatch();
        if(processNewBooking()){
            bookingService.deleteBooking(bookingMatch.getId());    //da co delete cascade trong sql r 
            bookingView.setButtonForEdit(false);
            loadBookingListData();
            return true;
        }
        bookingView.displayError("Error editing booking: Booking not found or invalid data");
        return false;
    }

    public void processDeleteBooking(){
        Booking bookingMatch = getBookingMatch();
        if (bookingMatch == null) {
            bookingView.displayError("Error deleting booking: Booking not found");
            return;
        }
        try {
                bookingService.deleteBooking(bookingMatch.getId());
                bookingView.displayError("Xoa thanh cong");
                loadBookingListData();
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
        
            try {
            Customer customer = customerService.getCustomerById(bookingMatch.getCustomerId());
            customer.addToTotalSpent( - (bookingMatch.getTotalPrice()));
            customerService.updateCustomer(customer);
            } catch (Exception e) {
            System.out.println(e.getMessage());
            }
    }
}