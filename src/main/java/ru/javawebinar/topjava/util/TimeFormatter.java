package ru.javawebinar.topjava.util;

import org.springframework.format.Formatter;
import org.springframework.util.StringUtils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class TimeFormatter implements Formatter<LocalTime> {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    @Override
    public LocalTime parse(String text, Locale locale) {
        return StringUtils.hasLength(text) ? null : LocalTime.parse(text, TIME_FORMATTER);
    }

    @Override
    public String print(LocalTime object, Locale locale) {
        return object.format(DateTimeFormatter.ISO_LOCAL_TIME);
    }
}
