package controller;

import model.Product;
import service.ProductService;
import view.ProductListView;
import view.ProductView;

import java.util.List;

public class ProductController {
    private ProductView productView;
    private ProductService productService;
    private ProductListView productListView;

    public ProductController(ProductView productView, ProductListView productListView) {
        this.productService = new ProductService();
        this.productListView = productListView;
        this.productView = productView;
    }

    public void processSaveProduct() {
        Product productData = productView.getProductData();
        try {
            if (productService.addProduct(productData)) {
                productView.displayProductCreationSuccess(productData);
                productListView.showSuccess("Thêm sản phẩm thành công");
                refreshProduct();
            } else {
                productView.displayError("Sản phẩm đã tồn tại");
            }
        } catch (Exception e) {
            productView.displayError("Lỗi khi tạo sản phẩm: " + e.getMessage());
        }
    }

    public void processDeleteProduct() {
        String productId = productListView.getSelectProductID();
        if (productId == null || productId.isEmpty()) {
            productListView.showError("Vui lòng chọn sản phẩm để xóa");
            return;
        }
        int id = Integer.parseInt(productId);
        try {
            boolean success = productService.deleteProduct(id);
            if (success) {
                productListView.showSuccess("Xóa sản phẩm thành công");
                refreshProduct();
            } else {
                productListView.showError("Xóa sản phẩm thất bại");
            }
        } catch (Exception e) {
            productListView.showError("Lỗi khi xóa sản phẩm: " + e.getMessage());
        }
    }

    public void processUpdateProduct() {
        String productId = productListView.getSelectProductID();
        if (productId == null || productId.isEmpty()) {
            productListView.showError("Vui lòng chọn sản phẩm để cập nhật");
            return;
        }
        int id = Integer.parseInt(productId);

        try {
            Product productData = productService.getProductById(id);
            if (productData == null) {
                productListView.showError("Sản phẩm không tồn tại");
                return;
            }

            productData.setName(productListView.getUpdateName());
            productData.setCategory(productListView.getUpdateCategory());
            productData.setSellPrice(productListView.getUpdateSellPrice());
            productData.setCurrentStock(productListView.getUpdateCurrentStock());
            productData.setMinStockLevel(productListView.getUpdateMinStockLevel());
            productData.setUnit(productListView.getUpdateUnit());
            productData.setDescription(productListView.getUpdateDescription());

            boolean updated = productService.updateProduct(productData);
            if (updated) {
                productListView.showSuccess("Cập nhật sản phẩm thành công");
                productListView.showDialog(false);
            } else {
                productListView.showError("Cập nhật sản phẩm thất bại");
            }
        } catch (Exception e) {
            productListView.showError("Lỗi khi cập nhật sản phẩm: " + e.getMessage());
        }
    }

    public void processSearchProduct() {
        String keyword = productListView.getProductName();
        if (keyword == null || keyword.isBlank()) {
            productListView.showError("Vui lòng nhập từ khóa tìm kiếm");
            return;
        }
        try {
            List<Product> productList = productService.searchProductsByName(keyword);
            if (productList == null || productList.isEmpty()) {
                productListView.showError("Không tìm thấy sản phẩm với từ khóa: " + keyword);
            } else {
                productListView.displayProductList(productList);
            }
        } catch (Exception e) {
            productListView.showError("Lỗi khi tìm kiếm sản phẩm: " + e.getMessage());
        }
    }

    public void refreshProduct() {
        productListView.clearJtextFieldSearch();
        try {
            List<Product> productList = productService.getAllProducts();
            if (productList == null || productList.isEmpty()) {
                productListView.showError("Không có sản phẩm nào trong hệ thống");
            } else {
                productListView.displayProductList(productList);
            }
        } catch (Exception e) {
            productListView.showError("Lỗi khi làm mới danh sách sản phẩm: " + e.getMessage());
        }
    }

    public void clearForm() {
        productView.clear();
    }

    // Lấy sản phẩm theo ID được chọn
    public Product getSelectedProduct() {
        String productId = productListView.getSelectProductID();
        if (productId == null || productId.isEmpty()) {
            productListView.showError("Vui lòng chọn sản phẩm");
            return null;
        }
        int id = Integer.parseInt(productId);
        try {
            return productService.getProductById(id);
        } catch (Exception e) {
            productView.displayError("Lỗi khi lấy sản phẩm: " + e.getMessage());
            return null;
        }
    }

    public void displayUpdateProduct() {
        Product productData = getSelectedProduct();
        if (productData != null) {
            productListView.initDialog(productData.getName(), productData.getCategory(),
                    productData.getSellPrice(), productData.getCurrentStock(),
                    productData.getMinStockLevel(), productData.getUnit(),
                    productData.getDescription());
            productListView.showDialog(false);
        }
    }
    public void updateProduct(Product product) {
        Product productData = getSelectedProduct();
        if(productData != null){
            
            productService.updateStock(productData.getId(),  product.getCurrentStock());
        }
    }

    public void loadProductList(){
        productListView.loadProductList(productService.getAllProducts());
    }
}
