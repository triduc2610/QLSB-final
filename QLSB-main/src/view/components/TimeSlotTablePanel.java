package view.components;

import javax.swing.*;
import java.awt.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import java.util.List;
import java.util.Map;

import java.util.ArrayList;


public class TimeSlotTablePanel extends JPanel {
    public interface SlotClickListener {
        //void onSlotClick(LocalDate date, LocalTime time);
        void onSlotClick();
    }
    private LocalDate selectedDate;
    private LocalTime selectedStartTime;
    private LocalTime selectedEndTime;
    private List<LocalDate> weekDates;
    private List<LocalTime> timeSlots;
    private SlotClickListener listener;
    //private JButton btn;

    public final LocalTime START_TIME = LocalTime.of(5, 0);
    public final LocalTime END_TIME = LocalTime.of(23, 0);
    public final int SLOT_MINUTES = 30;

    // Thêm biến lưu button và trạng thái chọn
    private JButton[][] slotButtons; // [row][col]
    private boolean isSelecting = false;
    private int selectCol = -1;
    private int startRow = -1, endRow = -1;
    private List<Map<String,Object>> data;

    public TimeSlotTablePanel(List<LocalDate> weekDates, List<LocalTime> timeSlots, List<Map<String,Object>> data) {
        this.weekDates = weekDates;
        this.timeSlots = timeSlots;
        this.data = data;
        setLayout(new GridLayout(timeSlots.size() + 1, weekDates.size() + 1, 2, 2));
        //System.out.println("timeSlots size: " + timeSlots.size());//37
        //System.out.println("weekDates size: " + weekDates.size());//7
        renderTable();
    }
    public TimeSlotTablePanel(){
        setLayout(new GridLayout(38, 8, 2, 2));
        //renderTable();
    }

    public void updateData(List<LocalDate> weekDates, List<LocalTime> timeSlots, List<Map<String,Object>> data) {
        this.weekDates = weekDates;
        this.timeSlots = timeSlots;
        this.data = data;
        removeAll();
        renderTable();
        revalidate();
        repaint();
    }

    private void renderTable() {
        int rowHeight = 40; // default height for each time row
        slotButtons = new JButton[timeSlots.size()][weekDates.size()];
        // Header row: empty cell + days of week
        JLabel emptyHeader = new JLabel("", SwingConstants.CENTER);
        emptyHeader.setPreferredSize(new Dimension(60, rowHeight));
        add(emptyHeader);
        for (LocalDate date : weekDates) {
            String dayLabel = (date.getDayOfWeek() == DayOfWeek.SUNDAY ? "Chủ nhật" : "Thứ " + (date.getDayOfWeek().getValue()+1));
            JLabel dayHeader = new JLabel(dayLabel, SwingConstants.CENTER);
            dayHeader.setPreferredSize(new Dimension(60, rowHeight));
            add(dayHeader);
        }
           
        // Rows for each time slot
        // 1st slot is 5:00
        for (int i = 0; i < timeSlots.size(); i++) {
            LocalTime slot = timeSlots.get(i);
            JLabel timeLabel = new JLabel(slot.toString(), SwingConstants.CENTER);
            timeLabel.setPreferredSize(new Dimension(60, rowHeight));
            add(timeLabel);
            //lấy từng ngày trong weekDates sau đó kiểm tra từng bookings 1 
            //lọc danh sách các booking từ bookings có cùng ngày vừa lấy
            //so sánh từng bookings xem slot có nằm trong khoảng (startime-endtime) 
            //vd slot là  18:00:00 trong khoảng (18:00:00-20:00:00) thi se break và tô đậm btn[i,j] đó
            for (int j = 0; j < weekDates.size(); j++) {
                LocalDate date = weekDates.get(j);
                JButton btn = new JButton();
                btn.setPreferredSize(new Dimension(60, rowHeight));
                btn.setMargin(new Insets(0,0,0,0));
                btn.setFont(new Font("Arial", Font.PLAIN, 10));
                btn.setBackground(Color.WHITE);
                btn.setOpaque(true);
                btn.setEnabled(false);
                btn.setText("");
                // Kiểm tra slot đã đặt chưa
                int found = 0;
                
                
                for (Map<String,Object> b : data) {
                    if (b.get("date") != null && LocalDate.parse((String)b.get("date")).equals(date)) {
                        LocalTime start = ((LocalDateTime)b.get("startTime")).toLocalTime();
                        LocalTime end = ((LocalDateTime)b.get("endTime")).toLocalTime();
                        if (!slot.isBefore(start) && slot.isBefore(end.plusMinutes(5))) {
                            found = 1;
                            break;
                        }
                    }
                }
                if (found != 0) {
                            btn.setText("Đã đặt");
                            btn.setBackground(new Color(255, 200, 200));
                            found = 0;
                } else {
                            btn.setEnabled(true);
                            btn.setBackground(new Color(220, 255, 220));//set background color for available slot
                            // Gán MouseListener cho từng button
                            final int row = i;
                            final int col = j;
                            btn.addMouseListener(new java.awt.event.MouseAdapter() {
                                @Override
                                public void mousePressed(java.awt.event.MouseEvent e) {
                                    isSelecting = true;
                                    selectCol = col;//thay giá trị bán đầu cho cột = cột đang chọn
                                    startRow = row;//thay giá trị bán đầu cho startrow = row đang chọn
                                    endRow = row;//thay giá trị bán đầu cho endrow = row đang chọn
                                    highlightSelectedSlots();
                                    updateSelectedTimeRange();
                                }
                                @Override
                                public void mouseReleased(java.awt.event.MouseEvent e) {
                                    isSelecting = false;//dừng chọn
                                    if (listener != null) listener.onSlotClick();
                                    updateSelectedTimeRange();
                                }
                                @Override
                                public void mouseEntered(java.awt.event.MouseEvent e) {// tô màu những ô mà chuột đi tới và gắn giá trị endrow
                                    if (isSelecting && selectCol == col) {
                                        endRow = row;
                                        highlightSelectedSlots();
                                        updateSelectedTimeRange();//di chuột tới đâu sẽ cập nhật tới đó
                                    }
                                }
                            });
                        }
                add(btn);
                slotButtons[i][j] = btn;
            }
        }
    }

