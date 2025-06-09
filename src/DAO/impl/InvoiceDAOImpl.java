package DAO.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import DAO.InvoiceDAO;
import model.Invoice;
//import model.InvoiceItem;

public class InvoiceDAOImpl implements InvoiceDAO {

    @Override
    public Invoice findById(int id) {
        String sql = "SELECT * FROM Invoices WHERE id = ?";
        try (Connection conn = DatabaseConnector.connect("QuanLySanBong");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToInvoice(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Invoice> findAll() {
        List<Invoice> invoices = new ArrayList<>();
        String sql = "SELECT * FROM Invoices";
        try (Connection conn = DatabaseConnector.connect("QuanLySanBong");
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                invoices.add(mapResultSetToInvoice(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return invoices;
    }

    @Override
    public boolean save(Invoice invoice) {
        String sql = "INSERT INTO Invoices (customer_Id, Pitch_Id, discount, total, note) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnector.connect("QuanLySanBong");
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, invoice.getCustomerId());
            stmt.setInt(2, invoice.getPitchId());
            stmt.setDouble(3, invoice.getDiscount());
            stmt.setDouble(4, invoice.getTotal());
            stmt.setString(5, invoice.getNote());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                // Lấy ID được sinh ra
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    invoice.setId(generatedId); // Cập nhật ID cho invoice
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
    public boolean update(Invoice invoice) {
        String sql = "UPDATE Invoices SET customer_id = ?, Pich_id = ?, discount = ?, total = ?, note = ? WHERE id = ?";
        try (Connection conn = DatabaseConnector.connect("QuanLySanBong");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, invoice.getCustomerId());
            stmt.setInt(2, invoice.getPitchId());
            stmt.setDouble(3, invoice.getDiscount());
            stmt.setDouble(4, invoice.getTotal());
            stmt.setString(5, invoice.getNote());
            stmt.setInt(6, invoice.getId());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM Invoices WHERE id = ?";
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
    public List<Invoice> findByCustomer(int customerId) {
        List<Invoice> invoices = new ArrayList<>();
        String sql = "SELECT * FROM Invoices WHERE customer_Id = ?";
        try (Connection conn = DatabaseConnector.connect("QuanLySanBong");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                invoices.add(mapResultSetToInvoice(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return invoices;
    }

    @Override
    public List<Invoice> findByDate(LocalDate date) {
        List<Invoice> invoices = new ArrayList<>();
        String sql = "SELECT * FROM invoices " + //
                        "WHERE CONVERT(date, created_at) = ?";
        try (Connection conn = DatabaseConnector.connect("QuanLySanBong");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, date.toString());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                invoices.add(mapResultSetToInvoice(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return invoices;
    }

    @Override
    public List<Invoice> findByDateRange(LocalDate startDate, LocalDate endDate) {
        List<Invoice> invoices = new ArrayList<>();
        String sql = "SELECT * FROM Invoices WHERE CAST(created_at as date) BETWEEN ? AND ?";
        try (Connection conn = DatabaseConnector.connect("QuanLySanBong");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, startDate.toString());
            stmt.setString(2, endDate.toString());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                invoices.add(mapResultSetToInvoice(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return invoices;
    }

    @Override
    public List<Invoice> findByType(String type) {
        List<Invoice> invoices = new ArrayList<>();
        String sql = "SELECT * FROM Invoices WHERE type = ?";
        try (Connection conn = DatabaseConnector.connect("QuanLySanBong");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, type);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                invoices.add(mapResultSetToInvoice(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return invoices;
    }

    @Override 
    public List<Invoice> findByPitch(int pitchId){
        List<Invoice> invoices = new ArrayList<>();
        String sql = "SELECT * FROM Invoices WHERE Pitch_Id = ?";
        try (Connection conn = DatabaseConnector.connect("QuanLySanBong");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, pitchId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                invoices.add(mapResultSetToInvoice(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return invoices;
    }

    // Sửa mapResultSetToInvoice (loại bỏ created_at)
    private Invoice mapResultSetToInvoice(ResultSet rs) throws SQLException {
        Invoice invoice = new Invoice(
                rs.getInt("id"),
                rs.getInt("customer_Id"),
                rs.getInt("Pitch_Id"),
                rs.getDouble("discount"),
                rs.getString("note")
                // Không còn created_at
        );
        return invoice;
    }

    @Override
    public boolean addInvoice(Invoice invoice) {
        String sql = "INSERT INTO invoices (customer_id, pitch_id, discount, total, note, created_at) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnector.connect("QuanLySanBong");
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, invoice.getCustomerId());
            stmt.setInt(2, invoice.getPitchId());
            stmt.setDouble(3, invoice.getDiscount());
            stmt.setDouble(4, invoice.getTotal());
            stmt.setString(5, invoice.getNote());

            // Nếu createdAt null thì dùng thời điểm hiện tại
            if (invoice.getCreatedAt() != null) {
                stmt.setTimestamp(6, Timestamp.valueOf(invoice.getCreatedAt()));
            } else {
                stmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            }

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        invoice.setId(rs.getInt(1)); // Lưu lại ID hóa đơn mới tạo
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
