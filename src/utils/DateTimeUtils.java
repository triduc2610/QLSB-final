package utils;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.time.Duration;

public class DateTimeUtils {
    private static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    /**
     * Format LocalDateTime to string using default format
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) return "";
        return dateTime.format(DEFAULT_FORMATTER);
    }

    public static String formatDate(LocalDateTime date) {
        if (date == null) return null;
        return date.format(DATE_FORMATTER);
    }
    public static String formatTime(LocalDateTime time) {
        if (time == null) return null;
        return time.format(TIME_FORMATTER);
    }

    public static String getDateFromDate(Date inputDate) {
        // Convert java.util.Date to String 
        if(inputDate==null) return "";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String format = formatter.format(inputDate);
        return format;
    }
    public static String getTimeFromDate(Date inputDate) {
        // Convert java.util.Date to String 
        if(inputDate == null) return "";
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String format = formatter.format(inputDate);
        return format;
    }   
    /**
     * Parse string to LocalDateTime using default format
     */
    public static LocalDateTime parseDateTime(String dateTimeStr) throws DateTimeParseException {
        if (dateTimeStr == null || dateTimeStr.trim().isEmpty()) return null;
        return LocalDateTime.parse(dateTimeStr, DEFAULT_FORMATTER);
    }
    public static LocalDateTime parseDate(String dateStr) throws DateTimeParseException {
        if (dateStr == null || dateStr.trim().isEmpty()) return null;
         LocalDate ld = LocalDate.parse(dateStr, DATE_FORMATTER);
        return LocalDateTime.of(ld, LocalDateTime.now().toLocalTime());
    }
    public static LocalDateTime parseTime(String timeStr) throws DateTimeParseException {
        if (timeStr == null || timeStr.trim().isEmpty()) return null;
        LocalTime lt = timeStr.length() > 5 ? LocalTime.parse(timeStr.substring(0, 8), TIME_FORMATTER) : LocalTime.parse(timeStr.concat(":00"), TIME_FORMATTER);
        return LocalDateTime.of(LocalDateTime.now().toLocalDate(),lt);
    }
    /**
     * Check if two time ranges overlap
     */
    public static boolean timeRangesOverlap(
            LocalDateTime start1, LocalDateTime end1,
            LocalDateTime start2, LocalDateTime end2) {
        
        return (start1.isBefore(end2) && start2.isBefore(end1));
    }
    
    /**
     * Calculate duration in hours between two times
     */
    public static double calculateHoursBetween(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) return 0;
        // Calculate duration in seconds then convert to hours
        long seconds = Duration.between(start, end).getSeconds();
        return seconds / 3600.0;
    }

    public static double calculateMinutesBetween(LocalDateTime start,LocalDateTime end){
        if (start == null || end == null) return 0;
        // Calculate duration in seconds then convert to hours
        long seconds = Duration.between(start, end).getSeconds();
        return seconds / 60.0;
    }
    /**
     * calculate days between two dates
     */
    public static int calculateDaysBetween(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) return 0;
        return Period.between(start.toLocalDate(), end.toLocalDate()).getDays();
    }
    /**
     * get days between two dates
     */
    public static List<LocalDate> getDaysBetween(LocalDateTime start, LocalDateTime end){
        LocalDate startdate = toLocalDate(start);
        LocalDate enddate = toLocalDate(end);
        List<LocalDate> totalDates = new ArrayList<>();
        while (!startdate.isAfter(enddate)) {
            totalDates.add(startdate);
            startdate = startdate.plusDays(1);
        }
        return totalDates;
    }
    public static List<LocalDate> getMatchingDays(LocalDateTime start, LocalDateTime end, List<String> daysOfWeek) {
        // Convert input daysOfWeek to uppercase for case-insensitive comparison
        List<LocalDate> result = new ArrayList<>();
        LocalDate date = start.toLocalDate();
        while (!date.isAfter(end.toLocalDate())) {
            String dayName = date.getDayOfWeek().toString();
            if (daysOfWeek.stream().anyMatch(d -> d.equalsIgnoreCase(dayName))) {
                result.add(date);
            }
            date = date.plusDays(1);
        }
        return result;
    }
    /**
     * Convert LocalDateTime to LocalDate and LocalTime
     */
    public static LocalDate toLocalDate(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.toLocalDate() : null;
    }
    public static LocalTime toLocalTime(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.toLocalTime() : null;
    }

    /**
     * convert LocalDateTime to Date
     */
    public static Date toDate(LocalDateTime dateTime) {
        return  Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}