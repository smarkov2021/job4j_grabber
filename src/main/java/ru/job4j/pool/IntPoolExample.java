package ru.job4j.pool;

public class IntPoolExample {
    public static void main(String[] args) {
        Integer pool1 = new Integer(127);
        Integer pool2 = new Integer(127);
        System.out.println(pool1 == pool2);
    }
}