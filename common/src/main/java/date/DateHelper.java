package date;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by zhangbing on 15/9/11.
 */
public class DateHelper {

    public static String getCurrentDate(String format){

        DateTime dateTime = new DateTime();

        return dateTime.toString(format);
    }

    public static DateTime parse(String date, String format){

        DateTimeFormatter formatter = DateTimeFormat.forPattern(format);

        DateTime dateTime = DateTime.parse(date, formatter);

        return dateTime;
    }

    public static String getDateWithOffsetDay(String start, String dateFormat, int day, String dstFormat){
        checkNotNull(start, "start");
        checkNotNull(dateFormat, "dateFormat");
        checkNotNull(dstFormat, "dscFormat");

        DateTime dateTime = parse(start, dateFormat);

        return dateTime.plusDays(day).toString(dstFormat);
    }

    public static String getDateWithOffsetHour(String start, String dateFormat, int hour, String dstFormat){
        checkNotNull(start, "start");
        checkNotNull(dateFormat, "dateFormat");
        checkNotNull(dstFormat, "dscFormat");

        DateTime dateTime = parse(start, dateFormat);

        return dateTime.plusHours(hour).toString(dstFormat);
    }

    public static boolean isBefore(String compareDate, String compareFormat, String toCompareDate, String toCompareFormat){

        DateTime tmpCompareDate = parse(compareDate, compareFormat);
        DateTime tmpToCompareDate = parse(toCompareDate, toCompareFormat);

        return tmpCompareDate.isBefore(tmpToCompareDate);
    }

    public static long getCurrentTimestampInSec(){
        DateTime now = new DateTime();
        return now.getMillis()/1000;
    }

    public static long getTimestampInSec(String date, String format) throws Exception {
        DateTime dateTime = parse(date, format);
        return dateTime.getMillis() / 1000;
    }

    public static long getTimestampInSec(String date) throws Exception {
        String pattern = "yyyy-MM-dd HH:mm";
        SimpleDateFormat sd = new SimpleDateFormat(pattern, Locale.CHINA);
        return sd.parse(date).getTime() / 1000;
    }

    public static int getWeekByDate(String date, String format){
        DateTime dt = parse(date, format);
        return dt.getDayOfWeek();
    }

    public static String getDateFromLong(String format, Long millionSeconds){
//        SimpleDateFormat sf = new SimpleDateFormat(format);
//        return sf.format(time);
        DateTime dateTime = new DateTime(millionSeconds);
        return dateTime.toString(format);
    }

    /**
     * 是否为正确的日期格式
     * @param strDate
     * @param strFormat
     * @return
     */
    public static boolean isValidDate(String strDate, String strFormat) {
        boolean bFlag = false;
        SimpleDateFormat sdf = new SimpleDateFormat(strFormat);
        sdf.setLenient(false);
        if (strDate != null && strDate.length() > 0) {
            try {
                Date dtCheck = (sdf.parse(strDate));
                String strCheck = sdf.format(dtCheck);
                if (strDate.equals(strCheck)) {
                    bFlag = true;
                } else {
                    bFlag = false;
                }
            } catch (Exception e) {
                bFlag = false;
            }
        }
        sdf = null;
        return bFlag;
    }
}
