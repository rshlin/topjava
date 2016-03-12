package ru.javawebinar.topjava.util;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * GKislin
 * 07.01.2015.
 */
public class TimeUtil {
    private static final String DEFAULT_PATTERN = "yyyy-MM-ddTHH:mm";

    public static boolean isBetween(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
    }

    public static LocalDateTime parse(String dateTime) {
        return parse(dateTime,DEFAULT_PATTERN);
    }

    private static LocalDateTime parse(String dateTime, String pattern) {
        return LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern(pattern));
    }

    public static LocalDateTime now() {
        return LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);
    }
}
