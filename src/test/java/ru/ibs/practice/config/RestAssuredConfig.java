package ru.ibs.practice.config;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class RestAssuredConfig {
    private static final Cookies cookies = getCookies();
    private static final PropertiesConfig config = new PropertiesConfig();

    private RestAssuredConfig() {
    }

    private static Cookies getCookies() {
        return RestAssured.given()
                .when()
                .get("/api/food")
                .getDetailedCookies();
    }

    private static RequestSpecification getRequestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(config.get("base.url"))
                .setContentType(ContentType.JSON)
                .addCookies(cookies)
                .build();
    }

    private static ResponseSpecification getResponseSpec() {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();
    }

    public static void install() {
        RestAssured.requestSpecification = getRequestSpec();
        RestAssured.responseSpecification = getResponseSpec();
    }
}