package model;

//import java.util.ArrayList;
//import java.util.List;

public class Pitch {
    private int id;
    private String name;
    private String type; // 5 người, 7 người, 11 người
    private double pricePerHour;
    private String description;
    private int branchId; // ID chi nhánh
    private boolean active;

    public Pitch(int id, String name, String type, double pricePerHour, String description, int branchId,boolean active) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.pricePerHour = pricePerHour;
        this.description = description;
        this.branchId = branchId;
        this.active = active;
    }

    public Pitch() {
        this.active = true;
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(double pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    // @Override
    // public String toString() {
    //     return "Sân [" + id + "] " + " "+ name +" "+ " - " + type + "-"  + pricePerHour;
    // }
    @Override
    public String toString() {
        return "Sân [" + id + "] " + name ;
    }
    
}