package ru.javawebinar.topjava.web.util.format.datetime;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Locale;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;

public class FancyDateTimeFormatter implements Formatter<LocalDateTime> {

    @Override
    public LocalDateTime parse(String text, Locale locale) throws ParseException {
        return ISO_LOCAL_DATE_TIME.parse(text, LocalDateTime::from);
    }

    @Override
    public String print(LocalDateTime ldt, Locale locale) {
        return ISO_LOCAL_DATE_TIME.format(ldt);
    }
}
