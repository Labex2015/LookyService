package mocks;

import java.util.Calendar;
import java.util.Date;

public class MockUtils {


    public static Date getTime(int day, int month, int year){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return calendar.getTime();
    }
}
