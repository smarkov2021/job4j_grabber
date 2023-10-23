package ru.job4j.gc;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

public class ExampleSoft {
    private static void defineSize(List list) {
        List<SoftReference<Object>> objects = new ArrayList<>();
        objects.add(new SoftReference<Object>(new Object() {
            public int value = list.size();

            public String toString() {
                return String.valueOf(value);
            }

            @Override
                protected void finalize() throws Throwable {
                    System.out.println("Object removed!");
                }
    }));
        System.gc();
        Object obj = objects.get(0).get();
        if (obj != null) {
            System.out.println(obj.toString());
            System.out.println("Got from cache");
        } else {
            System.out.println(list.size());
            System.out.println("Computed Value");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        List<Integer> list = List.of(1, 2, 3, 4, 5);
        defineSize(list);
    }
}