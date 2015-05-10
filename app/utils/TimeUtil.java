package utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yin on 15-5-9.
 */
public class TimeUtil {

    public static String getCurrentDate(){

        DateFormat dateFormat = new SimpleDateFormat("/MM/dd/yy");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String getCurrentTime(){

        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
