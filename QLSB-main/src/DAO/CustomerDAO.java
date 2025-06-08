package DAO;
import model.Customer;
import java.util.List;

public interface CustomerDAO extends GenericDAO<Customer> {
    Customer findByPhone(String phone);
    List<Customer> searchByName(String keyword);
}
