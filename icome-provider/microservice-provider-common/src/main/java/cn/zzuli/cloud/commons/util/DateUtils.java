package cn.zzuli.cloud.commons.util;

import org.apache.commons.lang3.time.FastDateFormat;

import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author fredlau
 * @date 2017/12/15/015
 */
public class DateUtils {
    /**
     * 日期格式化模型： {@value}<br>
     * 举例："2015-07-05 10:42:00"
     */
    public static final String PATTERN_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    /**
     * 日期格式化模型： {@value}<br>
     * 举例："20150705104200"
     */
    private static final String PATTERN_DATE_TIME_SIMPLY = "yyyyMMddHHmmss";
    /**
     * 日期格式化模型： {@value}<br>
     * 举例："2015-07-05 10:42:00.000"
     */
    public static final String PATTERN_DATE_TIME_MS = "yyyy-MM-dd HH:mm:ss.SSS";
    /**
     * 日期格式化模型： {@value}<br>
     * 举例："2015-07-05 10:42:00.000"
     */
    public static final String PATTERN_DATE_TIME_MS_Z = "yyyy-MM-dd HH:mm:ss.SSSZ";
    /**
     * 日期格式化模型： {@value}<br>
     * 举例："2015-07-05 10:42:00.000"
     */
    public static final String PATTERN_DATE_TIME_MS_UTC = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    /**
     * 日期格式化模型： {@value}<br>
     * 举例："2015-07-05 10:42"
     */
    public static final String PATTERN_DATE_MINUTE = "yyyy-MM-dd HH:mm";
    /**
     * 日期格式化模型： {@value}<br>
     * 举例："2015-07-05 10:42" => "2015-07-05"
     */
    public static final String PATTERN_DATE = "yyyy-MM-dd";
    /**
     * 日期格式化模型： {@value}<br>
     * 举例："2015-07-05 10:42" => "20150705"
     */
    public static final String PATTERN_DATE_F = "yyyyMMdd";
    /**
     * 日期格式化模型： {@value}<br>
     * 举例："2015-07-05 10:42:00:952" => "104200952"
     */
    public static final String PATTERN_TIME_F = "HHmmssSSS";
    /**
     * 日期格式化模型： {@value}<br>
     * 举例："2015-07-05 10:42" => "2015-07"
     */
    public static final String PATTERN_DATE_YYYY_MM = "yyyy-MM";
    /**
     * 日期格式化模型： {@value}<br>
     * 举例："2015-07-05 10:42" => "1507"
     */
    public static final String PATTERN_DATE_YYMM = "yyMM";
    /**
     * 日期格式化模型： {@value}<br>
     * 举例："2015-07-05 10:42" => "2015"
     */
    public static final String PATTERN_DATE_YYYY = "yyyy";
    /**
     * 日期格式化模型： {@value}<br>
     * 举例："2015年07月05日"
     */
    public static final String PATTERN_DATE_CALENDAR = "yyyy年MM月dd日";
    /**
     * <p>
     * FastDateFormat is a fast and thread-safe version of {@link java.text.SimpleDateFormat}.
     * </p>
     * Pattern: {@value #PATTERN_DATE_TIME}
     */
    public static final FastDateFormat FORMATTER_DATE_TIME = FastDateFormat
            .getInstance(PATTERN_DATE_TIME);
    /**
     * <p>
     * FastDateFormat is a fast and thread-safe version of {@link java.text.SimpleDateFormat}.
     * </p>
     * Pattern: {@value #PATTERN_DATE_TIME_MS}
     */
    public static final FastDateFormat FORMATTER_DATE_TIME_MS = FastDateFormat
            .getInstance(PATTERN_DATE_TIME_MS);
    /**
     * <p>
     * FastDateFormat is a fast and thread-safe version of {@link java.text.SimpleDateFormat}.
     * </p>
     * Pattern: {@value #PATTERN_DATE_TIME_MS_Z}
     */
    public static final FastDateFormat FORMATTER_DATE_TIME_MS_Z = FastDateFormat
            .getInstance(PATTERN_DATE_TIME_MS_Z);
    /**
     * <p>
     * FastDateFormat is a fast and thread-safe version of {@link java.text.SimpleDateFormat}.
     * </p>
     * Pattern: {@value #PATTERN_DATE_TIME_MS_UTC}
     */
    public static final FastDateFormat FORMATTER_DATE_TIME_MS_UTC = FastDateFormat
            .getInstance(PATTERN_DATE_TIME_MS_UTC);
    /**
     * <p>
     * FastDateFormat is a fast and thread-safe version of {@link java.text.SimpleDateFormat}.
     * </p>
     * Pattern: {@value #PATTERN_DATE_TIME_SIMPLY}
     */
    public static final FastDateFormat FORMATTER_DATE_TIME_SIMPLY = FastDateFormat
            .getInstance(PATTERN_DATE_TIME_SIMPLY);
    /**
     * <p>
     * FastDateFormat is a fast and thread-safe version of {@link java.text.SimpleDateFormat}.
     * </p>
     * Pattern: {@value #PATTERN_DATE_MINUTE}
     */
    public static final FastDateFormat FORMATTER_DATE_MINUTE = FastDateFormat
            .getInstance(PATTERN_DATE_MINUTE);
    /**
     * <p>
     * FastDateFormat is a fast and thread-safe version of {@link java.text.SimpleDateFormat}.
     * </p>
     * Pattern: {@value #PATTERN_DATE}
     */
    public static final FastDateFormat FORMATTER_DATE = FastDateFormat.getInstance(PATTERN_DATE);
    /**
     * <p>
     * FastDateFormat is a fast and thread-safe version of {@link java.text.SimpleDateFormat}.
     * </p>
     * Pattern: {@value #PATTERN_DATE_F}
     */
    public static final FastDateFormat FORMATTER_DATE_F = FastDateFormat.getInstance(PATTERN_DATE_F);

