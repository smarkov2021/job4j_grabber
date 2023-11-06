package ru.job4j.ood.srp.task;

import ru.job4j.ood.srp.task.Calculator;

public class FirstCalculator implements Calculator {
    @Override
    public int addition(int a, int b) {
        return a + b;
    }

    @Override
    public int difference(int a, int b) {
        return a - b;
    }

    @Override
    public int multiplication(int a, int b) {
        return a * b;
    }

    @Override
    public float divide(int a, int b) {
        if (b == 0) {
            throw new RuntimeException("Divide by zero");
        }
        return a / b;
    }

    @Override
    public String translateOnEnglish(int a) {
        return a == 0 ? "Zero" : "Unknown";
    }
}
