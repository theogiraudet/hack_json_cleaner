package fr.istic;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class Utils {

    private final static Pattern digitPattern = Pattern.compile("\\d+");

    public static Integer timestamp2year(long timestamp) {
        Instant instant = Instant.ofEpochSecond(timestamp);
        final var date = Date.from(instant);
        final var calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    public static Integer date2edition(int year) {
        return year - 1979 + 1;
    }
}
