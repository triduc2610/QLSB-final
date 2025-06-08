package controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.swing.JOptionPane;

import model.Customer;
import model.Invoice;
import model.InvoiceItem;
import model.Product;
import service.CustomerService;
import service.PitchService;
import service.InvoiceService;
import service.ProductService;
import view.SaleView;
import service.InvoiceItemService;
// Add import for CurrentUser if it exists in your project

public class SaleController {
    private InvoiceService invoiceService = new InvoiceService();
    private InvoiceItemService invoiceItemService = new InvoiceItemService(); // Assuming you have an InvoiceItemService class

    private ProductService productService = new ProductService(); 
    private CustomerService customerService = new CustomerService();
    private SaleView saleView; // Assuming you have a SaleView class to interact with the UI
    private PitchService pitchService = new PitchService(); // Assuming you have a PitchService class to manage pitches

    public SaleController(SaleView view) {
        this.saleView = view;
        this.invoiceService = new InvoiceService();
        this.productService = new ProductService();
        this.customerService = new CustomerService();
        this.invoiceItemService = new InvoiceItemService();
        this.pitchService = new PitchService(); 
        
    }

    public void loadProducts(int branchId) {
        List<Product> productdata = productService.getAllProducts();
        //saleView.setPitchOptions(pitchList);
        saleView.loadProductList(productdata);
        saleView.initializeData(productdata);
        saleView.loadCustomerCb(customerService.getAllCustomers());
        
        if (branchId != 0) {//neu la admin tong thi hien thi tat ca pitch
            saleView.loadPitchCb(pitchService.getPitchesByBranch(branchId));
        } else {
            saleView.loadPitchCb(pitchService.getAllPitches());
        }
    }

    public void processSaveInvoice() {
    Customer selectedCustomer = (Customer) saleView.getCustomerComboBox().getSelectedItem();
    
    List<InvoiceItem> cartItems = saleView.getCart();

    // Lấy note và discount từ view
    String note = saleView.getNote();
    double discount = 0;
    try {
        discount = Double.parseDouble(saleView.getDiscount());
        if (discount < 0) discount = 0;
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(saleView, "Giảm giá không hợp lệ!");
        return;
    }

    // if (cartItems.isEmpty()) {
    //     JOptionPane.showMessageDialog(saleView, "Giỏ hàng đang trống!");
    //     return;
    // }

    // Kiểm tra tồn kho trước khi tạo hóa đơn
    for (InvoiceItem item : cartItems) {
        Product product = productService.getProductById(item.getItemId());
        if (product == null) {
            JOptionPane.showMessageDialog(saleView, "Không tìm thấy sản phẩm với ID: " + item.getItemId());
            return;
        }
        if (product.getCurrentStock() < item.getQuantity()) {
            JOptionPane.showMessageDialog(saleView,
                "Sản phẩm '" + product.getName() + "' không đủ hàng tồn.\nHiện còn: " + product.getCurrentStock());
            return;
        }
    }

    // Tạo hóa đơn
    Invoice invoice = new Invoice();
    invoice.setCustomerId(selectedCustomer != null ? selectedCustomer.getId() : null);
    invoice.setNote(note);
    invoice.setDiscount(discount);
    invoice.setCreatedAt(LocalDateTime.now());
    invoice.setPitchId(saleView.getSelectedPitchId());   

    double subtotal = cartItems.stream().mapToDouble(InvoiceItem::getTotal).sum();
    double totalBeforeTax = subtotal - discount;
    if (totalBeforeTax < 0) totalBeforeTax = 0;

    double tax = totalBeforeTax * 0.1;
    double total = totalBeforeTax + tax;
    invoice.setTotal(total);

    try {
        // Thêm hóa đơn và lấy ID
        boolean success = invoiceService.addInvoice(invoice);
        if (!success) {
            JOptionPane.showMessageDialog(saleView, "Không thể thêm hóa đơn vào cơ sở dữ liệu.");
            return;
        }

        int invoiceId = invoice.getId();
        System.out.println("neoivid" + invoiceId); // Đảm bảo addInvoice() phải set ID này

        // Lưu các mục hóa đơn và cập nhật tồn kho
        System.out.println("cart size final: " + cartItems.size());
        for (InvoiceItem item : cartItems) {
            System.out.println("Gio hang:");
            item.setInvoiceId(invoiceId);
            if(invoiceItemService.save(item)){
                Product product = productService.getProductById(item.getItemId());
                product.setCurrentStock(product.getCurrentStock() - item.getQuantity());
                productService.updateProduct(product);
            }else {
                JOptionPane.showMessageDialog(saleView, "Không thể thêm mục hóa đơn vào cơ sở dữ liệu.");
                return;
            }
        }

        // Xóa giỏ hàng, cập nhật lại hiển thị
       
        saleView.ClearCart();
        JOptionPane.showMessageDialog(saleView, "Thanh toán thành công!");

    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(saleView, "Lỗi khi lưu hóa đơn: " + ex.getMessage());
    }
}

}



