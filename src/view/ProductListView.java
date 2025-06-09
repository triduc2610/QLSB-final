package view;

import model.Product;
import view.components.TableComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class ProductListView extends JPanel {
    private JTextField tfSearch;
    private JButton btnSearch;
    private JButton btnAdd, btnEdit, btnDelete, btnRefresh;
    private TableComponent<Product> productTable;
    private JButton saveChangeButton = new JButton("Lưu");
    private JButton cancelButton = new JButton("Hủy");

    // Dialog + input fields
    private JDialog dialog;
    private JTextField tfName, tfCategory, tfSellPrice, tfStock, tfMinStock, tfUnit, tfDescription;

    public ProductListView() {
        setLayout(new BorderLayout());
        init();
    }

    public void init() {
        // Panel chứa tiêu đề và tìm kiếm
        JPanel topPanel = new JPanel(new BorderLayout());

        // Tiêu đề nằm ở trên cùng
        JLabel titleLabel = new JLabel("Quản lý sản phẩm");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setOpaque(true);
        //titleLabel.setBackground(new Color(200, 220, 255));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        topPanel.add(titleLabel, BorderLayout.NORTH);

        // Panel tìm kiếm nằm dưới tiêu đề
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tfSearch = new JTextField(20);
        btnSearch = new JButton("Tìm kiếm");
        searchPanel.add(new JLabel("Tên sản phẩm:"));
        searchPanel.add(tfSearch);
        searchPanel.add(btnSearch);
        topPanel.add(searchPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);

        // Bảng dữ liệu sử dụng TableComponent
        String[] columnNames = {
            "ID", "Tên", "Danh mục", "Giá bán", "Tồn kho",
            "Tồn tối thiểu", "Đơn vị", "Mô tả"
        };
        productTable = new TableComponent<Product>(columnNames);

        // **Bọc bảng trong JScrollPane để hiện tên cột**
        JScrollPane scrollPane = new JScrollPane(productTable);
        add(scrollPane, BorderLayout.CENTER);

        // Panel chứa các nút nằm dưới cùng
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnAdd = new JButton("Thêm");
        btnEdit = new JButton("Sửa");
        btnDelete = new JButton("Xóa");
        btnRefresh = new JButton("Làm mới");
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnRefresh);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void initDialog(String name, String category, float sellPrice, int stock,
                           int minStock, String unit, String description) {
        dialog = new JDialog((Frame) null, "Thông tin sản phẩm", true);

        JPanel dialogPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        dialogPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        tfName = new JTextField(name);
        tfCategory = new JTextField(category);
        tfSellPrice = new JTextField(Float.toString(sellPrice));
        tfStock = new JTextField(Integer.toString(stock));
        tfMinStock = new JTextField(Integer.toString(minStock));
        tfUnit = new JTextField(unit);
        tfDescription = new JTextField(description);

        dialogPanel.add(new JLabel("Tên:"));
        dialogPanel.add(tfName);
        dialogPanel.add(new JLabel("Danh mục:"));
        dialogPanel.add(tfCategory);
        dialogPanel.add(new JLabel("Giá bán:"));
        dialogPanel.add(tfSellPrice);
        dialogPanel.add(new JLabel("Tồn kho:"));
        dialogPanel.add(tfStock);
        dialogPanel.add(new JLabel("Tồn tối thiểu:"));
        dialogPanel.add(tfMinStock);
        dialogPanel.add(new JLabel("Đơn vị:"));
        dialogPanel.add(tfUnit);
        dialogPanel.add(new JLabel("Mô tả:"));
        dialogPanel.add(tfDescription);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(saveChangeButton);
        buttonPanel.add(cancelButton);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(dialogPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setContentPane(mainPanel);
        dialog.setSize(350, 400);
        dialog.setLocationRelativeTo(null);
    }

    public void showDialog(boolean value) {
        if (dialog != null) {
            dialog.setVisible(value);
        }
    }

    public void hideDialog() {
        if (dialog != null) {
            dialog.setVisible(false);
        }
    }

    public void displayProductList(List<Product> list) {
        productTable.clearTable();
        for (Product p : list) {
            productTable.addRow(new Object[]{
                p.getId(), p.getName(), p.getCategory(), p.getSellPrice(),
                p.getCurrentStock(), p.getMinStockLevel(), p.getUnit(), p.getDescription()
            });
        }
    }

    public void loadProductList(List<Product> products) {
        productTable.clearTable();
        for (Product p : products) {
            Object[] rowData = {
                p.getId(),
                p.getName(),
                p.getCategory(),
                p.getSellPrice(),
                p.getCurrentStock(),
                p.getMinStockLevel(),
                p.getUnit(),
                p.getDescription()
            };
            productTable.addRow(rowData);
        }
        productTable.revalidate();
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public String getUpdateName() {
        return tfName.getText();
    }

    public String getUpdateCategory() {
        return tfCategory.getText();
    }

    public float getUpdateSellPrice() {
        try {
            return Float.parseFloat(tfSellPrice.getText());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public int getUpdateCurrentStock() {
        try {
            return Integer.parseInt(tfStock.getText());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public int getUpdateMinStockLevel() {
        try {
            return Integer.parseInt(tfMinStock.getText());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public String getUpdateUnit() {
        return tfUnit.getText();
    }

    public String getUpdateDescription() {
        return tfDescription.getText();
    }

    public void clearJtextFieldSearch() {
        tfSearch.setText("");
    }

    public String getSelectProductID() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow != -1) {
            return String.valueOf(productTable.getValueAt(selectedRow, 0));
        }
        return null;
    }

    public int getSelectedProductIndex() {
        return productTable.getSelectedRow();
    }

    public TableComponent<Product> getProductTable() {
        return productTable;
    }

    public String getProductName() {
        return tfSearch.getText();
    }

    // Set action listeners
    public void setSaveEditAction(ActionListener listener) {
        saveChangeButton.addActionListener(listener);
    }

    public void setCancelEditAction(ActionListener listener) {
        cancelButton.addActionListener(listener);
    }

    public void setAddAction(ActionListener listener) {
        btnAdd.addActionListener(listener);
    }

    public void setEditAction(ActionListener listener) {
        btnEdit.addActionListener(listener);
    }

    public void setDeleteAction(ActionListener listener) {
        btnDelete.addActionListener(listener);
    }

    public void setRefreshAction(ActionListener listener) {
        btnRefresh.addActionListener(listener);
    }

    public void setSearchAction(ActionListener listener) {
        btnSearch.addActionListener(listener);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Product List");
            ProductListView productListView = new ProductListView();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(productListView);
            frame.setSize(900, 600);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
