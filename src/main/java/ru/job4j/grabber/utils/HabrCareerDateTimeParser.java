package ru.job4j.grabber.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class HabrCareerDateTimeParser implements DateTimeParser {

    @Override
    public LocalDateTime parse(String parse) {
        LocalDateTime date = LocalDateTime.parse(parse, DateTimeFormatter.ISO_DATE_TIME);
        String text = date.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        LocalDateTime parsedDate = LocalDateTime.parse(text, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        return parsedDate;
    }
}