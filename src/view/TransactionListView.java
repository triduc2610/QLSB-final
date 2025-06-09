package view;

import view.components.TableComponent;
import view.components.DialogComponent;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.List;

import model.Pitch;
import model.Transaction;

public class TransactionListView extends JPanel {
    private TableComponent<Object> transactionTable;
    private JButton addButton, deleteButton, refreshButton; // Xóa editButton
    private JButton saveButton = new JButton("Lưu");
    private JButton cancelButton = new JButton("Hủy");
    private JComboBox<String> filterComboBox;
    private DialogComponent dialog;

    public TransactionListView() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Title
        JLabel titleLabel = new JLabel("DANH SÁCH GIAO DỊCH", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(titleLabel, BorderLayout.NORTH);

        // Table
        String[] columnNames = {"Mã giao dịch", "Loại", "Danh mục", "Số tiền", "Mô tả", "Liên quan", "Mã Sân"};
        transactionTable = new TableComponent<>(columnNames);
        transactionTable.setFillsViewportHeight(true);
        JScrollPane tableScrollPane = new JScrollPane(transactionTable);
        add(tableScrollPane, BorderLayout.CENTER);

        // Button panel đặt dưới bảng, bố trí lại theo yêu cầu
        JPanel buttonPanel = new JPanel(new BorderLayout());

        // Combobox filter bên trái
        String[] filterOptions = {"Xem toàn bộ giao dịch", "Xem các giao dịch đặt sân", "Xem các giao dịch bán hàng", "Xem các giao dịch chi phí"};
        filterComboBox = new JComboBox<>(filterOptions);
        filterComboBox.setPreferredSize(new Dimension(250, 28));
        filterComboBox.setBorder(BorderFactory.createEmptyBorder(6, 18, 6, 18));
        buttonPanel.add(filterComboBox, BorderLayout.WEST);

        // Nút Thêm ở giữa
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        addButton = new JButton("Thêm");
        addButton.setPreferredSize(new Dimension(120, 32));
        addButton.setMargin(new Insets(6, 18, 6, 18));
        centerPanel.add(addButton);
        buttonPanel.add(centerPanel, BorderLayout.CENTER);

        // Nút Làm mới và Xóa bên phải
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        deleteButton = new JButton("Xóa");
        deleteButton.setPreferredSize(new Dimension(90, 32));
        deleteButton.setMargin(new Insets(6, 12, 6, 12));
        refreshButton = new JButton("Làm mới");
        refreshButton.setPreferredSize(new Dimension(90, 32));
        refreshButton.setMargin(new Insets(6, 12, 6, 12));
        addButton.setVisible(false);
        deleteButton.setVisible(false);
        refreshButton.setVisible(true);
        rightPanel.add(addButton);
        rightPanel.add(deleteButton);
        rightPanel.add(refreshButton);
        buttonPanel.add(rightPanel, BorderLayout.EAST);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void initdialog(List<Pitch> pitchList) {
        dialog = new DialogComponent("Thông tin giao dịch", 500, 350); // tăng kích thước
        JPanel dialogPanel = new JPanel(new GridBagLayout());
        dialogPanel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0; gbc.gridy = 0;

        // Danh mục (combobox)
        dialogPanel.add(new JLabel("Danh mục:"), gbc);
        gbc.gridx = 1;
        JComboBox<String> categoryComboBox = new JComboBox<>(new String[]{"MAINTENANCE", "SALARY"});
        dialogPanel.add(categoryComboBox, gbc);

        // Số tiền
        gbc.gridx = 0; gbc.gridy++;
        dialogPanel.add(new JLabel("Số tiền:"), gbc);
        gbc.gridx = 1;
        JTextField amountField = new JTextField();
        dialogPanel.add(amountField, gbc);

        // Mô tả
        gbc.gridx = 0; gbc.gridy++;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0;
        gbc.weighty = 0;
        dialogPanel.add(new JLabel("Mô tả:"), gbc);

        gbc.gridx = 1;
        // Đặt fill và weight cho JTextArea
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        JTextArea descriptionField = new JTextArea(4, 30);
        descriptionField.setLineWrap(true);
        descriptionField.setWrapStyleWord(true);
        JScrollPane descriptionScrollPane = new JScrollPane(descriptionField);
        dialogPanel.add(descriptionScrollPane, gbc);

        // Reset lại cho các thành phần sau
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Combobox pitchId
        gbc.gridx = 0; gbc.gridy++;
        dialogPanel.add(new JLabel("Sân:"), gbc);
        gbc.gridx = 1;
        JComboBox<String> pitchComboBox = new JComboBox<>();
        for(Pitch pitch : pitchList) {
            pitchComboBox.addItem(pitch.getName() + " (ID: " + pitch.getId() + ")");
        }
        dialogPanel.add(pitchComboBox, gbc);

        // Đăng ký component để lấy dữ liệu sau này
        dialog.registerComponent("category", categoryComboBox);
        dialog.registerComponent("amount", amountField);
        dialog.registerComponent("description", descriptionField);
        dialog.registerComponent("pitchId", pitchComboBox);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(dialogPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setContentPane(mainPanel);
    }

    public void loadDataToTable(List<Transaction> transactions) {
        transactionTable.clearTable();
        for (Transaction t : transactions) {
            Object[] rowData = {
                t.getId(),
                t.getType(),
                t.getCategory(),
                t.getAmount(),
                t.getDescription(),
                t.getRelatedId(),
                t.getpitchId()
            };
            transactionTable.addRow(rowData);
        }
    }

    public void setBtnforExpense(boolean isExpense) {
        if (isExpense) {
            addButton.setVisible(true);
            deleteButton.setVisible(true);
            refreshButton.setVisible(true);
        } else {
            addButton.setVisible(false);
            deleteButton.setVisible(false);
            refreshButton.setVisible(true);
        }
    }

    public int getSelectedTransactionId() {
        try {
            return (int) transactionTable.getValueAt(transactionTable.getSelectedRow(), 0);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Hãy chọn một giao dịch!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return -1;
        }
    }

    public Transaction getNewTransaction() {
        String category = "";
        Object catObj = dialog.getComboBoxValue("category");
        if (catObj != null) category = catObj.toString();

        String description = dialog.getFieldValue("description");

        double amount = 0;
        try {
            amount = Double.parseDouble(dialog.getFieldValue("amount").replace(",", "").trim());
        } catch (NumberFormatException e) {
            amount = 0;
        }

        // Lấy pitchId từ chuỗi "Tên sân (ID: xx)"
        int pitchId = -1;
        Object selectedPitch = dialog.getComboBoxValue("pitchId");
        if (selectedPitch != null) {
            String selectedPitchStr = selectedPitch.toString();
            int idx = selectedPitchStr.lastIndexOf("ID: ");
            if (idx != -1) {
                try {
                    pitchId = Integer.parseInt(selectedPitchStr.substring(idx + 4, selectedPitchStr.length() - 1));
                } catch (Exception e) {
                    pitchId = -1;
                }
            }
        }
        return new Transaction(
            0,
            "EXPENSE",
            category,
            amount,
            description,
            0,
            pitchId,
            LocalDateTime.now()
        );
    }

    public int getSelectedFilterIndex() {
        return filterComboBox.getSelectedIndex();
    }

    public void setAddAction(ActionListener listener) {
        addButton.addActionListener(listener);
    }
    public void setDeleteAction(ActionListener listener) {
        deleteButton.addActionListener(listener);
    }
    public void setRefreshAction(ActionListener listener) {
        refreshButton.addActionListener(listener);
    }
    public void setFilterAction(ActionListener listener) {
        filterComboBox.addActionListener(listener);
    }
    public void setSaveAction(ActionListener listener) {
        saveButton.addActionListener(listener);
    }
    public void setCancelAction(ActionListener listener) {
        cancelButton.addActionListener(listener);
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }
    public void showDialog(boolean visible) {
        if (dialog != null) {
            dialog.setVisible(visible);
        } else {
            JOptionPane.showMessageDialog(this, "Dialog chưa được khởi tạo!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}
