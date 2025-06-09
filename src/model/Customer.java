package model;
import java.time.LocalDateTime;

public class Customer {
    private int id;
    private String name;
    private String phone;
    private String email;
    private String address;
    private double totalSpent;
    private LocalDateTime createdAt;

    public Customer(int id, String name, String phone, String email, String address) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.totalSpent = 0;
        this.createdAt = LocalDateTime.now();
    }

    public Customer() {
        this.totalSpent = 0;
        this.createdAt = LocalDateTime.now();
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getTotalSpent() {
        return totalSpent;
    }

    public void addToTotalSpent(double amount) {
        this.totalSpent += amount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return this.name + " ( " + this.phone + " ) ";
    }
}
