package model;

import java.time.LocalDateTime;

public class Booking {
    private int id;
    private int pitchId;
    private int customerId;
    private LocalDateTime date; // Ngày đặt sân
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double totalPrice;
    private boolean isPeriodic; // Đặt định kỳ
    private String note;

    public Booking(int id, int pitchId, int customerId,LocalDateTime date, LocalDateTime startTime, LocalDateTime endTime, 
                  double totalPrice, boolean isPeriodic,String note) {
        this.id = id;
        this.pitchId = pitchId;
        this.customerId = customerId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalPrice = totalPrice;
        this.isPeriodic = isPeriodic;
        this.note = note;
    }

    public Booking()
    {
        this.isPeriodic = false;
    }

    // Getters và Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getPitchId() {
        return pitchId;
    }

    public void setPitchId(int pitchId) {
        this.pitchId = pitchId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    
    public LocalDateTime getDate(){
        return this.date;
    }
    
    public void setDate(LocalDateTime date){
        this.date = date;
    }
    
    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public boolean isPeriodic() {
        return isPeriodic;
    }

    public void setPeriodic(boolean periodic) {
        isPeriodic = periodic;
    }

    /*public String getPeriodicType() {
        return periodicType;
    }

    public void setPeriodicType(String periodicType) {
        this.periodicType = periodicType;
    }
*/
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override 
    public String toString(){
        return "Booking{" +
                "id=" + id +
                ", pitchId=" + pitchId +
                ", customerId=" + customerId +
                ", date=" + date +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", totalPrice=" + totalPrice +
                ", isPeriodic=" + isPeriodic +
                ", note='" + note + '\'' +
                '}';
    }
    // public LocalDateTime getCreatedAt() {
    //     return createdAt;
    // }

    // Phương thức kiểm tra xung đột thời gian
    // public boolean isConflictWith(Booking other) {
    //     return (this.startTime.isBefore(other.endTime) && this.endTime.isAfter(other.startTime));
    // }
    public static void main(String[] args) {
        
    }
}
