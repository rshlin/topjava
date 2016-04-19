package ru.javawebinar.topjava.web.util.format.datetime;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

public class FancyDateTimeFormatterTest {
    private static final Logger LOG = LoggerFactory.getLogger(FancyDateTimeFormatterTest.class);
    public static final String EXPECTED_STRING = "2015-05-30T10:00:00";

    FancyDateTimeFormatter fdtf = new FancyDateTimeFormatter();
    public static final LocalDateTime EXPECTED = LocalDateTime.of(2015, 5, 30, 10, 0);

    @Test
    public void testParse() throws Exception {
        LocalDateTime parsed = fdtf.parse(EXPECTED_STRING, Locale.getDefault());
        assertEquals(EXPECTED, parsed);
    }

    @Test
    public void testPrint() throws Exception {
        String printed = fdtf.print(EXPECTED, Locale.getDefault());
        assertEquals(EXPECTED_STRING, printed);
    }
}