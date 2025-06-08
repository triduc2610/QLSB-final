package model;

public class Product {
    private int id;
    private String name;
    private String category;
    private double buyPrice;
    private float sellPrice;
    private int currentStock;
    private int minStockLevel;
    private String unit; 
    private String description;

    public Product(int id, String name, String category, float sellPrice, 
                 int currentStock, int minStockLevel, String unit, String description) {
        this.id = id;
        this.name = name;
        this.category = category;
        //this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.currentStock = currentStock;
        this.minStockLevel = minStockLevel;
        this.unit = unit;
        this.description = description;
    }
    public Product(){}

    // Getters và Setters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public float getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(float sellPrice) {
        this.sellPrice = sellPrice;
    }

    public int getCurrentStock() {
        return currentStock;
    }
    public void setCurrentStock(int curentStock){
        this.currentStock = curentStock;
    }

    public void updateStock(int quantity) {
        this.currentStock += quantity;
    }

    public int getMinStockLevel() {
        return minStockLevel;
    }

    public void setMinStockLevel(int minStockLevel) {
        this.minStockLevel = minStockLevel;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isLowStock() {
        return currentStock <= minStockLevel;
    }

    @Override
    public String toString() {
        return name + " (" + currentStock + " " + unit + ")";
    }
}





