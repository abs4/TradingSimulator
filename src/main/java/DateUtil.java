import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    /**
     * addDays is used to move a date to the future by a given number of days
     * @param date the current date
     * @param days amount of days to go into the future
     * @return future Date
     */
    public static Date addDays(Date date, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, days);
        return c.getTime();
    }

    /**
     * stringToDate turns a String into a Date where String takes the form "yyyy-MM-dd"
     * @param date String which will be transformed to a Date
     * @return Date type of passed in String
     * @throws ParseException
     */
    public static Date StringToDate(String date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.parse(date);
    }

    /**
     * dateToString turns a Date into a String where String takes the form "yyyy-MM-dd"
     * @param date Date which will be transformed to a String
     * @return String type of passed in Date
     * @throws ParseException
     */
    public static String DateToString(Date date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }
}