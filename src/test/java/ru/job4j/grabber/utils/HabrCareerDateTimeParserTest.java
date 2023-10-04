package ru.job4j.grabber.utils;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class HabrCareerDateTimeParserTest {

    @Test
    public void whenTimeAfterParsingIsEqual20231003T120740() {
        String dateTime = "2023-10-03T12:07:40+03:00";
        LocalDateTime expectedDate =
                LocalDateTime.parse("2023-10-03T12:07:40", DateTimeFormatter.ISO_DATE_TIME);
        HabrCareerDateTimeParser habrCareerDateTimeParser = new HabrCareerDateTimeParser();
        LocalDateTime parsedDate = habrCareerDateTimeParser.parse(dateTime);
        assertThat(parsedDate).isEqualTo(expectedDate);
    }

    @Test
    public void whenParsedCurrentTime() {
        LocalDateTime dateTime = LocalDateTime.now();
        LocalDateTime expectedDate = LocalDateTime.parse(dateTime.toString(), DateTimeFormatter.ISO_DATE_TIME);
        String inputDate = dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        HabrCareerDateTimeParser habrCareerDateTimeParser = new HabrCareerDateTimeParser();
        LocalDateTime parsedDate = habrCareerDateTimeParser.parse(inputDate);
        assertThat(parsedDate).isEqualTo(expectedDate);
    }
}