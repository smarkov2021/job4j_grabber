package ru.job4j.ood.srp.task;

import ru.job4j.ood.srp.task.Item;

public interface Store {
    public void addItem(Item item);
    public void deleteItem(Item item);
    public void sellItem(Item item);
}
