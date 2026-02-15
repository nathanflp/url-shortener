package com.nathanflp.url_shortener.utils;

import java.time.*;
import java.time.format.*;

public class DateFormatter {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm").withZone(ZoneId.of("UTC"));

    public static String formatInstant(Instant instant) {
        if (instant == null) return null;
        return FORMATTER.format(instant);
    }
}
