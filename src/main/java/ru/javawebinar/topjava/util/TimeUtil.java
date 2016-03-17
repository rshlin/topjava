package ru.javawebinar.topjava.util;

import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * GKislin
 * 07.01.2015.
 */
public class TimeUtil {
    public static final DateTimeFormatter DATE_TME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static boolean isBetween(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        if (startTime.compareTo(endTime)<=0)
            return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
        else
            return lt.compareTo(startTime) >= 0 || lt.compareTo(endTime) <= 0;
    }

    public static LocalTime parseTime(String toParse, LocalTime def) {
        return StringUtils.isEmpty("toParse") ? def : LocalTime.parse(toParse);
    }

    public static LocalDate parseDate(String toParse, LocalDate def) {
        return StringUtils.isEmpty("toParse") ? def : LocalDate.parse(toParse);
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TME_FORMATTER);
    }
}