    // Highlight các button được chọn
    private void highlightSelectedSlots() {
        for (int i = 0; i < slotButtons.length; i++) {//ensure all button refrest when press new column
            for (int j = 0; j < slotButtons[0].length; j++) {
                JButton btn = slotButtons[i][j];
                if (btn.isEnabled()) {
                    btn.setBackground(new Color(220, 255, 220));

                }
            }
        }
        if (selectCol >= 0 && startRow >= 0 && endRow >= 0) {//tô đậm các row ở giữa minrow và maxrow
            int minRow = Math.min(startRow, endRow);
            int maxRow = Math.max(startRow, endRow);
            for (int i = minRow; i <= maxRow; i++) {
                JButton btn = slotButtons[i][selectCol];
                if (btn.isEnabled()) {
                    btn.setBackground(new Color(100, 200, 255));
                    }
            }
        }
    }

    // Cập nhật selectedDate, selectedStartTime, selectedEndTime
    private void updateSelectedTimeRange() {
        System.out.println("col :"+selectCol);
        System.out.println("startRow :"+startRow);
        System.out.println("endRow :"+endRow);
        if (selectCol >= 0 && startRow >= 0 && endRow >= 0) {
            int minRow = Math.min(startRow, endRow);//double check to be the true minRow
            int maxRow = Math.max(startRow, endRow);
            this.selectedDate = weekDates.get(selectCol);
            this.selectedStartTime = timeSlots.get(minRow);
            this.selectedEndTime = (timeSlots.get(minRow).equals(timeSlots.get(maxRow))) ? 
            timeSlots.get(maxRow).plusMinutes(30) : timeSlots.get(maxRow);
        }
    }
    
    public void setSlotClickListener(SlotClickListener listener) {
        this.listener = listener;
    }

    public LocalDate getSelectedDate() {
        return this.selectedDate;
    }

    public LocalTime getSelectedStartTime() {
        return this.selectedStartTime;
    }

    public LocalTime getSelectedEndTime() {
        return this.selectedEndTime;
    }

    // Tiện ích tạo danh sách khung giờ 30 phút từ 5:00 đến 23:00
    public static List<LocalTime> defaultTimeSlots() {
        List<LocalTime> slots = new ArrayList<>();
        LocalTime t = LocalTime.of(5, 0,0);
        while (!t.isAfter(LocalTime.of(23, 0,0))) {
            slots.add(t);
            t = t.plusMinutes(30);
        }
        return slots;
    }
}
