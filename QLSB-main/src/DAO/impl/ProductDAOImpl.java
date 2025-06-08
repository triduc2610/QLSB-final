package DAO.impl;
import DAO.ProductDAO;
import model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {

    @Override
    public Product findById(int id) {
        String sql = "SELECT * FROM products WHERE id = ?";
        try (Connection conn = DatabaseConnector.connect("QuanLySanBong");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToProduct(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";
        try (Connection conn = DatabaseConnector.connect("QuanLySanBong");
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                products.add(mapResultSetToProduct(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public boolean save(Product product) {
        String sql = "INSERT INTO products (name, category, sell_price, current_stock, min_stock_level, unit, description) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnector.connect("QuanLySanBong");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getCategory());
            //stmt.setDouble(3, product.getBuyPrice());
            stmt.setFloat(3, product.getSellPrice());
            stmt.setInt(4, product.getCurrentStock());
            stmt.setInt(5, product.getMinStockLevel());
            stmt.setString(6, product.getUnit());
            stmt.setString(7, product.getDescription());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Product product) {
        String sql = "UPDATE products SET name = ?, category = ?, sell_price = ?, current_stock = ?, min_stock_level = ?, unit = ?, description = ? WHERE id = ?";
        try (Connection conn = DatabaseConnector.connect("QuanLySanBong");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getCategory());
            //stmt.setDouble(3, product.getBuyPrice());
            stmt.setFloat(3, product.getSellPrice());
            stmt.setInt(4, product.getCurrentStock());
            stmt.setInt(5, product.getMinStockLevel());
            stmt.setString(6, product.getUnit());
            stmt.setString(7, product.getDescription());
            stmt.setInt(8, product.getId());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM products WHERE id = ?";
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
    public List<Product> findByCategory(String category) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE category = ?";
        try (Connection conn = DatabaseConnector.connect("QuanLySanBong");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, category);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                products.add(mapResultSetToProduct(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public List<Product> findLowStock() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE current_stock <= min_stock_level";
        try (Connection conn = DatabaseConnector.connect("QuanLySanBong");
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                products.add(mapResultSetToProduct(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public List<Product> searchByName(String keyword) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE name LIKE ?";
        try (Connection conn = DatabaseConnector.connect("QuanLySanBong");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + keyword + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                products.add(mapResultSetToProduct(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
    @Override 
    public Product findByName(String name) {
        String sql = "SELECT * FROM products WHERE name = ?";
        try (Connection conn = DatabaseConnector.connect("QuanLySanBong");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToProduct(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Product mapResultSetToProduct(ResultSet rs) throws SQLException {
        return new Product(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("category"),
                //rs.getDouble("buy_price"),
                rs.getFloat("sell_price"),
                rs.getInt("current_stock"),
                rs.getInt("min_stock_level"),
                rs.getString("unit"),
                rs.getString("description")
        );
    }
}
