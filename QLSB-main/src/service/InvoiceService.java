package service;

import DAO.InvoiceDAO;
import DAO.impl.InvoiceDAOImpl;
import model.Invoice;
import java.time.LocalDate;
import java.util.List;

public class InvoiceService {
    private InvoiceDAO invoiceDAO;
    private TransactionService transactionService = new TransactionService();
    
    public InvoiceService() {
        this.invoiceDAO = new InvoiceDAOImpl();
    }
    
    public Invoice getInvoiceById(int id) {
        return invoiceDAO.findById(id);
    }
    
    public List<Invoice> getAllInvoices() {
        return invoiceDAO.findAll();
    }
    
    public List<Invoice> getInvoicesByCustomer(int customerId) {
        return invoiceDAO.findByCustomer(customerId);
    }
    
    public List<Invoice> getInvoicesByDate(LocalDate date) {
        return invoiceDAO.findByDate(date);
    }
    
    public List<Invoice> getInvoicesByDateRange(LocalDate startDate, LocalDate endDate) {
        return invoiceDAO.findByDateRange(startDate, endDate);
    }
    
    public List<Invoice> getInvoicesByType(String type) {
        return invoiceDAO.findByType(type);
    }

    public List<Invoice> getInvoicesByPitch(int pitchId){
        return invoiceDAO.findByPitch(pitchId);
    }
    

    public boolean addInvoice(Invoice invoice) {
        boolean invoiceSaved = invoiceDAO.save(invoice);

        if (invoiceSaved) {
            // Sau khi lưu, invoice đã có ID hợp lệ nếu dùng auto-increment
            boolean transactionSaved = transactionService.addTransaction(invoice);

            if (!transactionSaved) {
                System.out.println("Failed to save transaction for invoice ID: " + invoice.getId());
                // (Tùy chọn) Rollback việc lưu invoice nếu cần
            }
            return transactionSaved;
        }
        return false;
    }

    public boolean deleteInvoice(int id) {
        // Xóa transaction trước, sau đó xóa invoice
        boolean transactionDeleted = transactionService.deleteTransaction(id, "invoice");
        boolean invoiceDeleted = invoiceDAO.delete(id);
        return transactionDeleted && invoiceDeleted;
    }
}