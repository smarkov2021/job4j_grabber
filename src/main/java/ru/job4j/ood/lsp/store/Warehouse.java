package ru.job4j.ood.lsp.store;

import java.util.ArrayList;
import java.util.List;

public class Warehouse extends AbstractStore {
    private static List<Food> foodList = new ArrayList<>();
    private static double lowerValue = 0;
    private static double upperValue = 0.25;
    private static double discount = 0;

    public Warehouse() {
        super(foodList, lowerValue, upperValue, discount);
    }

    @Override
    public List<Food> getfoodList() {
        return foodList;
    }
}
