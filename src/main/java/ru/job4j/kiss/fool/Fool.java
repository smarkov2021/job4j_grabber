package ru.job4j.kiss.fool;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.Predicate;

public class Fool {

    Map<Predicate, String> rule = new HashMap<>();

    public void init() {
        Predicate<Integer> firstCondition = el -> el % 3 == 0 && el % 5 != 0;
        Predicate<Integer> secondCondition = el -> el % 3 != 0 && el % 5 == 0;
        Predicate<Integer> thirdCondition = el -> el % 3 == 0 && el % 5 == 0;
        rule.put(firstCondition, "Fizz");
        rule.put(secondCondition, "Buzz");
        rule.put(thirdCondition, "FizzBuzz");
    }

    public boolean go(int start, String answer) {
        boolean rsl = true;
        String check = String.valueOf(start);
        for (Predicate key : rule.keySet()) {
            if (key.test(start)) {
                check = rule.get(key);
                break;
            }
        }
        if (answer != null && !Objects.equals(check, answer)) {
            System.out.println("Ошибка. Начинай снова.");
            rsl = false;
        } else if (answer == null) {
            System.out.println(check);
        }
        return rsl;
        }


    public static void main(String[] args) {
        System.out.println("Игра FizzBuzz.");
        Fool fool = new Fool();
        fool.init();
        var startAt = 1;
        var io = new Scanner(System.in);
        while (startAt < 100) {
            fool.go(startAt++, null);
            var answer = io.nextLine();
            startAt = fool.go(startAt++, answer) ? startAt : 1;
        }
    }
}