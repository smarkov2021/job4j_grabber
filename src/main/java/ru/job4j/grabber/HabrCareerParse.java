package ru.job4j.grabber;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.grabber.utils.DateTimeParser;
import ru.job4j.grabber.utils.HabrCareerDateTimeParser;
import ru.job4j.grabber.utils.Post;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class HabrCareerParse implements Parse {

    private static final String SOURCE_LINK = "https://career.habr.com";

    private static final String PAGE_LINK = String.format("%s/vacancies/java_developer", SOURCE_LINK);

    private static final int NUMB_OF_PAGES = 5;

    private final DateTimeParser dateTimeParser;

    public HabrCareerParse(DateTimeParser dateTimeParser) {
        this.dateTimeParser = dateTimeParser;
    }

    @Override
    public List<Post> list(String link) throws IOException {
        List<Post> rsl = new ArrayList<>();
        Connection connection = Jsoup.connect(link);
        Document document = null;
        document = connection.get();
        Elements rows = document.select(".vacancy-card__inner");
        rows.forEach(row -> {
            Element titleElement = row.select(".vacancy-card__title").first();
            Element dateTime = row.select(".vacancy-card__date").first();
            Element linkElement = titleElement.child(0);
            String vacancyName = titleElement.text();
            String vacancyLink = String.format("%s%s", SOURCE_LINK, linkElement.attr("href"));
            LocalDateTime vacancyDate = dateTimeParser.parse(dateTime.child(0).attr("datetime"));
            String vacancyDescription = null;
            try {
                vacancyDescription = retrieveDescription(vacancyLink);
            } catch (IOException e) {
                e.printStackTrace();
            }
            int vacancyId = rsl.size() + 1;
            Post vacancy = new Post(vacancyId, vacancyName, vacancyLink, vacancyDescription, vacancyDate);
            rsl.add(vacancy);
        });
        return rsl;
    }

    private static List<String> generatePagesLinks(int number) {
        List<String> pages = new ArrayList<>();
        for (int i = 0; i <= number; i++) {
            pages.add(PAGE_LINK + "?page=" + i);
        }
        return pages;
    }

    private static String retrieveDescription(String link) throws IOException {
        List<String> rslList = new ArrayList<>();
        StringBuilder rsl = new StringBuilder();
        Connection connection = Jsoup.connect(link);
        Document document = connection.get();
        Elements rows = document.select(".faded-content__container");
        rows.forEach(row -> {
            Element titleElement = row.select(".vacancy-description__text").first();
            String vacancyDescr = titleElement.text();
            rslList.add(vacancyDescr);
        });
        for (String elem : rslList) {
            rsl.append(elem);
        }
        return rsl.toString();
    }

    public static void main(String[] args) throws IOException {
        DateTimeParser dateTimeParser = new HabrCareerDateTimeParser();
        HabrCareerParse habrCareerParse = new HabrCareerParse(dateTimeParser);
        List<Post> rsl = new ArrayList<>();
        rsl = habrCareerParse.list(PAGE_LINK);
        for (Post post : rsl) {
            System.out.println(post);
        }
    }
}