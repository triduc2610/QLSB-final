package DAO;

import model.Booking;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface BookingDAO extends GenericDAO<Booking> {
    List<Booking> findByPitch(int pitchId);
    List<Booking> findByCustomer(int customerId);
    List<Booking> findByDate(LocalDate date);
    List<Booking> findByDateRange(LocalDate startDate, LocalDate endDate);
    List<Booking> findByPitchAndDateRange(int pitchId, LocalDateTime start, LocalDateTime end);
    //boolean checkConflict(int pitch_Id,LocalDateTime date ,LocalDateTime startTime,LocalDateTime endTime);
}
