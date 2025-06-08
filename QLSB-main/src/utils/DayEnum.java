package utils;

public enum DayEnum {
    MONDAY(0, "Monday"),
    TUESDAY(1, "Tuesday"),
    WEDNESDAY(2, "Wednesday"),
    THURSDAY(3, "Thursday"),
    FRIDAY(4, "Friday"),
    SATURDAY(5, "Saturday"),
    SUNDAY(6, "Sunday");

    private final int dayNumber;
    private final String dayName;

    DayEnum(int dayNumber, String dayName) {
        this.dayNumber = dayNumber;
        this.dayName = dayName;
    }

    public int getDayNumber() {
        return dayNumber;
    }

    public String getDayName() {
        return dayName;
    }

    public static String day(int index){
        return DayEnum.values()[index].getDayName();
    }
    
}
