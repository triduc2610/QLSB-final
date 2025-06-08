package model;

import java.util.ArrayList;
import java.util.List;
import utils.*;
public class test{
    public static void main(String[] args) {
        MonthlyBooking monthlyBooking = new MonthlyBooking();
        List<String> hehe = new ArrayList<>();
        for(int i =0;i<7;i++)
        {
            hehe.add(i, DayEnum.day(i));
        }
        monthlyBooking.setDaysOfWeek(hehe);
        for(int i=0;i<hehe.size();i++)
        {
            System.out.println(monthlyBooking.getDaysOfWeek().get(i));
        }
        
    }
}