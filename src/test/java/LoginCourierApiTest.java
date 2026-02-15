import courier.Courier;
import courier.CourierClient;
import courier.CourierCredentials;
import courier.CourierGenerator;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

@Epic("API Яндекс Самокат")
@Feature("Курьеры")
@Story("Логин курьера")
public class LoginCourierApiTest {

    private CourierClient courierClient;
    private Courier courier;
    private Integer courierId;

    @BeforeEach
    public void setUp() {
        courierClient = new CourierClient();
        courier = CourierGenerator.randomCourier();
        courierClient.createCourier(courier).statusCode(201);
        courierId = courierClient.getCourierId(CourierCredentials.from(courier));
    }

    @AfterEach
    public void tearDown() {
        if (courierId != null) {
            courierClient.deleteCourier(courierId);
        }
    }

    @Test
    @DisplayName("Успешный логин курьера → 200 + id")
    public void loginCourierSuccess() {
        CourierCredentials creds = CourierCredentials.from(courier);

        courierClient.loginCourier(creds)
                .statusCode(200)
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Логин с неверным паролем → 404")
    public void loginWithWrongPasswordFail() {
        CourierCredentials wrongCreds = new CourierCredentials(courier.getLogin(), "wrongpass123");

        courierClient.loginCourier(wrongCreds)
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Логин несуществующего пользователя → 404")
    public void loginNonExistingUserFail() {
        CourierCredentials fakeCreds = new CourierCredentials("nonexistent_999999", "pass123");

        courierClient.loginCourier(fakeCreds)
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Логин без логина → 400")
    public void loginWithoutLoginFail() {
        CourierCredentials emptyLogin = CourierCredentials.withLoginOnly("pass123");

        courierClient.loginCourier(emptyLogin)
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }
}