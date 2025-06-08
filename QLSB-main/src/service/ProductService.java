package service;

// Service cho Quản lý sản phẩm và kho


import DAO.ProductDAO;
import DAO.impl.ProductDAOImpl;
import model.Product;

import java.util.List;

public class ProductService {
    private ProductDAO productDAO;
    
    public ProductService() {
        this.productDAO = new ProductDAOImpl();
    }
    
    public Product getProductById(int id) {
        return productDAO.findById(id);
    }
    
    public List<Product> getAllProducts() {
        return productDAO.findAll();
    }
    
    public List<Product> getProductsByCategory(String category) {
        return productDAO.findByCategory(category);
    }
    
    public List<Product> getLowStockProducts() {
        return productDAO.findLowStock();
    }
    
    public List<Product> searchProductsByName(String keyword) {
        return productDAO.searchByName(keyword);
    }
    
    public boolean addProduct(Product product) {
        return productDAO.save(product);
    }
    
    public boolean updateProduct(Product product) {
        return productDAO.update(product);
    }
    
    public boolean deleteProduct(int id) {
        return productDAO.delete(id);
    }
    
    public boolean updateStock(int productId, int quantity) {
        Product product = getProductById(productId);
        if (product != null) {
            product.updateStock(quantity);
            return productDAO.update(product);
        }
        return false;
    }
    
    public boolean checkLowStock(int productId) {
        Product product = getProductById(productId);
        return (product != null && product.isLowStock());
    }

    public Product getProductByName(String name) {
        return productDAO.findByName(name);
    }

}




