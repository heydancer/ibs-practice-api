package ru.ibs.practice.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.ibs.practice.model.Food;
import ru.ibs.practice.model.FoodType;
import ru.ibs.practice.tests.general.BaseTest;

import java.util.List;
import java.util.Optional;

import static ru.ibs.practice.model.FoodType.FRUIT;
import static ru.ibs.practice.model.FoodType.VEGETABLE;

@Owner("Ustiantcev Aleksandr")
@DisplayName("Проверка добавления продуктов API")
public class APITest extends BaseTest {
    private final static String EXOTIC_FOOD = "Дуриан";
    private final static String NON_EXOTIC_FOOD = "Морковь";

    @AfterEach
    public void dataReset() {
        apiService.dataReset();
    }

    @Test
    @DisplayName("Добавление и получение товаров")
    @Description("Проверка добавления и получения списка товаров через API")
    public void testShouldReturnFoodsAfterAdding() {
        Food food = createTestFood(EXOTIC_FOOD, VEGETABLE, true);

        List<Food> oldApiFoodList = apiService.getAll();
        List<Food> oldDbFoodList = dbService.findAll();
        int oldApiFoodListSize = oldApiFoodList.size();
        int oldDbFoodListSize = oldDbFoodList.size();

        Assertions.assertEquals(oldApiFoodList, oldDbFoodList,
                "Объекты должны быть равны");
        Assertions.assertEquals(oldApiFoodListSize, oldDbFoodListSize,
                "Размеры списка товаров из БД и API должны совпадать");

        apiService.add(food);

        List<Food> newApiFoodList = apiService.getAll();
        List<Food> newDbFoodList = dbService.findAll();
        int newApiFoodListSize = newApiFoodList.size();
        int newDbFoodListSize = newDbFoodList.size();

        Assertions.assertNotEquals(oldApiFoodList, newApiFoodList,
                "Объекты не должны быть равны");
        Assertions.assertTrue(oldApiFoodListSize < newApiFoodListSize,
               "Размер нового списка API после добавления товара должен быть больше старого");
        Assertions.assertNotEquals(oldDbFoodList, newDbFoodList,
                "Объекты не должны быть равны");
        Assertions.assertTrue(oldDbFoodListSize < newDbFoodListSize,
                "Размер нового списка БД после добавления товара должен быть больше старого");
    }

    @Test
    @DisplayName("Добавление Овоща, экзотический = false")
    @Description("Проверка возможности добавить продукт с типом 'Овощ' не экзотический")
    public void testAddingNonExoticVegetable() {
        Food food = createTestFood(NON_EXOTIC_FOOD, VEGETABLE, false);
        Optional<Food> foodOptional;

        foodOptional = dbService.findByName(NON_EXOTIC_FOOD);

        Assertions.assertFalse(foodOptional.isPresent(),
                "Продукта: " + food + " не должно быть в базе данных");

        apiService.add(food);

        foodOptional = dbService.findByName(NON_EXOTIC_FOOD);

        Assertions.assertTrue(foodOptional.isPresent(),
                "Продукт: " + food + " должен быть в базе данных");
        Assertions.assertNotNull(foodOptional.get().getId(),
                "ID продукта не может быть null");
        Assertions.assertEquals(foodOptional.get().getName(), NON_EXOTIC_FOOD,
                String.format("Наименование продукта %s не соответствует наименованию %s",
                        foodOptional.get().getName(), NON_EXOTIC_FOOD));
        Assertions.assertEquals(foodOptional.get().getType(), VEGETABLE,
                String.format("Тип продукта не соответствует типу %s", VEGETABLE));
        Assertions.assertFalse(foodOptional.get().isExotic(),
                "Состояние продукта должно быть false");
    }

    @Test
    @DisplayName("Добавление фрукта, экзотический = true")
    @Description("Проверка возможности добавить продукт с типом 'Фрукт' экзотический")
    public void testAddingExoticFruit() {
        Food food = createTestFood(EXOTIC_FOOD, FRUIT, true);
        Optional<Food> foodOptional;

        foodOptional = dbService.findByName(EXOTIC_FOOD);

        Assertions.assertFalse(foodOptional.isPresent(),
                "Продукта: " + food + " не должно быть в базе данных");

        apiService.add(food);

        foodOptional = dbService.findByName(EXOTIC_FOOD);

        Assertions.assertTrue(foodOptional.isPresent(),
                "Продукт: " + food + " должен быть в базе данных");
        Assertions.assertNotNull(foodOptional.get().getId(),
                "ID продукта не может быть null");
        Assertions.assertEquals(foodOptional.get().getName(), EXOTIC_FOOD,
                String.format("Наименование продукта %s не соответствует наименованию %s",
                        foodOptional.get().getName(), EXOTIC_FOOD));
        Assertions.assertEquals(foodOptional.get().getType(), FRUIT,
                String.format("Тип продукта не соответствует типу %s", FRUIT));
        Assertions.assertTrue(foodOptional.get().isExotic(),
                "Состояние продукта должно быть true");
    }

    private Food createTestFood(String foodName, FoodType foodType, boolean exoticStatus) {
        return Food.builder()
                .name(foodName)
                .type(foodType)
                .exotic(exoticStatus)
                .build();
    }
}