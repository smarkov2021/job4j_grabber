package ru.job4j.ood.lsp.store;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractStore implements Store {
    private List<Food> foodList = new ArrayList<>();
    private double lowerValue;
    private double upperValue;
    private double discount;

    public AbstractStore(List<Food> foodList) {
        this.foodList = foodList;
    }
    public AbstractStore(List<Food> foodList, double lowerValue, double upperValue, double discount) {
        this.foodList = foodList;
        this.lowerValue = lowerValue;
        this.upperValue = upperValue;
        this.discount = discount;
    }

    public abstract List<Food> getfoodList();

    @Override
    public void addFood(Food food) {
        food.setPrice(food.getPrice() * (1 - discount));
        foodList.add(food);
    }

    @Override
    public boolean checkFood(Food food) {
        LocalDateTime now = LocalDateTime.now();
        return food.getExpiryDate().isAfter(now)
                && (persentsOfExpired(food) >= lowerValue && persentsOfExpired(food) <= upperValue);
    }

    static double persentsOfExpired(Food food) {
        LocalDateTime now = LocalDateTime.now();
        Duration expired = Duration.between(food.getCreateDate(), now);
        Duration all = Duration.between(food.getCreateDate(), food.getExpiryDate());
        return (double) expired.toMillis() / all.toMillis();
    }

    public List<Food> getFoodList() {
        return foodList;
    }

    public void setFoodList(List<Food> foodList) {
        foodList = foodList;
    }

    public double getLowerValue() {
        return lowerValue;
    }

    public void setLowerValue(double lowerValue) {
        lowerValue = lowerValue;
    }

    public double getUpperValue() {
        return upperValue;
    }

    public void setUpperValue(double upperValue) {
        upperValue = upperValue;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        discount = discount;
    }
}
