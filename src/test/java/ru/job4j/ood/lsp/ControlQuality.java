package ru.job4j.ood.lsp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ControlQuality {
    private List<Store> listStorage = new ArrayList<>();
    private List<Food> listFood = new ArrayList<>();

    public ControlQuality(List<Store> listStorage, List<Food> listFood) {
        this.listStorage = listStorage;
        this.listFood = listFood;
    }

    public void sort() {
        listFood.forEach(e ->
            listStorage.stream().filter(el -> el.checkFood(e)).forEach(el -> el.addFood(e)));
    }

    public List<Store> getListStorage() {
        return listStorage;
    }

    public void setListStorage(List<Store> listStorage) {
        listStorage = listStorage;
    }

    public List<Food> getListFood() {
        return listFood;
    }

    public void setListFood(List<Food> listFood) {
        listFood = listFood;
    }
}
