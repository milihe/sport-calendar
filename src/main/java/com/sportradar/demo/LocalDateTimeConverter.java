package com.sportradar.demo;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeConverter {
    // For simplicity, I have chosen to use a Vienna timezone for the demo application.
    // In a real application this could be a user's setting.
    public final ZoneId timeZone = ZoneId.of("Europe/Vienna");

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    // This is used to convert Instant(UTC) to local time, and format it in the templating engine
    public String toLocalDateTimeText(Instant instant) {
        var localDateTime = LocalDateTime.ofInstant(instant, timeZone);
        return localDateTime.format(formatter);
    }

    // This is used to convert local time back to Instant (UTC) before storing it in database
    public Instant tryGetInstantFromLocalDateTime(String value) {
        try {
            var localDateTime = LocalDateTime.parse(value);
            return localDateTime.atZone(timeZone).toInstant();
        } catch (Exception ex) {
            return null;
        }
    }
}
