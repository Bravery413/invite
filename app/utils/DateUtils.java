package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class DateUtils {

	public static final String PATTERN_STANDARD08W = "yyyyMMdd";
	public static final String PATTERN_STANDARD12W = "yyyyMMddHHmm";
	public static final String PATTERN_STANDARD14W = "yyyyMMddHHmmss";
	public static final String PATTERN_STANDARD17W = "yyyyMMddHHmmssSSS";

	public static final String PATTERN_STANDARD10H = "yyyy-MM-dd";
	public static final String PATTERN_STANDARD16H = "yyyy-MM-dd HH:mm";
	public static final String PATTERN_STANDARD19H = "yyyy-MM-dd HH:mm:ss";

	public static final String PATTERN_STANDARD10X = "yyyy/MM/dd";
	public static final String PATTERN_STANDARD16X = "yyyy/MM/dd HH:mm";
	public static final String PATTERN_STANDARD19X = "yyyy/MM/dd HH:mm:ss";

	public static final String PATTERN_STANDARD05H = "HH:mm";
	public static final String PATTERN_STANDARD02H = "HH";

	private DateUtils() {
	}

	public static String getTimestamp() {
		return String.valueOf(System.currentTimeMillis());
	}

	public static String getRandstr() {
		return String.valueOf(new Random().nextInt(1000000));
	}

	public static Long getDate(String timeStr, String pattern) {
		try {
			if (StringUtils.isNullOrEmpty(timeStr)) {
				return null;
			}
			if (StringUtils.isNullOrEmpty(pattern)) {
				pattern = "yyyy-MM-dd HH:mm";
			}
			SimpleDateFormat sdf = new SimpleDateFormat();
			sdf.applyPattern(pattern);
			return sdf.parse(timeStr).getTime();
		} catch (Exception e) {
		}
		return null;
	}

	public static int calcAge(long bornTimestamp) {
		Calendar now = Calendar.getInstance();
		Calendar born = Calendar.getInstance();
		born.setTimeInMillis(bornTimestamp);
		int age = -1;
		if (now.after(born)) {
			age = now.get(Calendar.YEAR) - born.get(Calendar.YEAR);
			if (now.get(Calendar.DAY_OF_YEAR) < born.get(Calendar.DAY_OF_YEAR)) {
				age -= 1;
				if (age < 0) {
				    age = 0;
                }
			}
		}
		return age;
	}

	/**
	 * 获取周几（一-日:0-6）
	 *
	 * @param date
	 * @return
	 */
	public static int getDayofweek(long date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(date));
		int weekIdx = (cal.get(Calendar.DAY_OF_WEEK) + 5) % 7;
		return weekIdx;
	}

	public static Date getDateBefore(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
		return now.getTime();
	}

	public static long getDateBefore(long timestamp, int day) {
		Calendar now = Calendar.getInstance();
		now.setTimeInMillis(timestamp);
		now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
		return now.getTimeInMillis();
	}

	public static Date getDateAfter(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
		return now.getTime();
	}

	public static long getDateAfter(long timestamp, int day) {
		Calendar now = Calendar.getInstance();
		now.setTimeInMillis(timestamp);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
		return now.getTimeInMillis();
	}

	public static long getDateTimestamp(long timestamp) {
		Calendar date = Calendar.getInstance();
		date.setTimeInMillis(timestamp);
		date.set(Calendar.MILLISECOND, 0);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.HOUR_OF_DAY, 0);
		return date.getTimeInMillis();
	}

	public static long getYearTimestamp(long timestamp) {
		Calendar date = Calendar.getInstance();
		date.setTimeInMillis(timestamp);
		date.set(Calendar.MONTH, 0);
		date.set(Calendar.DAY_OF_MONTH, 1);
		date.set(Calendar.MILLISECOND, 0);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.HOUR_OF_DAY, 0);
		return date.getTimeInMillis();
	}

	public static long getMonthTimestamp(long timestamp) {
		Calendar date = Calendar.getInstance();
		date.setTimeInMillis(timestamp);
		date.set(Calendar.DAY_OF_MONTH, 1);
		date.set(Calendar.MILLISECOND, 0);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.HOUR_OF_DAY, 0);
		return date.getTimeInMillis();
	}

	public static long getMonthAfter(long timestamp, int month) {
		Calendar now = Calendar.getInstance();
		now.setTimeInMillis(timestamp);
		now.set(Calendar.MONTH, now.get(Calendar.MONTH) + month);
		return now.getTimeInMillis();
	}

	public static long getMonthBefore(long timestamp, int month) {
		Calendar now = Calendar.getInstance();
		now.setTimeInMillis(timestamp);
		now.set(Calendar.MONTH, now.get(Calendar.MONTH) - month);
		return now.getTimeInMillis();
	}

	public static long getYearAfter(long timestamp, int year) {
		Calendar now = Calendar.getInstance();
		now.setTimeInMillis(timestamp);
		now.set(Calendar.YEAR, now.get(Calendar.YEAR) + year);
		return now.getTimeInMillis();
	}

	public static long getYearBefore(long timestamp, int year) {
		Calendar now = Calendar.getInstance();
		now.setTimeInMillis(timestamp);
		now.set(Calendar.YEAR, now.get(Calendar.YEAR) - year);
		return now.getTimeInMillis();
	}

	public static String formatTimestamp(String format, long ts) {
		SimpleDateFormat simpleDateFormat = null;
		String dateStr = null;
		try {
			simpleDateFormat = new SimpleDateFormat(format);
			dateStr = simpleDateFormat.format(new Date(ts));
		} catch (Exception e) {
		}
		return dateStr;
	}

	public static String getWeekDay(Long ts) {
		Calendar cl = Calendar.getInstance();
		cl.setTimeInMillis(ts);
		int dayForWeek = 0;
		int dayOfWeek = cl.get(Calendar.DAY_OF_WEEK);
		if (dayOfWeek == 1) {
			dayForWeek = 6;
		} else {
			dayForWeek = dayOfWeek - 2;
		}
		String[] weekDay = {"周一", "周二", "周三", "周四", "周五", "周六", "周日"};
		return weekDay[dayForWeek];
	}
	
	public static long getMonday(long time) {
		time = getDateTimestamp(time);
		Calendar cd = Calendar.getInstance();
		cd.setTimeInMillis(time);
		cd.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		
		return cd.getTimeInMillis();
	}
	
	public static int getWeekOfDate(Date dt) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        
        if (w < 0) {
            w = 0;
        }
        
        return w;
    }
	
	/**
	 * 获取月份的天数
	 * @param date
	 * @return
	 * @throws ParseException 
	 */
	public static int getDaysOfMonth(String date) throws ParseException {  //date的格式是（yyyyMMdd）
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(format.parse(date));  
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);  
    } 
	
	public static int getDaysOfMonth(long date) throws ParseException {
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(new Date(date));  
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);  
    } 
	
	/**   
	 * 得到本月的第一天   
	 */ 
	 public static Long getMonthFirstDay(long timestamp) {     
	    Calendar now = Calendar.getInstance();
	    now.setTimeInMillis(timestamp);  
	    now.set(Calendar.DAY_OF_MONTH, now     
	            .getActualMinimum(Calendar.DAY_OF_MONTH));     
	    
	    return now.getTimeInMillis();     
	}     
	    
	/**   
	 * 得到本月的最后一天   
	 */    
	public static Long getMonthLastDay(long timestamp) {     
	    Calendar now = Calendar.getInstance();
	    now.setTimeInMillis(timestamp);
	    now.set(Calendar.DAY_OF_MONTH, now
	            .getActualMaximum(Calendar.DAY_OF_MONTH));     
	    return now.getTimeInMillis();
	}

    /**
     * 获取剩余天数
     */
	public static Integer getLeftDays(long timestamp) {
	    long now = System.currentTimeMillis();
        double left = (timestamp - now) / (86400 * 1000.0);
        if (left < 0) {
            left = 0;
        }
        return (int)(Math.ceil(left));
    }
	
	public static void main(String[] args) {
		System.out.println(getDateTimestamp(getDateAfter(System.currentTimeMillis(), 1)));
	}
}
