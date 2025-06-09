package model;

public class InvoiceItem {
    private int id;// invoice id
    
    //private String itemType; // BOOKING, PRODUCT
    private int itemId; // ID của booking hoặc product
    private int invoiceId; // ID của hóa đơn
    //private double unitPrice;
    private int quantity;
    private double total;

    public InvoiceItem(int id ,int invoiceId, int itemId, 
                      int quantity,double total) {
        
        
        this.id = id;
        this.invoiceId = invoiceId;
        //this.itemType = itemType;
        this.itemId = itemId;
        //this.unitPrice = unitPrice;
        this.quantity = quantity;
        //this.total = unitPrice * quantity;
        this.total = total;
    }

    // Getters và Setters
    public void setId(int id){
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setItemId(int itemId){
        this.itemId = itemId;
    }
    public int getItemId() {
        return itemId;
    }

    public void setInvoiceId(int invoiceId){
        this.invoiceId = invoiceId;
    }

    public int getInvoiceId(){
        return invoiceId;
    }

    /*public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }*/

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        //this.total = unitPrice * quantity;
    }

    public double getTotal() {
        return total;
    }
    public void setTotal(double total) {
        this.total = total;
    }
}