package ru.ibs.practice.service;

import ru.ibs.practice.model.Food;
import ru.ibs.practice.model.FoodType;

import java.util.List;
import java.util.Optional;

public interface DBService {
    void addProduct(String productName, FoodType type, boolean exoticStatus);

    List<Food> findAll();

    Optional<Food> findBy(Food food);

    void removeProductByName(String name);

    boolean checkTable(String tableName);
}