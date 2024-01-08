package ru.job4j.ood.lsp;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.job4j.ood.lsp.store.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ControlQualityTest {
    @Test
    public void whenFoodMustReplacedInTrash() {
        List<Store> listStorage = new ArrayList<>();
        List<Food> listFood = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        AbstractStore shop = new Shop();
        AbstractStore warehouse = new Warehouse();
        AbstractStore trash = new Trash();
        listStorage.add(shop);
        listStorage.add(warehouse);
        listStorage.add(trash);
        Food forTrash = new Food("For Trash", now.minusDays(100), now.minusDays(1), 100.00, 0.0);
        listFood.add(forTrash);
        ControlQuality controlQuality = new ControlQuality(listStorage, listFood);
        controlQuality.sort();
        Assertions.assertThat(trash.getFoodList().size()).isEqualTo(1);
        Assertions.assertThat(trash.getFoodList().contains(forTrash)).isTrue();
    }

    @Test
    public void whenFoodMustReplacedInShop() {
        List<Store> listStorage = new ArrayList<>();
        List<Food> listFood = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        AbstractStore shop = new Shop();
        AbstractStore warehouse = new Warehouse();
        AbstractStore trash = new Trash();
        listStorage.add(shop);
        listStorage.add(warehouse);
        listStorage.add(trash);
        Food forShop = new Food("For Shop", now.minusDays(1), now.plusDays(1), 100.00, 0.0);
        listFood.add(forShop);
        ControlQuality controlQuality = new ControlQuality(listStorage, listFood);
        controlQuality.sort();
        Assertions.assertThat(warehouse.getFoodList().size()).isEqualTo(0);
        Assertions.assertThat(trash.getFoodList().size()).isEqualTo(0);
        Assertions.assertThat(shop.getFoodList().size()).isEqualTo(1);
        Assertions.assertThat(shop.getFoodList().contains(forShop)).isTrue();
    }

    @Test
    public void whenFoodMustReplacedInShopWithDiscont() {
        List<Store> listStorage = new ArrayList<>();
        List<Food> listFood = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        AbstractStore shop = new Shop();
        AbstractStore warehouse = new Warehouse();
        AbstractStore trash = new Trash();
        listStorage.add(shop);
        listStorage.add(warehouse);
        listStorage.add(trash);
        Food forShop = new Food("For Shop", now.minusDays(100), now.plusDays(1), 100.00, 0.0);
        listFood.add(forShop);
        ControlQuality controlQuality = new ControlQuality(listStorage, listFood);
        controlQuality.sort();
        Assertions.assertThat(shop.getFoodList().contains(forShop)).isTrue();
        Assertions.assertThat(forShop.getPrice()).isEqualTo(80.0);
    }

    @Test
    public void whenFoodMustReplacedInWarehouse() {
        List<Store> listStorage = new ArrayList<>();
        List<Food> listFood = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        AbstractStore shop = new Shop();
        AbstractStore warehouse = new Warehouse();
        AbstractStore trash = new Trash();
        listStorage.add(shop);
        listStorage.add(warehouse);
        listStorage.add(trash);
        Food forWarehouse =
                new Food("For Warehouse", now.minusDays(1), now.plusDays(100), 100.00, 0.0);
        listFood.add(forWarehouse);
        ControlQuality controlQuality = new ControlQuality(listStorage, listFood);
        controlQuality.sort();
        Assertions.assertThat(warehouse.getFoodList().size()).isEqualTo(1);
        Assertions.assertThat(warehouse.getFoodList().contains(forWarehouse)).isTrue();
        Assertions.assertThat(forWarehouse.getPrice()).isEqualTo(100.0);
    }
}