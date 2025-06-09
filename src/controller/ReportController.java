package controller;

import java.time.LocalDate;
import java.util.List;

import model.Transaction;
import service.PitchService;
import service.ReportService;
import service.TransactionService;
import utils.ConvertToVnd;
import view.ReportView;
import view.TransactionListView;

public class ReportController {
    private TransactionListView transactionListView;
    private ReportView reportView;
    private TransactionService transactionService = new TransactionService();
    private ReportService reportService = new ReportService();
    private PitchService pitchService = new PitchService();

    public ReportController(TransactionListView transactionListView, ReportView reportView) {
        // Initialize the controller with the view
        this.transactionListView = transactionListView;
        this.reportView = reportView;
    }

    public void LoadTransactionListData(int flag) {
        switch (flag) {
            case 0: // Load all transactions
                transactionListView.loadDataToTable(transactionService.getAllTransactions());
                break;
            case 1:
                transactionListView.loadDataToTable(transactionService.getTransactionsByCategory("BOOKING"));
                break;
            case 2:
                transactionListView.loadDataToTable(transactionService.getTransactionsByCategory("PRODUCT_SALE"));
                break;
            case 3:
                transactionListView.loadDataToTable(transactionService.getTransactionsByType("EXPENSE"));
                break;
            default:
                break;
        }
    }

    public void initdialog() {
        transactionListView.initdialog(pitchService.getAllPitches());
    }

    public boolean processSaveExpense() {
        Transaction transaction = transactionListView.getNewTransaction();
        System.out.println(transaction);
        if (transaction == null) {
            transactionListView.showMessage("Vui lòng nhập đầy đủ thông tin giao dịch!");
            return false;
        }
        if (transactionService.addTransaction(transaction)) {
            transactionListView.showMessage("Lưu giao dịch thành công!");
            LoadTransactionListData(3); // Refresh the expense list
            return true;
        } else {
            transactionListView.showMessage("Lưu giao dịch thất bại!");
            return false;
        }
    }

    public boolean processDeleteExpense(int id) {
        if (transactionService.deleteTransaction(id)) {
            transactionListView.showMessage("Xóa giao dịch thành công!");
            LoadTransactionListData(3);
            return true;
            // Refresh the expense list
        } else {
            transactionListView.showMessage("Xóa giao dịch thất bại!");
            return false;
        }
    }

    public void loadReportData() {
        reportView.loadPitchcb(pitchService.getAllPitches());
        // Set default dates to current month
        LocalDate now = LocalDate.now();
        LocalDate firstDayOfMonth = now.withDayOfMonth(1);
        LocalDate lastDayOfMonth = now.withDayOfMonth(now.lengthOfMonth());
        java.util.Date firstDate = java.util.Date
                .from(firstDayOfMonth.atStartOfDay(java.time.ZoneId.systemDefault()).toInstant());
        java.util.Date lastDate = java.util.Date
                .from(lastDayOfMonth.atStartOfDay(java.time.ZoneId.systemDefault()).toInstant());
        reportView.getStartDateChooser().setDate(firstDate);
        reportView.getEndDateChooser().setDate(lastDate);

        reportView.setPitchChangeAction(e -> processFilter());
        processFilter();
    }

    public void processFilter() {
        LocalDate startDate = reportView.getStartDate().toLocalDate();
        LocalDate endDate = reportView.getEndDate().toLocalDate();
        if (startDate.isAfter(endDate)) {
            reportView.showMessage("Ngày bắt đầu không thể sau ngày kết thúc!");
            return;
        }
        String totalresult;
        String countBooking;
        String countProductSales;
        int pitchId = reportView.getSelectedPitchId();
        if (pitchId == 0) {
            Double netProfit = (Double) reportService.getRevenueReportByTimeRange(startDate, endDate).get("netProfit");
            totalresult = ConvertToVnd.formatCurrency(netProfit.longValue());
            countBooking = String.valueOf(reportService.countPitchBookingsByTimeRange(startDate, endDate));
            countProductSales = String.valueOf(reportService.countProductSalesByTimeRange(startDate, endDate));
            List<Transaction> transactions = transactionService.getTransactionsByDateRange(startDate, endDate);
            reportView.loadDatatoTable(transactions);
            reportView.setSummaryBoxValues(totalresult, countBooking, countProductSales);
        } else {
            Double netProfit = (Double) reportService.getRevenueReportByTimeRangeAndPitch(startDate, endDate, pitchId)
                    .get("netProfit");
            totalresult = ConvertToVnd.formatCurrency(netProfit.longValue());
            List<Transaction> transactions = transactionService.getTransactionsByDateRange(startDate, endDate)
                    .stream()
                    .filter(transaction -> transaction.getpitchId() == pitchId)
                    .toList();
            reportView.loadDatatoTable(transactions);
            countBooking = String
                    .valueOf(reportService.countPitchBookingsByTimeRangePitchid(startDate, endDate, pitchId));
            countProductSales = String
                    .valueOf(reportService.countProductSalesByTimeRangePitchid(startDate, endDate, pitchId));
            reportView.setSummaryBoxValues(totalresult, countBooking, countProductSales);
        }
    }
}
