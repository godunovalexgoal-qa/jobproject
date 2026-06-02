package ru.bulgakov.booking.util;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;

public class RestAssuredSpec {
    public static RequestSpecification requestSpec = with()
            .log().method()
            .log().uri()
            .log().headers()
            .log().body();

    public static ResponseSpecification responseSpec = new ResponseSpecBuilder()
            .log(LogDetail.BODY)
            .build();

    public static void setUpRestAssured() {
        RestAssured.requestSpecification = requestSpec;
        RestAssured.responseSpecification = responseSpec;

        RestAssured.filters(new AllureRestAssured());
    }
}
