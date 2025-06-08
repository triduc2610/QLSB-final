package service;

import model.Transaction;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportService {
    private TransactionService transactionService;

    public ReportService() {
        this.transactionService = new TransactionService();
    }

    public Map<String, Double> getRevenueReportByTimeRange(LocalDate startDate, LocalDate endDate) {
        System.out.println("timerange only!!");
        double totalPitchRevenue = 0;
        double totalSalesRevenue = 0;
        double totalExpenses = 0;
        LocalDate current = startDate;
        while (!current.isAfter(endDate)) {
            List<Transaction> dailyTransactions = transactionService.getTransactionsByDate(current);
            double expenses = dailyTransactions.stream()
                    .filter(transaction -> "EXPENSE".equals(transaction.getType()))
                    .mapToDouble(Transaction::getAmount)
                    .sum();
            double pitchRevenue = dailyTransactions.stream()
                    .filter(transaction -> "BOOKING".equals(transaction.getCategory()))
                    .mapToDouble(Transaction::getAmount)
                    .sum();
            double salesRevenue = dailyTransactions.stream()
                    .filter(transaction -> "PRODUCT_SALE".equals(transaction.getCategory()))
                    .mapToDouble(Transaction::getAmount)
                    .sum();
            totalPitchRevenue += pitchRevenue;
            totalSalesRevenue += salesRevenue;
            totalExpenses += expenses;
            current = current.plusDays(1);
        }
        Map<String, Double> report = new HashMap<>();
        report.put("PitchBookings", totalPitchRevenue);
        report.put("productSales", totalSalesRevenue);
        report.put("totalRevenue", totalPitchRevenue + totalSalesRevenue);
        report.put("expenses", totalExpenses);
        report.put("netProfit", totalPitchRevenue + totalSalesRevenue - totalExpenses);
        return report;
    }

    public Map<String, Double> getRevenueReportByTimeRangeAndPitch(LocalDate startDate, LocalDate endDate,
            int pitchId) {
        System.out.println("timerange and pitchid!!");
        double totalPitchRevenue = 0;
        double totalSalesRevenue = 0;
        double totalExpenses = 0;
        LocalDate current = startDate;
        while (!current.isAfter(endDate)) {
            double pitchRevenue;
            double salesRevenue;
            List<Transaction> dailyTransactions = transactionService.getTransactionsByDate(current);
            double expenses = dailyTransactions.stream()
                    .filter(transaction -> "EXPENSE".equals(transaction.getType())
                            && transaction.getpitchId() == pitchId)
                    .mapToDouble(Transaction::getAmount)
                    .sum();
            pitchRevenue = dailyTransactions.stream()
                    .filter(transaction -> "BOOKING".equals(transaction.getCategory())
                            && transaction.getpitchId() == pitchId)
                    .mapToDouble(Transaction::getAmount)
                    .sum();
            salesRevenue = dailyTransactions.stream()
                    .filter(transaction -> "PRODUCT_SALE".equals(transaction.getCategory())
                            && transaction.getpitchId() == pitchId)
                    .mapToDouble(Transaction::getAmount)
                    .sum();

            totalPitchRevenue += pitchRevenue;
            totalSalesRevenue += salesRevenue;
            totalExpenses += expenses;
            current = current.plusDays(1);
        }
        Map<String, Double> report = new HashMap<>();
        report.put("PitchBookings", totalPitchRevenue);
        report.put("productSales", totalSalesRevenue);
        report.put("totalRevenue", totalPitchRevenue + totalSalesRevenue);
        report.put("expenses", totalExpenses);
        report.put("netProfit", totalPitchRevenue + totalSalesRevenue - totalExpenses);
        return report;
    }

    public int countPitchBookingsByTimeRange(LocalDate startDate, LocalDate endDate) {
        int totalBookings = 0;
        LocalDate current = startDate;
        while (!current.isAfter(endDate)) {
            List<Transaction> dailyTransactions = transactionService.getTransactionsByDate(current);
            int dailyBookings = (int) dailyTransactions.stream()
                    .filter(transaction -> "BOOKING".equals(transaction.getCategory()))
                    .count();
            totalBookings += dailyBookings;
            current = current.plusDays(1);
        }
        return totalBookings;
    }

    public int countProductSalesByTimeRange(LocalDate startDate, LocalDate endDate) {
        int totalSales = 0;
        LocalDate current = startDate;
        while (!current.isAfter(endDate)) {
            List<Transaction> dailyTransactions = transactionService.getTransactionsByDate(current);
            int dailySales = (int) dailyTransactions.stream()
                    .filter(transaction -> "PRODUCT_SALE".equals(transaction.getCategory()))
                    .count();
            totalSales += dailySales;
            current = current.plusDays(1);
        }
        return totalSales;
    }

    public int countPitchBookingsByTimeRangePitchid(LocalDate startDate, LocalDate endDate, int pitchId) {
        int totalBookings = 0;
        LocalDate current = startDate;
        while (!current.isAfter(endDate)) {
            List<Transaction> dailyTransactions = transactionService.getTransactionsByDate(current);
            int dailyBookings = (int) dailyTransactions.stream()
                    .filter(transaction -> "BOOKING".equals(transaction.getCategory())
                            && transaction.getpitchId() == pitchId)
                    .count();
            totalBookings += dailyBookings;
            current = current.plusDays(1);
        }
        return totalBookings;
    }

    public int countProductSalesByTimeRangePitchid(LocalDate startDate, LocalDate endDate, int pitchId) {
        int totalSales = 0;
        LocalDate current = startDate;
        while (!current.isAfter(endDate)) {
            List<Transaction> dailyTransactions = transactionService.getTransactionsByDate(current);
            int dailySales = (int) dailyTransactions.stream()
                    .filter(transaction -> "PRODUCT_SALE".equals(transaction.getCategory())
                            && transaction.getpitchId() == pitchId)
                    .count();
            totalSales += dailySales;
            current = current.plusDays(1);
        }
        return totalSales;
    }

}
