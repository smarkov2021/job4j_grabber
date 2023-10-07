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

    private Post createPost(Element row, int currentIndex) {
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
        return new Post(currentIndex, vacancyName, vacancyLink, vacancyDescription, vacancyDate);
    }

    @Override
    public List<Post> list(String link) {
        List<Post> rsl = new ArrayList<>();
        generatePagesLinks().stream().forEach(currentLink -> {
            Connection connection = Jsoup.connect(currentLink);
            Document document = null;
            try {
                document = connection.get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (document != null) {
            Elements rows = document.select(".vacancy-card__inner");
            rows.forEach(row -> {
                    rsl.add(createPost(row, rsl.size() + 1));
                });
            }
        });
        return rsl;
    }

    private List<String> generatePagesLinks() {
        List<String> pages = new ArrayList<>();
        for (int i = 0; i <= NUMB_OF_PAGES; i++) {
            StringBuilder elem = new StringBuilder();
            elem.append(PAGE_LINK)
                .append("?page=")
                .append(i);
            pages.add(elem.toString());
        }
        return pages;
    }

    private String retrieveDescription(String link) throws IOException {
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
        for (Post post : habrCareerParse.list(PAGE_LINK)) {
            System.out.println(post);
        }
    }
}