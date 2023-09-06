package ru.ibs.practice.service.impl;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import ru.ibs.practice.config.RestAssuredConfig;
import ru.ibs.practice.model.Food;
import ru.ibs.practice.service.APIService;

import java.util.List;

@Slf4j
public class APIServiceImpl implements APIService {
    public APIServiceImpl() {
        RestAssuredConfig.install();
    }

    @Step("Получен список товаров через API")
    @Override
    public List<Food> getAll() {
        log.info("Получаем список всех товаров");

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .get("/api/food");

        return response.jsonPath().getList(".", Food.class);
    }

    @Step("Добавлен товара через API {food}")
    @Override
    public void add(Food food) {
        log.info("Добавляется товар: {}", food);

        RestAssured
                .given()
                .body(food)
                .post("/api/food")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Step("Выполнен сброс данных")
    @Override
    public void dataReset() {
        log.info("Выполняется сброс данных");

        RestAssured
                .given()
                .post("/api/data/reset")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }
}