package DAO;
import model.Product;
import java.util.List;

public interface ProductDAO extends GenericDAO<Product> {
    List<Product> findByCategory(String category);
    List<Product> findLowStock();
    List<Product> searchByName(String keyword);
    Product findByName(String name);
}


