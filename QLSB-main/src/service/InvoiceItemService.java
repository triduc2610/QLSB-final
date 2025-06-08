package service;

import DAO.InvoiceItemDAO;
import DAO.impl.InvoiceItemDAOImpl;
import model.InvoiceItem;
import java.util.List;

public class InvoiceItemService {
    private InvoiceItemDAO invoiceItemDAO;

    public InvoiceItemService() {
        this.invoiceItemDAO = new InvoiceItemDAOImpl();
    }

    public InvoiceItem findById(int id) {
        return invoiceItemDAO.findById(id);
    }

    public List<InvoiceItem> getAllInvoiceItems() {
        return invoiceItemDAO.findAll();
    }

    public boolean save(InvoiceItem invoiceItem) {
        return invoiceItemDAO.save(invoiceItem);
    }

    public boolean update(InvoiceItem invoiceItem) {
        return invoiceItemDAO.update(invoiceItem);
    }

    public boolean delete(int id) {
        return invoiceItemDAO.delete(id);
    }

    public List<InvoiceItem> findByType(String type) {
        return invoiceItemDAO.findByType(type);
    }
    
    // dùng cái này để thêm các sản phẩm vào hóa đơn
    public boolean addInvoiceItem(InvoiceItem item) {
        try {
            invoiceItemDAO.save(item);
            return true;
        } 
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("edge case");
            return false;
        }
    }

   

}
