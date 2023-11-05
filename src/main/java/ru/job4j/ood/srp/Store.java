package ru.job4j.ood.srp;

import java.nio.file.Path;

public interface Store {
    public void addItem(Item item);
    public void deleteItem(Item item);
    public void sellItem(Item item);
}
