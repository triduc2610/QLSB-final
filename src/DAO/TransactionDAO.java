package DAO;

import model.Transaction;
import java.time.LocalDate;
import java.util.List;

public interface TransactionDAO extends GenericDAO<Transaction> {
    List<Transaction> findByType(String type);
    List<Transaction> findByCategory(String category);
    List<Transaction> findByDate(LocalDate date);
    List<Transaction> findByDateRange(LocalDate startDate, LocalDate endDate);
    List<Transaction> findByPitch(int branchId);
    Transaction findByBooking(int bookingId,int pitch_Id);
    Transaction findByInvoice(int invoiceId, int pitchId);
}