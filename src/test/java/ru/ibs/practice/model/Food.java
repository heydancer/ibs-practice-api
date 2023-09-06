package ru.ibs.practice.model;

import io.qameta.allure.internal.shadowed.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Data
@Builder
public class Food {
    @JsonIgnore
    private Integer id;
    private String name;
    private FoodType type;
    private boolean exotic;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Food food = (Food) o;
        return exotic == food.exotic && Objects.equals(name, food.name) && type == food.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, exotic);
    }
}