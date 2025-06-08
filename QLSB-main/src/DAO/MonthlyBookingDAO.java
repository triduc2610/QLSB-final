package DAO;
import model.MonthlyBooking;
//import java.time.LocalDate;
import java.util.List;

public interface MonthlyBookingDAO extends GenericDAO<MonthlyBooking> {
    List<MonthlyBooking> findByCustomer(int customerId);
    List<MonthlyBooking> findByPitch(int pitchId);
    List<MonthlyBooking> findByStatus(String status);
    List<MonthlyBooking> findByMonth(int month, int year);
}