package ru.job4j.ood.lsp.store;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Trash extends AbstractStore {
    private static List<Food> foodList = new ArrayList<>();

    public Trash() {
        super(foodList);
    }

    @Override
    public List<Food> getfoodList() {
        return foodList;
    }

    @Override
    public boolean checkFood(Food food) {
        LocalDateTime now = LocalDateTime.now();
        return food.getExpiryDate().isBefore(now);
    }
}
