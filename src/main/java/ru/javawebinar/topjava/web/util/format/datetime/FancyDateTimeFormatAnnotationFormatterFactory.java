package ru.javawebinar.topjava.web.util.format.datetime;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;
import ru.javawebinar.topjava.web.util.format.annotation.FancyDateTimeFormat;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;

public class FancyDateTimeFormatAnnotationFormatterFactory implements AnnotationFormatterFactory<FancyDateTimeFormat> {

    private static final Set<Class<?>> FIELD_TYPES;

    static {
        FIELD_TYPES = Collections.singleton(LocalDateTime.class);
    }

    @Override
    public Set<Class<?>> getFieldTypes() {
        return FIELD_TYPES;
    }

    @Override
    public Printer<?> getPrinter(FancyDateTimeFormat annotation, Class<?> fieldType) {
        return new FancyDateTimeFormatter();
    }

    @Override
    public Parser<?> getParser(FancyDateTimeFormat annotation, Class<?> fieldType) {
        return new FancyDateTimeFormatter();
    }
}
