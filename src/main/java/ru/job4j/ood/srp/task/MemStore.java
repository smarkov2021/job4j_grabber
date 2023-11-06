package ru.job4j.ood.srp.task;

import java.util.ArrayList;
import java.util.List;

public class MemStore implements Store {

    private List<Item> listStore = new ArrayList<>();

    @Override
    public void addItem(Item item) {
        listStore.add(item);
    }

    @Override
    public void deleteItem(Item item) {
        listStore.remove(item);
    }

    @Override
    public void sellItem(Item item) {
        listStore.remove(item);
    }
}
