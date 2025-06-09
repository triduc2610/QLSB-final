package model;

import java.util.ArrayList;
import java.util.List;

public class Branch {
    private int id;
    private String name;
    private String address;
    private String phone;
    //private String managerName;
    private List<Pitch> pitches;
    private boolean active;

    public Branch(int id, String name, String address, String phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        //this.managerName = managerName;
        this.pitches = new ArrayList<>();
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    /*public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }*/

    public List<Pitch> getPitches() {
        return pitches;
    }

    public void addPitch(Pitch pitch) {
        pitches.add(pitch);
    }

    public void removePitch(Pitch pitch) {
        pitches.remove(pitch);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return id+ "- "+name + " " +address + " " + phone;
    }

    public static void main(String[] args) {
        Branch branch = new Branch(1, "Branch 1", "123 Main St", "123-456-7890");
        String test[] = branch.toString().split("-");
        System.out.println(branch);
        System.out.println(test[0]);
        System.out.println();
   
    
   
    }
}