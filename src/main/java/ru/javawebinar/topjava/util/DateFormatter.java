package ru.javawebinar.topjava.util;

import org.springframework.format.Formatter;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateFormatter implements Formatter<LocalDate> {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public LocalDate parse(String text, Locale locale) {
        return StringUtils.hasLength(text) ? null : LocalDate.parse(text, DATE_FORMATTER);
    }

    @Override
    public String print(LocalDate object, Locale locale) {
        return object.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
