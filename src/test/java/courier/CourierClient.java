package courier;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierClient {

    private static final String BASE_URI = "https://qa-scooter.praktikum-services.ru/api/v1";

    @Step("Создать курьера")
    public ValidatableResponse createCourier(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post(BASE_URI + "/courier")
                .then();
    }

    @Step("Авторизовать курьера")
    public ValidatableResponse loginCourier(CourierCredentials credentials) {
        return given()
                .header("Content-type", "application/json")
                .body(credentials)
                .when()
                .post(BASE_URI + "/courier/login")
                .then();
    }

    @Step("Удалить курьера по id {courierId}")
    public ValidatableResponse deleteCourier(int courierId) {
        return given()
                .when()
                .delete(BASE_URI + "/courier/" + courierId)
                .then();
    }

    @Step("Получить id курьера после логина")
    public int getCourierId(CourierCredentials credentials) {
        return loginCourier(credentials)
                .statusCode(200)
                .extract()
                .path("id");
    }
}