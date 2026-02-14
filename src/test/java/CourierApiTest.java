import Courier.Courier;
import Courier.CourierClient;
import Courier.CourierCredentials;
import Courier.CourierGenerator;
import io.qameta.allure.Description;
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
@Story("Создание курьера")
public class CourierApiTest {

    private CourierClient courierClient;
    private Courier courier;
    private Integer courierId;

    @BeforeEach
    public void setUp() {
        courierClient = new CourierClient();
        courier = CourierGenerator.randomCourier();
        courierId = null;
    }

    @AfterEach
    public void tearDown() {
        if (courierId != null) {
            courierClient.deleteCourier(courierId);
        }
    }

    @Test
    @DisplayName("Создание курьера → 201 + ok: true")
    @Description("Успешное создание курьера со всеми полями")
    public void createCourierSuccess() {
        courierClient.createCourier(courier)
                .statusCode(201)
                .body("ok", equalTo(true));

        courierId = courierClient.getCourierId(CourierCredentials.from(courier));
    }

    @Test
    @DisplayName("Создание двух одинаковых курьеров → 409")
    @Description("Повторное создание курьера с тем же логином возвращает ошибку")
    public void createDuplicateCourierFail() {
        courierClient.createCourier(courier).statusCode(201);
        courierId = courierClient.getCourierId(CourierCredentials.from(courier));

        courierClient.createCourier(courier)
                .statusCode(409)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Создание без логина → 400")
    @Description("Отсутствие обязательного поля login возвращает ошибку")
    public void createCourierWithoutLoginFail() {
        Courier incomplete = new Courier(null, "pass123", "Test");
        courierClient.createCourier(incomplete)
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание без пароля → 400")
    public void createCourierWithoutPasswordFail() {
        Courier incomplete = new Courier("testuser123", null, "Test");
        courierClient.createCourier(incomplete)
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
}