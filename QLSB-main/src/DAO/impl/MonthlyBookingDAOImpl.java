package DAO.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DAO.MonthlyBookingDAO;
import model.MonthlyBooking;
import utils.*;
public class MonthlyBookingDAOImpl implements MonthlyBookingDAO {

    @Override
    public MonthlyBooking findById(int id) {
        String sql = "SELECT * FROM Monthly_Bookings WHERE id = ?";
        try (Connection conn = DatabaseConnector.connect("QuanLySanBong");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToMonthlyBooking(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean save(MonthlyBooking monthlyBooking) {
        String sql = "INSERT INTO Monthly_Bookings (id,start_Date, end_Date ,days_Of_Week, discount) VALUES (?,?, ?, ?, ?)";
        try (Connection conn = DatabaseConnector.connect("QuanLySanBong");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            //stmt.setInt(1, monthlyBooking.getCustomerId());
            //stmt.setInt(2, monthlyBooking.getPitchId());
            stmt.setInt(1, monthlyBooking.getId());
            stmt.setString(2, DateTimeUtils.formatDate(monthlyBooking.getStartDate()));
            stmt.setString(3, DateTimeUtils.formatDate(monthlyBooking.getEndDate()));
            stmt.setString(4, String.join(",", monthlyBooking.getDaysOfWeek()));
            stmt.setDouble(5, monthlyBooking.getDiscount());
            //stmt.setString(13, monthlyBooking.getStatus());
            //stmt.setString(14, monthlyBooking.getNote());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(MonthlyBooking monthlyBooking) {
        String sql = "UPDATE Monthly_Bookings SET  startDate = ?, endDate = ?,daysOfWeek = ?, discount = ? WHERE id = ?";
        try (Connection conn = DatabaseConnector.connect("QuanLySanBong");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            //stmt.setInt(1, monthlyBooking.getCustomerId());
            //stmt.setInt(2, monthlyBooking.getPitchId());
            stmt.setString(1, monthlyBooking.getStartDate().toString());
            stmt.setString(2, monthlyBooking.getEndDate().toString());
            //stmt.setString(5, monthlyBooking.getStartTime().toString());
            //stmt.setString(6, monthlyBooking.getEndTime().toString());
            stmt.setString(3, String.join(",", monthlyBooking.getDaysOfWeek()));
            //stmt.setInt(8, monthlyBooking.getSessionsPerMonth());
            //stmt.setDouble(9, monthlyBooking.getPricePerSession());
            //stmt.setDouble(10, monthlyBooking.getTotalAmount());
            //stmt.setDouble(11, monthlyBooking.getDiscount());
            //stmt.setDouble(12, monthlyBooking.getFinalAmount());
            //stmt.setString(13, monthlyBooking.getStatus());
            //stmt.setString(14, monthlyBooking.getNote());
            stmt.setInt(4, monthlyBooking.getId());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM MonthlyBookings WHERE id = ?";
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

    @Override
    public List<MonthlyBooking> findAll() {
        List<MonthlyBooking> monthlyBookings = new ArrayList<>();
        String sql = "SELECT * FROM Monthly_Bookings";
        
        try (Connection conn = DatabaseConnector.connect("QuanLySanBong");
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                monthlyBookings.add(mapResultSetToMonthlyBooking(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return monthlyBookings;
    }

    @Override
    public List<MonthlyBooking> findByCustomer(int customerId) {
        List<MonthlyBooking> monthlyBookings = new ArrayList<>();
        String sql = "SELECT * FROM MonthlyBookings WHERE customerId = ?";
        try (Connection conn = DatabaseConnector.connect("QuanLySanBong");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                monthlyBookings.add(mapResultSetToMonthlyBooking(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return monthlyBookings;
    }

    @Override
    public List<MonthlyBooking> findByPitch(int pitchId) {
        List<MonthlyBooking> monthlyBookings = new ArrayList<>();
        String sql = "SELECT * FROM MonthlyBookings WHERE pitchId = ?";
        try (Connection conn = DatabaseConnector.connect("QuanLySanBong");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, pitchId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                monthlyBookings.add(mapResultSetToMonthlyBooking(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return monthlyBookings;
    }

    @Override
    public List<MonthlyBooking> findByStatus(String status) {
        List<MonthlyBooking> monthlyBookings = new ArrayList<>();
        String sql = "SELECT * FROM MonthlyBookings WHERE status = ?";
        try (Connection conn = DatabaseConnector.connect("QuanLySanBong");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                monthlyBookings.add(mapResultSetToMonthlyBooking(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return monthlyBookings;
    }

    @Override
    public List<MonthlyBooking> findByMonth(int month, int year) {
        List<MonthlyBooking> monthlyBookings = new ArrayList<>();
        String sql = "SELECT * FROM MonthlyBookings WHERE MONTH(startDate) = ? AND YEAR(startDate) = ?";
        try (Connection conn = DatabaseConnector.connect("QuanLySanBong");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, month);
            stmt.setInt(2, year);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                monthlyBookings.add(mapResultSetToMonthlyBooking(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return monthlyBookings;
    }


    private MonthlyBooking mapResultSetToMonthlyBooking(ResultSet rs) throws SQLException {
        List<String> daysOfWeek = List.of(rs.getString("days_Of_Week").split(","));
        return new MonthlyBooking(
                rs.getInt("id"),
                DateTimeUtils.parseDate(rs.getString("start_Date")),
                DateTimeUtils.parseDate(rs.getString("end_date")),
                daysOfWeek,
                rs.getDouble("discount")
        );
    }
}
