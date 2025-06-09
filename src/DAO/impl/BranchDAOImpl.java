package DAO.impl;

import DAO.BranchDAO;
import model.Branch;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BranchDAOImpl implements BranchDAO {

    @Override
    public Branch findById(int id) {
        String sql = "SELECT * FROM branches WHERE id = ?";
        try (Connection conn = DatabaseConnector.connect("QuanLySanBong");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToBranch(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Branch> findAll() {
        List<Branch> branches = new ArrayList<>();
        String sql = "SELECT * FROM branches";
        try (Connection conn = DatabaseConnector.connect("QuanLySanBong");
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                branches.add(mapResultSetToBranch(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return branches;
    }

    @Override
    public boolean save(Branch branch) {
        String sql = "INSERT INTO branches (name, address, phone,active) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnector.connect("QuanLySanBong");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, branch.getName());
            stmt.setString(2, branch.getAddress());
            stmt.setString(3, branch.getPhone());
            //stmt.setString(4, branch.getManagerName());
            stmt.setBoolean(4, branch.isActive());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Branch branch) {
        String sql = "UPDATE branches SET name = ?, address = ?, phone = ?, active = ? WHERE id = ?";
        try (Connection conn = DatabaseConnector.connect("QuanLySanBong");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, branch.getName());
            stmt.setString(2, branch.getAddress());
            stmt.setString(3, branch.getPhone());
            //stmt.setString(4, branch.getManagerName());
            stmt.setBoolean(4, branch.isActive());
            stmt.setInt(5, branch.getId());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM branches WHERE id = ?";
        try (Connection conn = DatabaseConnector.connect("QuanLySanBong");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Branch> findActive() {
        List<Branch> branches = new ArrayList<>();
        String sql = "SELECT * FROM branches WHERE active = true";
        try (Connection conn = DatabaseConnector.connect("QuanLySanBong");
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                branches.add(mapResultSetToBranch(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return branches;
    }

    @Override
    public Branch findByPitch(int PitchId) {
        String sql = "SELECT b.* FROM branches b JOIN pitches p ON b.id = p.branch_id WHERE p.id = ?";
        try (Connection conn = DatabaseConnector.connect("QuanLySanBong");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, PitchId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToBranch(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Branch mapResultSetToBranch(ResultSet rs) throws SQLException {
        Branch branch = new Branch(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("address"),
                rs.getString("phone")              
        );
        branch.setActive(rs.getBoolean("active"));
        return branch;
    }
}
