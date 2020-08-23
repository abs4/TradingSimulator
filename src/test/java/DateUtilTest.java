import static org.junit.Assert.*;

import org.junit.Test;

import java.text.ParseException;
import java.util.Date;

public class DateUtilTest {

    @Test
    public void stringToDateTest() throws ParseException {
        String d = "2020-07-01";
        assertEquals("Wed Jul 01 00:00:00 CDT 2020", DateUtil.StringToDate(d).toString());
    }

    @Test
    public void dateToStringTest() throws ParseException {
        Date d = new Date(1000000000);
        assertEquals("1970-01-12", DateUtil.DateToString(d));
    }

    @Test
    public void addDaysWeekDayTest() throws ParseException {
        Date d = new Date(1000000000);
        assertEquals("Mon Jan 12 07:46:40 CST 1970", d.toString());
        Date d2 = DateUtil.addDays(d,2);
        assertEquals("Wed Jan 14 07:46:40 CST 1970", d2.toString());
    }

    @Test
    public void addDaysMonthTest() throws ParseException {
        Date d = new Date(1000000000);
        assertEquals("Mon Jan 12 07:46:40 CST 1970", d.toString());
        Date d2 = DateUtil.addDays(d,25);
        assertEquals("Fri Feb 06 07:46:40 CST 1970", d2.toString());
    }

    @Test
    public void addDaysYearTest() throws ParseException {
        Date d = new Date(1000000000);
        assertEquals("Mon Jan 12 07:46:40 CST 1970", d.toString());
        Date d2 = DateUtil.addDays(d,365);
        assertEquals("Tue Jan 12 07:46:40 CST 1971", d2.toString());
    }
}