    /**
     * <p>
     * FastDateFormat is a fast and thread-safe version of {@link java.text.SimpleDateFormat}.
     * </p>
     * Pattern: {@value #PATTERN_TIME_F}
     */
    public static final FastDateFormat FORMATTER_TIME_F = FastDateFormat.getInstance(PATTERN_TIME_F);
    /**
     * <p>
     * FastDateFormat is a fast and thread-safe version of {@link java.text.SimpleDateFormat}.
     * </p>
     * Pattern: {@value #PATTERN_DATE_YYYY_MM}
     */
    public static final FastDateFormat FORMATTER_YYYY_MM = FastDateFormat
            .getInstance(PATTERN_DATE_YYYY_MM);
    /**
     * <p>
     * FastDateFormat is a fast and thread-safe version of {@link java.text.SimpleDateFormat}.
     * </p>
     * Pattern: {@value #PATTERN_DATE_YYMM}
     */
    public static final FastDateFormat FORMATTER_YYMM = FastDateFormat.getInstance(PATTERN_DATE_YYMM);
    /**
     * <p>
     * FastDateFormat is a fast and thread-safe version of {@link java.text.SimpleDateFormat}.
     * </p>
     * Pattern: {@value #PATTERN_DATE_YYYY}
     */
    public static final FastDateFormat FORMATTER_YYYY = FastDateFormat.getInstance(PATTERN_DATE_YYYY);
    /**
     * <p>
     * FastDateFormat is a fast and thread-safe version of {@link java.text.SimpleDateFormat}.
     * </p>
     * Pattern: {@value #PATTERN_DATE_CALENDAR}
     */
    public static final FastDateFormat FORMATTER_DATE_CALENDAR = FastDateFormat
            .getInstance(PATTERN_DATE_CALENDAR);

    /**
     * Converts a {@code Date} into a {@code Calendar}.
     *
     * @param date the date to convert to a Calendar
     * @return the created Calendar
     * @throws NullPointerException if null is passed in
     * @since 3.0
     */
    public static Calendar toCalendar(final Date date) {
        final Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c;
    }
}
