package DAO.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import utils.DateTimeUtils;
import DAO.BookingDAO;
import model.Booking;

public class BookingDAOImpl implements BookingDAO {
    // Implement the methods defined in BookingDAO interface
    @Override
    public List<Booking> findByPitch(int pitchId) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE pitch_Id = ?";
        try (Connection conn = DatabaseConnector.connect("QuanLySanBong");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, pitchId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                bookings.add(mapResultSetToBooking(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    @Override
    public List<Booking> findByCustomer(int customerId) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE id = ?";
        try (Connection conn = DatabaseConnector.connect("QuanLySanBong");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                bookings.add(mapResultSetToBooking(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    @Override
    public List<Booking> findByDate(LocalDate date) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE date = ?";
        try (Connection conn = DatabaseConnector.connect("QuanLySanBong");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, date.toString());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                bookings.add(mapResultSetToBooking(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    @Override
    public List<Booking> findByDateRange(LocalDate startDate, LocalDate endDate) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE DATE(start_Time) BETWEEN ? AND ?";
        try (Connection conn = DatabaseConnector.connect("QuanLySanBong");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, startDate.toString());
            stmt.setString(2, endDate.toString());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                bookings.add(mapResultSetToBooking(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    @Override
    public List<Booking> findByPitchAndDateRange(int pitchId, LocalDateTime start, LocalDateTime end) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE pitchId = ? AND start_Time >= ? AND end_Time <= ?";
        try (Connection conn = DatabaseConnector.connect("QuanLySanBong");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, pitchId);
            stmt.setString(2, start.toString());
            stmt.setString(3, end.toString());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                bookings.add(mapResultSetToBooking(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    
    // @Override
    // public boolean checkConflict(int pitchId, LocalDateTime date, LocalDateTime startTime, LocalDateTime endTime) {
    //     // Only check for conflicts in the bookings table (ignore monthly_bookings)
    //     // Conflict if: same pitch, same date, and time ranges overlap

    //     // The original SQL and logic are incorrect for time overlap.
    //     // Correct logic: (start1 < end2) AND (start2 < end1)
    //     // So: (existing.start_time < newEnd) AND (newStart < existing.end_time)
    //     // String sql =
    //     //     "SELECT id FROM bookings " +
    //     //     "WHERE pitch_id = ? AND date = ? " +
    //     //     //"AND ((start_time > ? AND end_time > ?) OR (start_time < ? AND end_time < ?)) OR (start_time < ? AND end_time > ?) OR (start_time > ? AND end_time < ?)";
    //     //     "AND (start_time < ? AND end_time > ?)";
    //     // try (Connection conn = DatabaseConnector.connect("QuanLySanBong");
    //     //      PreparedStatement stmt = conn.prepareStatement(sql)) {
    //     //     stmt.setInt(1, pitchId);
    //     //     stmt.setString(2, DateTimeUtils.formatDate(date));
    //     //     stmt.setString(3, DateTimeUtils.formatTime(endTime));
    //     //     stmt.setString(4, DateTimeUtils.formatTime(startTime));
    //     //     ResultSet rs = stmt.executeQuery();
    //     //     if (rs.next()) {
    //     //         return true;
    //     //     }
    //     // } catch (SQLException e) {
    //     //     e.printStackTrace();
    //     // }
    //     // return false;
    //     return true;
    // }


    
    @Override
    public Booking findById(int id) {
        String sql = "SELECT * FROM bookings WHERE id = ?";
        try (Connection conn = DatabaseConnector.connect("QuanLySanBong");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToBooking(rs);
            }
        } catch (SQLException e) {
            System.out.println("fingbyid err");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Booking> findAll() {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings";
        try (Connection conn = DatabaseConnector.connect("QuanLySanBong");
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                bookings.add(mapResultSetToBooking(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    @Override
    public boolean save(Booking booking) {
        String sql = "INSERT INTO bookings (pitch_Id, customer_Id, date, start_Time, end_Time, total_Price, is_Periodic,note) VALUES (?, ?, ?,?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnector.connect("QuanLySanBong");
             PreparedStatement stmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, booking.getPitchId());
            stmt.setInt(2, booking.getCustomerId());
            stmt.setString(3, DateTimeUtils.formatDate(booking.getDate()));
            stmt.setString(4, DateTimeUtils.formatTime(booking.getStartTime()));
            stmt.setString(5, DateTimeUtils.formatTime(booking.getEndTime()));
            stmt.setDouble(6, booking.getTotalPrice());
            stmt.setBoolean(7, booking.isPeriodic());
            stmt.setString(8, booking.getNote());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
            // Lấy ID được sinh ra
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int generatedId = rs.getInt(1);
                booking.setId(generatedId); // Cập nhật ID cho booking
            }
            return true;
            }
        return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Booking booking) {
        String sql = "UPDATE bookings SET pitchId = ?, customerId = ?, date = ?,start_Time = ?, end_Time = ?, totalPrice = ?, isPeriodic = ? WHERE id = ?";
        try (Connection conn = DatabaseConnector.connect("QuanLySanBong");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, booking.getPitchId());
            stmt.setInt(2, booking.getCustomerId());
            stmt.setString(3, DateTimeUtils.formatDate(booking.getDate()));
            stmt.setString(4, DateTimeUtils.formatTime(booking.getStartTime()));
            stmt.setString(5, DateTimeUtils.formatTime(booking.getEndTime()));
            stmt.setDouble(6, booking.getTotalPrice());
            stmt.setBoolean(7, booking.isPeriodic());
            stmt.setInt(8, booking.getId());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM bookings WHERE id = ?";
        try (Connection conn = DatabaseConnector.connect("QuanLySanBong");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Booking mapResultSetToBooking(ResultSet rs) throws SQLException {
        return new Booking(
                rs.getInt("id"),
                rs.getInt("pitch_Id"),
                rs.getInt("customer_Id"),
                DateTimeUtils.parseDate(rs.getString("date")),
                DateTimeUtils.parseTime(rs.getString("start_time")),
                DateTimeUtils.parseTime(rs.getString("end_time")),
                rs.getDouble("total_Price"),
                rs.getBoolean("is_Periodic"),
                rs.getString("note")
        );
    }
}
