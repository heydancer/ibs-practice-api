package ru.ibs.practice.service;

import ru.ibs.practice.model.Food;

import java.util.List;

public interface APIService {
    List<Food> getAll();

    void add(Food food);

    void dataReset();
}