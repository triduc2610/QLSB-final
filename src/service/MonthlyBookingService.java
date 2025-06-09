package service;

// Service cho Quản lý đơn tháng

import DAO.MonthlyBookingDAO;
import DAO.impl.MonthlyBookingDAOImpl;
import model.MonthlyBooking;
import utils.DateTimeUtils;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MonthlyBookingService {
    private MonthlyBookingDAO monthlyBookingDAO;
    
    public MonthlyBookingService() {
        this.monthlyBookingDAO = new MonthlyBookingDAOImpl();
    }
    
    public MonthlyBooking getMonthlyBookingById(int id) {
        return monthlyBookingDAO.findById(id);
    }
    
    public List<MonthlyBooking> getAllMonthlyBookings() {
        return monthlyBookingDAO.findAll();
    }
    
    public List<MonthlyBooking> getMonthlyBookingsByCustomer(int customerId) {
        return monthlyBookingDAO.findByCustomer(customerId);
    }
    
    public List<MonthlyBooking> getMonthlyBookingsByPitch(int pitchId) {
        return monthlyBookingDAO.findByPitch(pitchId);
    }
    
    public List<MonthlyBooking> getMonthlyBookingsByStatus(String status) {
        return monthlyBookingDAO.findByStatus(status);
    }
    
    public List<MonthlyBooking> getMonthlyBookingsByMonth(int month, int year) {
        return monthlyBookingDAO.findByMonth(month, year);
    }
    
    public boolean addMonthlyBooking(MonthlyBooking monthlyBooking) {
        boolean saved = monthlyBookingDAO.save(monthlyBooking);
        return saved;
    }
    
    public boolean deleteMonthlyBooking(int id) {
        // Có thể cần xóa các booking liên quan
        return monthlyBookingDAO.delete(id);
    }
    
    // Phương thức tạo rs qua các đơn tháng
    public Map<String,Integer> getAllDateMonthlyBoookings(){
        Map<String,Integer> data = new HashMap<>();
        List<MonthlyBooking> monthlyBookings = getAllMonthlyBookings();
        for(MonthlyBooking monthlyBooking : monthlyBookings){
            for(LocalDate day : DateTimeUtils.getMatchingDays(monthlyBooking.getStartDate(), monthlyBooking.getEndDate(), monthlyBooking.getDaysOfWeek())){
                data.put(day.toString(), monthlyBooking.getId());
            }      
        }
        return data;
    }   
}

