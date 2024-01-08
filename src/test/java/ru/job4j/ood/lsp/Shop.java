package ru.job4j.ood.lsp;

import java.util.ArrayList;
import java.util.List;

public class Shop extends AbstractStore {
    private static  List<Food> foodList = new ArrayList<>();

    private static double lowerValue = 0.25;
    private static double upperValue = 1.00;
    private static double discount = 0.2;

    public Shop() {
        super(foodList, lowerValue, upperValue, discount);
    }

    @Override
    public List<Food> getfoodList() {
        return foodList;
    }

    @Override
    public void addFood(Food food) {
        if (persentsOfExpired(food) > (1 - 0.75)) {
        food.setPrice(food.getPrice() * (1 - discount));
        }
        foodList.add(food);
    }
}
