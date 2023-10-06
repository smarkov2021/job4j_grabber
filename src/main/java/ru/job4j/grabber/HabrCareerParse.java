package ru.job4j.grabber;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class HabrCareerParse {

    private static final String SOURCE_LINK = "https://career.habr.com";

    private static final String PAGE_LINK = String.format("%s/vacancies/java_developer", SOURCE_LINK);

    private static final int NUMB_OF_PAGES = 5;

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

    public static void main(String[] args) {
        generatePagesLinks(NUMB_OF_PAGES).forEach(currentLink -> {
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
                    Element titleElement = row.select(".vacancy-card__title").first();
                    Element dateTime = row.select(".vacancy-card__date").first();
                    Element linkElement = titleElement.child(0);
                    String vacancyName = titleElement.text();
                    String link = String.format("%s%s", SOURCE_LINK, linkElement.attr("href"));
                    String date = dateTime.child(0).attr("datetime");
                    System.out.printf("%s %s %s %n", vacancyName, link, date);
                });
            }
        });
    }
}