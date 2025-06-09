package DAO.impl;



import DAO.PitchDAO;
import model.Pitch;
//import com.footballmanager.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PitchDAOImpl implements PitchDAO {
    private Connection connection;

    public PitchDAOImpl() {
        this.connection = DatabaseConnector.connect("QuanLySanBong");// Assuming you have a DatabaseConnection class to manage DB connections
    }

    @Override
    public Pitch findById(int id) {
        Pitch pitch = null;
        String sql = "SELECT * FROM pitches WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                pitch = new Pitch(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("type"),
                    rs.getDouble("price_per_hour"),
                    rs.getString("description"),
                    rs.getInt("branch_id"),
                    rs.getBoolean("active") // Thêm active vào constructor
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pitch;
    }

    @Override
    public List<Pitch> findAll() {
        List<Pitch> pitches = new ArrayList<>();
        String sql = "SELECT * FROM pitches";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Pitch pitch = new Pitch(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("type"),
                    rs.getDouble("price_per_hour"),
                    rs.getString("description"),
                    rs.getInt("branch_id"),
                    rs.getBoolean("active") // Thêm active vào constructor
                );
                pitches.add(pitch);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pitches;
    }

    @Override
    public boolean save(Pitch pitch) {
        String sql = "INSERT INTO pitches (name, type, price_per_hour, description, branch_id, active) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, pitch.getName());
            stmt.setString(2, pitch.getType());
            stmt.setDouble(3, pitch.getPricePerHour());
            stmt.setString(4, pitch.getDescription());
            stmt.setInt(5, pitch.getBranchId());
            stmt.setBoolean(6, pitch.isActive());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Pitch pitch) {
        String sql = "UPDATE pitches SET name = ?, type = ?, price_per_hour = ?, " +
                     "description = ?, branch_id = ?, active = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, pitch.getName());
            stmt.setString(2, pitch.getType());
            stmt.setDouble(3, pitch.getPricePerHour());
            stmt.setString(4, pitch.getDescription());
            stmt.setInt(5, pitch.getBranchId());
            stmt.setBoolean(6, pitch.isActive());
            stmt.setInt(7, pitch.getId());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM pitches WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Pitch> findByBranch(int branchId) {
        List<Pitch> pitches = new ArrayList<>();
        String sql = "SELECT * FROM pitches WHERE branch_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, branchId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Pitch pitch = new Pitch(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("type"),
                    rs.getDouble("price_per_hour"),
                    rs.getString("description"),
                    rs.getInt("branch_id"),
                    rs.getBoolean("active") // Thêm active vào constructor
                );
                pitches.add(pitch);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pitches;
    }

    @Override
    public List<Pitch> findByType(String type) {
        List<Pitch> pitches = new ArrayList<>();
        String sql = "SELECT * FROM pitches WHERE type = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, type);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Pitch pitch = new Pitch(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("type"),
                    rs.getDouble("price_per_hour"),
                    rs.getString("description"),
                    rs.getInt("branch_id"),
                    rs.getBoolean("active") // Thêm active vào constructor
                );
                pitches.add(pitch);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pitches;
    }

    /*@Override
    public List<Pitch> findActivePitchs() {
        List<Pitch> pitches = new ArrayList<>();
        String sql = "SELECT * FROM pitches WHERE active = true";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Pitch pitch = new Pitch(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("type"),
                    rs.getDouble("price_per_hour"),
                    rs.getString("description"),
                    rs.getInt("branch_id")
                );
                pitch.setActive(rs.getBoolean("active"));
                pitches.add(pitch);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pitches;
    }*/

    @Override
    public List<Pitch> findActivePitchs(int branchId) {
        List<Pitch> pitches = new ArrayList<>();
        String sql = "SELECT * FROM pitches WHERE active = true AND branch_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, branchId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Pitch pitch = new Pitch(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("type"),
                    rs.getDouble("price_per_hour"),
                    rs.getString("description"),
                    rs.getInt("branch_id"),
                    rs.getBoolean("active") // Thêm active vào constructor
                );
                pitches.add(pitch);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pitches;
    }

    @Override
    public List<Pitch> findAllActivePitchs() {
        List<Pitch> pitches = new ArrayList<>();
        String sql = "SELECT * FROM pitches WHERE active = 1";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Pitch pitch = new Pitch(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("type"),
                    rs.getDouble("price_per_hour"),
                    rs.getString("description"),
                    rs.getInt("branch_id"),
                    rs.getBoolean("active") // Thêm active vào constructor
                );
                pitches.add(pitch);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pitches;
}
    /*private Pitch mapResultSetToPitch(ResultSet rs) throws SQLException {
        Pitch pitch = new Pitch(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("type"),
                rs.getDouble("price_per_hour"),
                rs.getString("description"),
                rs.getInt("branch_id")
        );
        pitch.setActive(rs.getBoolean("active"));
        return pitch;
    }*/
}