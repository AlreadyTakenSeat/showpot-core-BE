package org.example.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DateTimeUtil {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(
        "yyyy-M-d HH:mm");

    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(DATE_TIME_FORMATTER);
    }

    public static String formatDateTime(LocalDate date) {
        return  date.atStartOfDay().format(DATE_TIME_FORMATTER);
    }

    public static LocalDateTime parseDateTime(String origin) {
        return LocalDateTime.parse(origin, DATE_TIME_FORMATTER);
    }
}
