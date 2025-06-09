package controller;

import java.util.List;

import model.Customer;
import service.CustomerService;
import view.CustomerListView;
import view.CustomerView;

public class CustomerController {
    private CustomerService customerService;
    private CustomerView customerView;
    private CustomerListView customerListView;
    public CustomerController(CustomerView customerView,CustomerListView customerListView) {
        this.customerService = new CustomerService();
        this.customerListView = customerListView;
        this.customerView = customerView;
    }
    
    /*public void displayAllCustomers() {
        try {
            List<Customer> customers = customerService.getAllCustomers();
            customerView.displayCustomerList(customers);
        } catch (Exception e) {
            customerView.displayError("Error retrieving customers: " + e.getMessage());
        }
    }*/

    public void processSearchCustomer() {
        String keyword = customerListView.getSearchText();
        if (keyword == null || keyword.isBlank()) {
            customerListView.showError("Vui lòng nhập từ khóa tìm kiếm");
            return;
        }
        try {
            List<Customer> customerList = customerService.searchCustomersByName(keyword);
            if ( customerList == null || customerList.isEmpty()) {
                customerListView.showError("Không tìm thấy khách hàng với từ khóa: " + keyword);
            } else {
                customerListView.loadCustomerList(customerList);
            }
        } catch (Exception e) {
            customerListView.showError("Lỗi khi tìm kiếm khách hàng: " + e.getMessage());
        }
    }
    
    public void processNewCustomer() {
        Customer customerData = customerView.getCustomerData();
        
        try {
            if(customerService.addCustomer(customerData))
            {
                customerView.displayCustomerCreationSuccess(customerData);
            }
            else
            {
                customerView.displayError("Error creating customer: Customer already exists");
                return;
            }
            
        } catch (Exception e) {
            customerView.displayError("Error creating customer: " + e.getMessage());
        }
    }
    
    public void processUpdateCustomer() {
        //displayUpdateCustomer();
        Customer customerData = getSelectedCustomer();
        //customerListView.initdialog(customerData.getName(),customerData.getPhone(),customerData.getEmail());
        customerData.setName(customerListView.getUpdatedName());
        customerData.setPhone(customerListView.getUpdatedPhone());
        customerData.setEmail(customerListView.getUpdatedEmail());

        try{
            customerService.updateCustomer(customerData);
            customerListView.showSuccess( "Customer updated successfully!");
            customerListView.showDialog(false);
            
        } catch (Exception e) {
            customerListView.showError("Error updating customer: " + e.getMessage());
        }
    }

    public void processDeleteCustomer() {
        String customerId = customerListView.getSelectedCustomerId();
        int id = Integer.parseInt(customerId);
        
        try {
            
            customerService.deleteCustomer(id);
            customerListView.showSuccess("Customer deleted successfully!");
        } catch (Exception e) {
            customerListView.showError("Error deleting customer: " + e.getMessage());
        }
    }
    public void displayUpdateCustomer() {
        Customer customerData = getSelectedCustomer();
        customerListView.initdialog(customerData.getName(),customerData.getPhone() , customerData.getEmail());
    }
    
   /* public void searchCustomerByPhone() {
        String phone = customerView.getPhoneForSearch();
        
        try {
            Customer customer = customerService.getCustomerByPhone(phone);
            if (customer != null) {
                customerView.displayCustomerDetails(customer);
            } else {
                customerView.displayCustomerNotFound();
            }
        } catch (Exception e) {
            customerView.displayError("Error searching customer: " + e.getMessage());
        }
    }
    
    /*public Customer selectCustomer() {
        try {
            List<Customer> customers = customerService.getAllCustomers();
            return customerView.selectCustomer(customers);
        } catch (Exception e) {
            customerView.displayError("Error retrieving customers: " + e.getMessage());
            return null;
        }
    }*/
    public Customer getSelectedCustomer() {
        try{
            return customerService.getCustomerByPhone(customerListView.getSelectedCustomerPhone());
        }
        catch(Exception e){
            customerView.displayError("Error retrieving customer: " + e.getMessage());
            return null;
    
        }
    }

    public void loadCustomerList(){
        List<Customer> customers = customerService.getAllCustomers();
        customerListView.loadCustomerList(customers);
    }
}