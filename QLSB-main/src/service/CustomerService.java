package service;

import DAO.CustomerDAO;
import DAO.impl.CustomerDAOImpl;
import model.Customer;
//import model.Invoice;

import java.util.List;

public class CustomerService {
    private CustomerDAO customerDAO;
    
    public CustomerService() {
        this.customerDAO = new CustomerDAOImpl();
    }
    
    public Customer getCustomerById(int id) {
        Customer customer = customerDAO.findById(id);
        return customer;
    }
    
    public Customer getCustomerByPhone(String phone) {
        return customerDAO.findByPhone(phone);
    }
    
    public List<Customer> getAllCustomers() {
        return customerDAO.findAll();
    }
    
    public List<Customer> searchCustomersByName(String keyword) {
        return customerDAO.searchByName(keyword);
    }
    
    public boolean addCustomer(Customer customer) {
        //customer.setTotalSpent(0); // Khởi tạo tổng chi tiêu
        return customerDAO.save(customer);
    }
    
    public boolean updateCustomer(Customer customer) {
        return customerDAO.update(customer);
    }
    
    public boolean deleteCustomer(int id) {
        return customerDAO.delete(id);
    }
}
