package ru.job4j.gc;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class WeakExample {
    private int firstDigit = 1;

    private static void example() throws InterruptedException {
        Object object = new Object() {
            public String value = String.valueOf(System.currentTimeMillis());

            public String toString() {
                return String.format("%s%s", "Creation Date ", value);
            }

            @Override
            protected void finalize() throws Throwable {
                System.out.println("Removed");
            }
        };
        System.out.println(object);
        ReferenceQueue<Object> queue = new ReferenceQueue<>();
        WeakReference<Object> weak = new WeakReference<>(object, queue);
        object = null;
        System.gc();
        TimeUnit.SECONDS.sleep(3);
        System.out.println("from link " + weak.get());
        System.out.println("from queue " + queue.poll());
    }

    public static void main(String[] args) throws InterruptedException {
        example();
    }
}
