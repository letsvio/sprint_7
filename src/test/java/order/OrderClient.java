package order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderClient {

    private static final String BASE_URI = "https://qa-scooter.praktikum-services.ru/api/v1";

    @Step("Создать заказ")
    public ValidatableResponse createOrder(Order order) {
        return given()
                .header("Content-type", "application/json")
                .body(order)
                .when()
                .post(BASE_URI + "/orders")
                .then();
    }

    @Step("Получить список всех заказов")
    public ValidatableResponse getOrders() {
        return given()
                .when()
                .get(BASE_URI + "/orders")
                .then();
    }
}