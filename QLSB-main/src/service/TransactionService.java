package service;

import DAO.TransactionDAO;
import DAO.impl.BookingDAOImpl;
import DAO.impl.InvoiceDAOImpl;
import DAO.impl.TransactionDAOImpl;
import DAO.BookingDAO;
import DAO.InvoiceDAO;

import model.Invoice;
import model.Transaction;
import model.Booking;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class TransactionService {
    private BookingDAO bookingDAO = new BookingDAOImpl();
    private InvoiceDAO invoiceDAO = new InvoiceDAOImpl();
    private TransactionDAO transactionDAO;
    public TransactionService() {
        this.transactionDAO = new TransactionDAOImpl();
       // this.bookingService = new BookingService();
    }
    
    public Transaction getTransactionById(int id) {
        return transactionDAO.findById(id);
    }
    
    public List<Transaction> getAllTransactions() {
        return transactionDAO.findAll();
    }
    
    public List<Transaction> getTransactionsByType(String type) {
        return transactionDAO.findByType(type);
    }
    
    public List<Transaction> getTransactionsByCategory(String category) {
        return transactionDAO.findByCategory(category);
    }
    
    public List<Transaction> getTransactionsByDate(LocalDate date) {
        return transactionDAO.findByDate(date);
    }
    
    public List<Transaction> getTransactionsByDateRange(LocalDate startDate, LocalDate endDate) {
        return transactionDAO.findByDateRange(startDate, endDate);
    }
    
    public List<Transaction> getTransactionsByPitch(int pitchId) {
        return transactionDAO.findByPitch(pitchId);
    }
    
    public boolean addTransaction(Object object) { 
        if(object instanceof Booking){
            Booking booking = (Booking) object;
            Transaction transaction = new Transaction(booking.getId(), "INCOME", "BOOKING", 
            booking.getTotalPrice(), booking.getNote(), booking.getId(), booking.getPitchId(),LocalDateTime.now());
            System.out.println("Transaction created for Booking: " + transaction);
            return transactionDAO.save(transaction);
        }
        if(object instanceof Invoice){
            Invoice invoice = (Invoice) object;
            Transaction transaction = new Transaction(invoice.getId(), "INCOME", "PRODUCT_SALE", 
            invoice.getTotal(), invoice.getNote(), invoice.getId(), invoice.getPitchId(),LocalDateTime.now());
            System.out.println("Transaction created for Invoice: " + transaction);
            return transactionDAO.save(transaction);
        }
        System.out.println("faled to add transaction!");
        return false;
    }

    

    public boolean addTransaction(Transaction transaction){
        return transactionDAO.save(transaction);
    }    

    
    // public boolean updateTransaction(Object object) {
    //     if(object instanceof Booking){
    //         Booking booking = (Booking) object;
    //         Transaction transaction = new Transaction(booking.getId(), "INCOME", "BOOKING", 
    //         booking.getTotalPrice(), booking.getNote(), booking.getId(), booking.getPitchId());
    //         return transactionDAO.update(transaction);
    //     }
    //     if(object instanceof Invoice){
    //         Invoice invoice = (Invoice) object;
    //         Transaction transaction = new Transaction(invoice.getId(), "INCOME", "PRODUCT_SALE", 
    //         invoice.getTotal(), invoice.getNote(), invoice.getId(), invoice.getPitchId());
    //         return transactionDAO.update(transaction);
    //     }
    //     return false;
    // }

    public boolean deleteTransaction(int objectId, String objectType) {
        if ("Booking".equalsIgnoreCase(objectType)) {
            Booking booking = bookingDAO.findById(objectId);
            System.out.println("data for Booking: " + booking.getId() + " " + booking.getPitchId());
            Transaction transaction = transactionDAO.findByBooking(booking.getId(), booking.getPitchId());
            System.out.println("Deleting transaction for Booking: " + transaction);
            return transactionDAO.delete(transaction.getId());
        }
        // if(object instanceof Booking){
        //     Booking booking = (Booking) object;
        //     Transaction transaction = transactionDAO.findByBooking(booking.getId(), booking.getPitchId());
        //     return transactionDAO.delete(transaction.getId());
        // }
        if("Invoice".equalsIgnoreCase(objectType)){
            Invoice invoice = invoiceDAO.findById(objectId);
            Transaction transaction = transactionDAO.findByInvoice(invoice.getId(), invoice.getPitchId());
            System.out.println("Deleting transaction for Invoice: " + transaction);
            return transactionDAO.delete(transaction.getId());
        }
        System.out.println("loi ko the xoa transaction");
        return false;
    }
    public boolean deleteTransaction(int id){
        return transactionDAO.delete(id);
    }
    
    // public boolean createExpenseTransaction(int transactionId, String type, String category, double amount, String description, int branchId) {
    //     Transaction transaction = new Transaction(transactionId, type, category, amount, description, 0, branchId);
    //     return addTransaction(transaction);
    // }
   
    // public boolean createTransactionbyinvoice(Invoice invoice,int transactionId,String type, String category,String description)
    // {
    //     Branch branch = branchDAO.findByPitch(invoice.getPitchId());
    //     Transaction transaction = new Transaction(transactionId, type, category,12,description,invoice.getId(), branch.getId());
    //     return addTransaction(transaction);
    // }
    // Tính tổng thu trong khoảng thời gian
    // + tinh tong thu tron ngay
    public double calculateTotalIncome(LocalDate startDate, LocalDate endDate) {
        List<Transaction> transactions = getTransactionsByDateRange(startDate, endDate);
        double total = 0;
        
        for (Transaction transaction : transactions) {
            if ("INCOME".equals(transaction.getType())) {
                total += transaction.getAmount();
            }
        }
        return total;
    }
    public double calculateTotalIncome(LocalDate date) {
        List<Transaction> transactions = getTransactionsByDate(date);
        double total = 0;
        
        for (Transaction transaction : transactions) {
            if ("INCOME".equals(transaction.getType())) {
                total += transaction.getAmount();
            }
        }
        return total;
    }

    // Tính tổng chi trong khoảng thời gian
    public double calculateTotalExpense(LocalDate startDate, LocalDate endDate) {
        List<Transaction> transactions = getTransactionsByDateRange(startDate, endDate);
        double total = 0;
        
        for (Transaction transaction : transactions) {
            if ("EXPENSE".equals(transaction.getType())) {
                total += transaction.getAmount();
            }
        }
        return total;
    }
    
    // Tính lợi nhuận trong khoảng thời gian
    public double calculateProfit(LocalDate startDate, LocalDate endDate) {
        double income = calculateTotalIncome(startDate, endDate);
        double expense = calculateTotalExpense(startDate, endDate);
        return income - expense;
    }
}