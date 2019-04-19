package com.blogging.aps.support.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtils {

    public static String formatDate(Date date){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Instant instant = date.toInstant();
        ZoneId zoneId =ZoneId.systemDefault();
        LocalDate localDate = instant.atZone(zoneId).toLocalDate();
        return dtf.format(localDate);
    }

    public static String formatDateTime(Date date){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Instant instant = date.toInstant();
        ZoneId zoneId =ZoneId.systemDefault();
        LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
        return dtf.format(localDateTime);
    }

    public static String DateTimeToDate(String str) {
        DateTimeFormatter dtDtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDate date = LocalDate.parse(str, dtDtf);
        return date.toString();
    }
}
