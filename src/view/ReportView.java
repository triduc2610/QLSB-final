package view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import view.components.TableComponent;
import com.toedter.calendar.JDateChooser;

import model.Pitch;
import model.Transaction;
import utils.ConvertToVnd;
import utils.DateTimeUtils;

public class ReportView extends JPanel {
  private JPanel mainPanel;
  private TableComponent<Transaction> reportTable;
  private String[] columns = { "Hình thức", "Phân loại", "Số tiền", "Mã sân", "Mục đích" };
  private JComboBox<String> pitchComboBox;
  private JDateChooser startDateChooser;
  private JDateChooser endDateChooser;
  private JButton resetPitchButton;
  private JButton filterDateButton;

  // Summary box labels
  private JLabel incomeValueLabel;
  private JLabel bookingValueLabel;
  private JLabel serviceValueLabel;

  private JPanel incomeBox;

  public ReportView() {
    // Initialize table using TableComponent
    reportTable = new TableComponent<>(columns);

    // Set up the main layout
    setLayout(new BorderLayout());
    createMainPanel();
    createHeaderPanel();
    createContentPanel();
    createTablePanel();

    // Add the main panel to this panel
    add(mainPanel, BorderLayout.CENTER);
  }

  private void createMainPanel() {
    mainPanel = new JPanel();
    mainPanel.setLayout(new BorderLayout(20, 20));
    mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
    mainPanel.setBackground(Color.WHITE);
  }

  private void createHeaderPanel() {
    JPanel headerPanel = new JPanel(new GridLayout(1, 3, 20, 0));
    headerPanel.setBackground(mainPanel.getBackground());

    // Create summary boxes with static/dummy values
    Color incomeColor = new Color(46, 204, 113); // Green for positive
    incomeBox = createSummaryBox("Doanh thu", "0đ", "Tổng doanh thu", incomeColor, true);
    JPanel bookingBox = createSummaryBox("Lượt đặt sân", "0", "Tổng lượt sử dụng", new Color(52, 152, 219), false);
    JPanel serviceBox = createSummaryBox("Dịch vụ", "0", "Tổng dịch vụ sử dụng", new Color(155, 89, 182), false);

    headerPanel.add(incomeBox);
    headerPanel.add(bookingBox);
    headerPanel.add(serviceBox);

    mainPanel.add(headerPanel, BorderLayout.NORTH);
  }

  private void createContentPanel() {
    JPanel contentPanel = new JPanel(new GridLayout(1, 2, 20, 0));
    contentPanel.setBackground(mainPanel.getBackground());

    // Left panel - Pitch Statistics
    JPanel pitchStatsPanel = createStyledPanel("Thống kê theo sân");
    JPanel pitchContent = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
    pitchContent.setBackground(Color.WHITE);

    pitchComboBox = new JComboBox<>();
    pitchComboBox.setPreferredSize(new Dimension(150, 30));

    resetPitchButton = createStyledButton("Reset");

    pitchContent.add(pitchComboBox);
    pitchContent.add(resetPitchButton);
    pitchStatsPanel.add(pitchContent, BorderLayout.CENTER);

    // Right panel - Time Statistics
    JPanel timeStatsPanel = createStyledPanel("Thống kê theo thời gian");
    JPanel timeContent = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
    timeContent.setBackground(Color.WHITE);

    startDateChooser = new JDateChooser();
    endDateChooser = new JDateChooser();
    startDateChooser.setPreferredSize(new Dimension(120, 30));
    endDateChooser.setPreferredSize(new Dimension(120, 30));
    startDateChooser.setDateFormatString("dd/MM/yyyy");
    endDateChooser.setDateFormatString("dd/MM/yyyy");
    LocalDateTime now = LocalDateTime.now();
    startDateChooser.setDate(DateTimeUtils.toDate(now.withDayOfMonth(1))); 
    endDateChooser.setDate(DateTimeUtils.toDate(now.withDayOfMonth(now.toLocalDate().lengthOfMonth())));

    filterDateButton = createStyledButton("Lọc");

    timeContent.add(new JLabel("Từ:"));
    timeContent.add(startDateChooser);
    timeContent.add(new JLabel("Đến:"));
    timeContent.add(endDateChooser);
    timeContent.add(filterDateButton);
    timeStatsPanel.add(timeContent, BorderLayout.CENTER);

    contentPanel.add(pitchStatsPanel);
    contentPanel.add(timeStatsPanel);
    mainPanel.add(contentPanel, BorderLayout.CENTER);
  }

