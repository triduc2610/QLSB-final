package view;

import model.Branch;
import model.Pitch;
import view.components.TableComponent;
import view.components.DialogComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class PitchListView extends JPanel {
    private TableComponent<Object> pitchTable;
    private JButton addButton, editButton, deleteButton, refreshButton;
    private DialogComponent dialog = new DialogComponent("Thông tin sân", 400, 300);

    private JButton saveAddButton = new JButton("Thêm");
    private JButton saveEditButton = new JButton("Lưu");
    private JButton cancelButton = new JButton("Hủy");

    // Thêm combobox chọn chi nhánh
    private JComboBox<Branch> branchComboBox = new JComboBox<>();
    private JLabel branchLabel = new JLabel("Chi nhánh:");

    public PitchListView() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Title
        JLabel titleLabel = new JLabel("DANH SÁCH SÂN", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(titleLabel, BorderLayout.NORTH);

        // Table
        String[] columnNames = {"Mã sân", "Tên sân", "Loại sân", "Giá/giờ", "Mô tả", "Mã chi nhánh", "Hoạt động"};
        pitchTable = new TableComponent<>(columnNames);
        pitchTable.setFillsViewportHeight(true);
        JScrollPane tableScrollPane = new JScrollPane(pitchTable);
        add(tableScrollPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new BorderLayout());

        // Panel chứa label và combobox chi nhánh bên trái
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.add(branchLabel);
        leftPanel.add(branchComboBox);
        buttonPanel.add(leftPanel, BorderLayout.WEST);

        // Các nút chức năng bên phải
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        addButton = new JButton("Thêm");
        editButton = new JButton("Sửa");
        deleteButton = new JButton("Xóa");
        refreshButton = new JButton("Làm mới");
        rightPanel.add(addButton);
        rightPanel.add(editButton);
        rightPanel.add(deleteButton);
        rightPanel.add(refreshButton);
        buttonPanel.add(rightPanel, BorderLayout.EAST);

        add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(false);
    }

    public void loadDataToTable(List<Pitch> pitches) {
        pitchTable.clearTable();
        for (Pitch pitch : pitches) {
            Object[] rowData = {
                pitch.getId(),
                pitch.getName(),
                pitch.getType(),
                pitch.getPricePerHour(),
                pitch.getDescription(),
                pitch.getBranchId(),
                pitch.isActive() ? "Có" : "Không"
            };
            pitchTable.addRow(rowData);
        }
    }

    public void loadComboboxData(List<Branch> branchNames) {
        branchComboBox.removeAllItems();
        for (Branch name : branchNames) {
            branchComboBox.addItem(name);
        }
        branchComboBox.setSelectedIndex(0); // Đặt giá trị mặc định là không chọn
    }

    public int getSelectedPitchId() {
        try {
            return (int) pitchTable.getValueAt(pitchTable.getSelectedRow(), 0);
        } catch (Exception e) {
            //JOptionPane.showMessageDialog(this, "Hãy chọn một sân!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return -1;
        }
    }

    public int getSelectedBranchId() {
        try {
            Branch selectedBranch = (Branch) branchComboBox.getSelectedItem();
            if (selectedBranch != null) {
                return selectedBranch.getId();
            } else {
                //JOptionPane.showMessageDialog(this, "Hãy chọn một chi nhánh!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                return -1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void initDialog(String name, String type, String price, String description, String branchId, boolean active) {
        JPanel dialogPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        dialogPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField nameField = new JTextField(name);
        JTextField typeField = new JTextField(type);
        JTextField priceField = new JTextField(price);
        JTextField descriptionField = new JTextField(description);
        JTextField branchIdField = new JTextField(branchId);
        branchIdField.setEditable(false);
        JCheckBox activeCheck = new JCheckBox("Hoạt động", active);

        dialogPanel.add(new JLabel("Tên sân:"));
        dialogPanel.add(nameField);
        dialogPanel.add(new JLabel("Loại sân:"));
        dialogPanel.add(typeField);
        dialogPanel.add(new JLabel("Giá/giờ:"));
        dialogPanel.add(priceField);
        dialogPanel.add(new JLabel("Mô tả:"));
        dialogPanel.add(descriptionField);
        dialogPanel.add(new JLabel("Mã chi nhánh:"));
        dialogPanel.add(branchIdField);
        dialogPanel.add(new JLabel("Trạng thái:"));
        dialogPanel.add(activeCheck);

        dialog.registerComponent("name", nameField);
        dialog.registerComponent("type", typeField);
        dialog.registerComponent("price", priceField);
        dialog.registerComponent("description", descriptionField);
        dialog.registerComponent("branchId", branchIdField);
        dialog.registerComponent("active", activeCheck);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(saveEditButton);
        buttonPanel.add(cancelButton);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(dialogPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setContentPane(mainPanel);
    }

    public void initDialogForAdding(String branchId) {
        JPanel dialogPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        dialogPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField nameField = new JTextField();
        JTextField typeField = new JTextField();
        JTextField priceField = new JTextField();
        JTextField descriptionField = new JTextField();
        JTextField branchIdField = new JTextField(branchId);
        branchIdField.setEditable(false);
        JCheckBox activeCheck = new JCheckBox("Hoạt động", true);

        dialogPanel.add(new JLabel("Tên sân:"));
        dialogPanel.add(nameField);
        dialogPanel.add(new JLabel("Loại sân:"));
        dialogPanel.add(typeField);
        dialogPanel.add(new JLabel("Giá/giờ:"));
        dialogPanel.add(priceField);
        dialogPanel.add(new JLabel("Mô tả:"));
        dialogPanel.add(descriptionField);
        dialogPanel.add(new JLabel("Mã chi nhánh:"));
        dialogPanel.add(branchIdField);
        dialogPanel.add(new JLabel("Trạng thái:"));
        dialogPanel.add(activeCheck);

        dialog.registerComponent("name", nameField);
        dialog.registerComponent("type", typeField);
        dialog.registerComponent("price", priceField);
        dialog.registerComponent("description", descriptionField);
        dialog.registerComponent("branchId", branchIdField);
        dialog.registerComponent("active", activeCheck);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(saveAddButton);
        buttonPanel.add(cancelButton);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(dialogPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setContentPane(mainPanel);
    }

    public void showDialog(boolean value) {
        this.dialog.setVisible(value);
    }

    public Pitch getUpdatedPitch() {
        return new Pitch(
            getSelectedPitchId(),
            dialog.getFieldValue("name"),
            dialog.getFieldValue("type"),
            Double.parseDouble(dialog.getFieldValue("price")),
            dialog.getFieldValue("description"),
            Integer.parseInt(dialog.getFieldValue("branchId")),
            ((JCheckBox) dialog.getComponent("active")).isSelected()
        );
    }

    public Pitch getNewPitch() {
        return new Pitch(
            0,
            dialog.getFieldValue("name"),
            dialog.getFieldValue("type"),
            Double.parseDouble(dialog.getFieldValue("price")),
            dialog.getFieldValue("description"),
            Integer.parseInt(dialog.getFieldValue("branchId")),
            ((JCheckBox) dialog.getComponent("active")).isSelected()
        );
    }

    public void setAddAction(ActionListener listener) {
        addButton.addActionListener(listener);
    }

    public void setEditAction(ActionListener listener) {
        editButton.addActionListener(listener);
    }

    public void setDeleteAction(ActionListener listener) {
        deleteButton.addActionListener(listener);
    }

    public void setRefreshAction(ActionListener listener) {
        refreshButton.addActionListener(listener);
    }

    public void setComboBoxAction(ActionListener listener) {
        branchComboBox.addActionListener(listener);
    }

    public void setSaveAddAction(ActionListener listener) {
        saveAddButton.addActionListener(listener);
    }

    public void setSaveEditAction(ActionListener listener) {
        saveEditButton.addActionListener(listener);
    }

    public void setCancelAction(ActionListener listener) {
        cancelButton.addActionListener(listener);
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }
}
