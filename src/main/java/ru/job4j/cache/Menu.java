package ru.job4j.cache;

import java.util.Scanner;

public class Menu {

    public static final String SELECT = "Введите название файла.";

    public static final String CREATE_DIRECTORY = "Директория создана.";
    public static final String CREATE_NOTE = "Запись в кэш осуществлена.";
    public static final int SET_DIRECTORY = 1;
    public static final int LOAD_TO_CACHE = 2;
    public static final int GET_FROM_CACHE = 3;
    public static final String MENU = """
                Введите 1, чтобы указать кэшируемую директорию.
                Введите 2, чтобы загрузить содержимое файла в кэш.
                Введите 3, получить содержимое файла из кэша.
                Введите любое другое число для выхода.
            """;

    public static void main(String[] args) {
        boolean run = true;
        Scanner scanner = new Scanner(System.in);
        AbstractCache<String, String> cache = new DirFileCache("");
        while (run) {
            System.out.println(MENU);
            int userChoice = Integer.parseInt(scanner.nextLine());
            if (SET_DIRECTORY == userChoice) {
                String dir = scanner.nextLine();
                cache = new DirFileCache(dir);
                System.out.println(CREATE_DIRECTORY);
            } else if (LOAD_TO_CACHE == userChoice) {
                System.out.println(SELECT);
                String name = scanner.nextLine();
                String text = cache.load(name);
                cache.put(name, text);
                System.out.println(CREATE_NOTE);
            } else if (GET_FROM_CACHE == userChoice) {
                System.out.println(SELECT);
                String name = scanner.nextLine();
                System.out.println(cache.get(name));
            } else {
                run = false;
                System.out.println("Конец работы");
            }
        }
    }
}
