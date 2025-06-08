package view.components;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;
import java.awt.*;
//import java.util.List;

public class TableComponent<T> extends JTable {
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> sorter;
    

    public TableComponent(String[] columnNames) {
        // Tạo model với cột không cho phép chỉnh sửa
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        // Thiết lập model cho bảng
        setModel(tableModel);
        
        // Tạo sorter cho model
        /*sorter = new TableRowSorter<>(tableModel);
        setRowSorter(sorter);*/
        
        // Cấu hình giao diện
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        setFillsViewportHeight(true);
        
        // Tùy chỉnh header
        getTableHeader().setReorderingAllowed(false);
        getTableHeader().setResizingAllowed(true);
        getTableHeader().setFont(new Font(getFont().getName(), Font.BOLD, 12));
        
        // Tùy chỉnh hiển thị hàng
        setRowHeight(25);
        setShowGrid(true);
        setGridColor(new Color(230, 230, 230));
        
        // Thiết lập màu xen kẽ cho hàng
        setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, 
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);
                
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(242, 242, 242));
                }                
                return c;
            }
        });
        resizeTableColumns(this);
    }

    private void resizeTableColumns(JTable table) {
        for (int i = 0; i < table.getColumnCount(); i++) {
            int width = 0;
            for (int j = 0; j < table.getRowCount(); j++) {
                TableCellRenderer renderer = table.getCellRenderer(j, i);
                Component comp = table.prepareRenderer(renderer, j, i);
                width = Math.max(width, comp.getPreferredSize().width);
            }
            table.getColumnModel().getColumn(i).setPreferredWidth(width + table.getIntercellSpacing().width);
        }
    }
   
    public void addRow(Object[] row) {
        tableModel.addRow(row);
    }
    /*public void addRows(List<Object[]> rows) {
        for (Object[] row : rows) {
            tableModel.addRow(row);
        }
    }*/

    public void removeRow(int row) {
        tableModel.removeRow(row);
    }

    public void clearTable() {
        tableModel.setRowCount(0);
    }

    public void filter(String text) {
        if (text.trim().isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
        }
    }
    
    public Object getValueAt(int row, int column) {
        return tableModel.getValueAt(convertRowIndexToModel(row), column);
    }
    
    public int getSelectedModelRow() {
        int viewRow = getSelectedRow();
        if (viewRow >= 0) {
            return convertRowIndexToModel(viewRow);
        }
        return -1;
    }
}