  private void createTablePanel() {
    JPanel tablePanel = new JPanel(new BorderLayout(0, 10));
    tablePanel.setBackground(mainPanel.getBackground());

    // Table title
    JLabel tableTitle = new JLabel("Chi tiết báo cáo", SwingConstants.LEFT);
    tableTitle.setFont(new Font("Arial", Font.BOLD, 16));
    tablePanel.add(tableTitle, BorderLayout.NORTH);

    // Table with scroll pane
    reportTable.setRowHeight(30);
    reportTable.setFont(new Font("Arial", Font.PLAIN, 12));
    reportTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
    reportTable.getTableHeader().setBackground(new Color(240, 240, 240));
    reportTable.setSelectionBackground(new Color(232, 241, 249));
    reportTable.setSelectionForeground(Color.BLACK);
    reportTable.setShowGrid(true);
    reportTable.setGridColor(new Color(230, 230, 230));

    // Add table to scroll pane
    JScrollPane scrollPane = new JScrollPane(reportTable);
    scrollPane.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));
    scrollPane.setPreferredSize(new Dimension(800, 410));
    tablePanel.add(scrollPane, BorderLayout.CENTER);

    mainPanel.add(tablePanel, BorderLayout.SOUTH);
  }

  private JPanel createSummaryBox(String title, String value, String subtitle, Color color, boolean isIncome) {
    JPanel panel = new JPanel();
    panel.setLayout(new BorderLayout());
    panel.setBackground(color);
    panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

    JLabel titleLabel = new JLabel(title);
    titleLabel.setForeground(Color.WHITE);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 14));

    JLabel valueLabel = new JLabel(value);
    valueLabel.setForeground(Color.WHITE);
    valueLabel.setFont(new Font("Arial", Font.BOLD, 24));

    JLabel subtitleLabel = new JLabel(subtitle);
    subtitleLabel.setForeground(new Color(255, 255, 255, 200));
    subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 12));

    JPanel textPanel = new JPanel(new GridLayout(3, 1, 5, 5));
    textPanel.setBackground(color);
    textPanel.add(titleLabel);
    textPanel.add(valueLabel);
    textPanel.add(subtitleLabel);
    textPanel.setOpaque(false);
    panel.add(textPanel, BorderLayout.CENTER);

    // Save reference to value label for updating
    if (isIncome) {
      incomeValueLabel = valueLabel;
    } else if ("Lượt đặt sân".equals(title)) {
      bookingValueLabel = valueLabel;
    } else if ("Dịch vụ".equals(title)) {
      serviceValueLabel = valueLabel;
    }
    return panel;
  }

  private void updateIncomeBoxColor(String incomeText) {
    try {
      // Remove all non-numeric characters except minus sign and digits
      String cleanNumber = incomeText
          .replaceAll("[^0-9\\-]", "") // Remove everything except digits and minus sign
          .trim();

      // Handle negative sign
      boolean isNegative = cleanNumber.startsWith("-");
      if (isNegative) {
        cleanNumber = cleanNumber.substring(1);
      }

      // Parse to number
      long value = Long.parseLong(cleanNumber);
      if (isNegative) {
        value = -value;
      }

      // Determine color
      Color newColor;
      if (value > 0) {
        newColor = new Color(46, 204, 113); // Green for positive
      } else if (value < 0) {
        newColor = new Color(231, 76, 60); // Red for negative
      } else {
        newColor = new Color(243, 156, 18); // Light orange for zero
      }

      // Update colors
      incomeBox.setBackground(newColor);
      updateComponentColors(incomeBox, newColor);

    } catch (Exception e) {
      // On error, set to orange to indicate there's an issue
      Color errorColor = new Color(243, 156, 18);
      incomeBox.setBackground(errorColor);
      updateComponentColors(incomeBox, errorColor);
    }
  }

  private void updateComponentColors(Container container, Color color) {
    container.setBackground(color);
    for (Component comp : container.getComponents()) {
      comp.setBackground(color);
      if (comp instanceof Container) {
        updateComponentColors((Container) comp, color);
      }
    }
  }

  // Method to update summary box values
  public void setSummaryBoxValues(String income, String booking, String service) {
    if (incomeValueLabel != null) {
      incomeValueLabel.setText(income);
      updateIncomeBoxColor(income);
    }
    if (bookingValueLabel != null)
      bookingValueLabel.setText(booking);
    if (serviceValueLabel != null)
      serviceValueLabel.setText(service);
  }

  private JPanel createStyledPanel(String title) {
    JPanel panel = new JPanel(new BorderLayout(10, 10));
    panel.setBackground(Color.WHITE);
    panel.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(230, 230, 230)),
        BorderFactory.createEmptyBorder(15, 15, 15, 15)));

    JLabel titleLabel = new JLabel(title);
    titleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
    panel.add(titleLabel, BorderLayout.NORTH);

    return panel;
  }

  private JButton createStyledButton(String text) {
    JButton button = new JButton(text);
    button.setPreferredSize(new Dimension(80, 30));
    button.setBackground(new Color(52, 152, 219));
    button.setForeground(Color.WHITE);
    button.setFocusPainted(false);
    button.setBorderPainted(false);
    button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    return button;
  }

  // #region Getters and Setters
  public void loadDatatoTable(List<Transaction> transactions) {
    reportTable.clearTable();
    for (Transaction transaction : transactions) {
      Object[] row = new Object[5];
      row[0] = transaction.getType();
      row[1] = transaction.getCategory();
      row[2] = ConvertToVnd.formatCurrency((long) transaction.getAmount());
      row[3] = transaction.getpitchId();
      row[4] = transaction.getDescription();
      reportTable.addRow(row);
    }
  }

  public void loadPitchcb(List<Pitch> pitches) {
    pitchComboBox.removeAllItems();
    pitchComboBox.addItem("Tất cả");
    for (Pitch pitch : pitches) {
      pitchComboBox.addItem(pitch.getId() + " - " + pitch.getName());
    }
  }

  public int getSelectedPitchId() {
    String selected = (String) pitchComboBox.getSelectedItem();
    if (selected == null) {
      showMessage(selected + " không hợp lệ. Vui lòng chọn lại.");
    }
    return selected != null && !selected.equals("Tất cả") ? Integer.parseInt(selected.split(" - ")[0]) : 0;
  }

  public Date getStartDate() {
    return startDateChooser.getDate() != null ? new Date(startDateChooser.getDate().getTime()) : null;
  }

  public Date getEndDate() {
    return endDateChooser.getDate() != null ? new Date(endDateChooser.getDate().getTime()) : null;
  }

  public JDateChooser getStartDateChooser() {
    return startDateChooser;
  }

  public JDateChooser getEndDateChooser() {
    return endDateChooser;
  }
  // #endregion

  // #region action listeners
  public void setResetPitch(ActionListener listener) {
    resetPitchButton.addActionListener(listener);
  }

  public void setFilterDateAction(ActionListener listener) {
    filterDateButton.addActionListener(listener);
  }

  public void setPitchChangeAction(ActionListener listener) {
    pitchComboBox.addActionListener(listener);
  }
  // #endregion

  public void showMessage(String message) {
    JOptionPane.showMessageDialog(this, message, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
  }
}