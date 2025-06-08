package service;

import DAO.BookingDAO;
import DAO.impl.BookingDAOImpl;
import model.Booking;
import utils.DateTimeUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BookingService {
    private BookingDAO bookingDAO;
    private TransactionService transactionService = new TransactionService();
    private MonthlyBookingService monthlyBookingService;
    public BookingService() {
        this.bookingDAO = new BookingDAOImpl();
        this.monthlyBookingService = new MonthlyBookingService();
    }
    
    public Booking getBookingById(int id) {
        return bookingDAO.findById(id);
    }
    
    public List<Booking> getAllBookings() {
        return bookingDAO.findAll();
    }
    
    public List<Booking> getBookingsByPitch(int pitchId) {
        return bookingDAO.findByPitch(pitchId);
    }
    
    public List<Booking> getBookingsByCustomer(int customerId) {
        return bookingDAO.findByCustomer(customerId);
    }
    
    public List<Booking> getBookingsByDate(LocalDate date) {
        return bookingDAO.findByDate(date);
    }
    
    public List<Booking> getBookingsByDateRange(LocalDate startDate, LocalDate endDate) {
        return bookingDAO.findByDateRange(startDate, endDate);
    }
    
    public List<Booking> getBookingsByPitchAndDateRange(int PitchId, LocalDateTime startDate, LocalDateTime endDate) {
        return bookingDAO.findByPitchAndDateRange(PitchId, startDate, endDate);
    }

    //return true neu ko co conflict
    public boolean checkConflict(int pitchId, LocalDateTime date, LocalDateTime startTime, LocalDateTime endTime) {
        // Only check bookings with the same pitch and same date
        List<Map<String, Object>> filterDateAndPitchId = getAllBookingsMap().stream()
            .filter(booking -> (int) booking.get("pitchId") == pitchId
                && booking.get("date").equals(DateTimeUtils.formatDate(date)))
            .collect(Collectors.toList());

        // If no bookings found, no conflict
        if (filterDateAndPitchId.isEmpty()) {
            return true;
        }

        // Check for time overlap
        for (Map<String, Object> booking : filterDateAndPitchId) {
            LocalDateTime existingStart = (LocalDateTime) booking.get("startTime");
            LocalDateTime existingEnd = (LocalDateTime) booking.get("endTime");
            // Only compare time part
            if (existingStart.toLocalTime().isBefore(endTime.toLocalTime()) &&
                startTime.toLocalTime().isBefore(existingEnd.toLocalTime())) {
                return false; // Conflict found
            }
        }
        return true; // No conflict
    }
    //return true neu ko co conflict
    public boolean checkConflict(int pitchId, LocalDateTime date, LocalDateTime startTime, LocalDateTime endTime,List<Map<String,Object>> data) {
        // Only check bookings with the same pitch and same date
        //add data vua nhap vao voi data cua databases
        List<Map<String, Object>> metadata = getAllBookingsMap();
        for(Map<String,Object> bookingmap : data){
            metadata.add(bookingmap);
        }

        List<Map<String, Object>> filterDateAndPitchId = metadata.stream()
            .filter(booking -> (int) booking.get("pitchId") == pitchId
                && booking.get("date").equals(DateTimeUtils.formatDate(date)))
            .collect(Collectors.toList());
        // If no bookings found, no conflict
        if (filterDateAndPitchId.isEmpty()) {
            return true;
        }

        // Check for time overlap
        for (Map<String, Object> booking : filterDateAndPitchId) {
            LocalDateTime existingStart = (LocalDateTime) booking.get("startTime");
            LocalDateTime existingEnd = (LocalDateTime) booking.get("endTime");
            // Only compare time part
            if (existingStart.toLocalTime().isBefore(endTime.toLocalTime()) &&
                startTime.toLocalTime().isBefore(existingEnd.toLocalTime())) {
                return false; // Conflict found
            }
        }
        return true; // No conflict
    }
    
    public boolean addBooking(Booking booking) {
            boolean bookingSaved = bookingDAO.save(booking);

        if (bookingSaved) {
            // Bây giờ booking có ID hợp lệ
            //System.out.println("Booking saved with ID: " + booking.getId());
            
            // Tạo transaction bằng cách gọi TransactionService
            boolean transactionSaved = transactionService.addTransaction(booking);
            
            if (!transactionSaved) {
            System.out.println("Failed to save transaction for booking ID: " + booking.getId());
            }
            return transactionSaved;
        }
        return false;
    }
    
    public boolean updateBooking(Booking booking) {
        if (checkConflict(booking.getPitchId(),booking.getDate(),booking.getStartTime(), booking.getEndTime())) {
            return false;
        }
        return bookingDAO.update(booking);
    }
    
    public boolean deleteBooking(int id) {
        return  transactionService.deleteTransaction(id, "booking")&& bookingDAO.delete(id);
    }

    public List<Map<String, Object>> getAllBookingsMap() {
        List<Map<String, Object>> result = new ArrayList<>();
        Map<String,Integer> mdata = monthlyBookingService.getAllDateMonthlyBoookings();
        List<Booking> bookings = getAllBookings();
        for(Booking booking : bookings){
            if(booking.getDate() == null){
                for(Map.Entry<String, Integer> entry : mdata.entrySet()) {
                    if(booking.getId() == entry.getValue()){
                Map<String, Object> obj = new HashMap<>();
                obj.put("id", booking.getId());
                obj.put("pitchId", booking.getPitchId());
                obj.put("date",entry.getKey());     
                obj.put("startTime", booking.getStartTime());
                obj.put("endTime", booking.getEndTime());
                result.add(obj);}
                }
            }
            else {
                Map<String, Object> obj = new HashMap<>();
                obj.put("id", booking.getId());
                obj.put("pitchId", booking.getPitchId());
                obj.put("date", DateTimeUtils.formatDate(booking.getDate()));
                obj.put("startTime", booking.getStartTime());
                obj.put("endTime", booking.getEndTime());
                result.add(obj);
            }
    }
    return result;
    }
}
