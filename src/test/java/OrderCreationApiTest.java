import Order.Order;
import Order.OrderClient;
import Order.OrderGenerator;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static org.hamcrest.Matchers.*;

@Epic("API Яндекс Самокат")
@Feature("Заказы")
@Story("Создание заказа")
public class OrderCreationApiTest {

    private final OrderClient orderClient = new OrderClient();

    @Test
    @DisplayName("Создание заказа без цвета → 201 + track")
    public void createOrderWithoutColorSuccess() {
        Order order = OrderGenerator.getDefaultOrder();

        orderClient.createOrder(order)
                .statusCode(201)
                .body("track", notNullValue());
    }

    @ParameterizedTest(name = "Создание заказа с цветами: {0}")
    @MethodSource("Order.OrderGenerator#colorCombinations")
    @DisplayName("Создание заказа с разными комбинациями цветов")
    @Description("Проверка всех вариантов: без цвета, один цвет, оба цвета")
    public void createOrderWithDifferentColors(List<String> colors) {
        Order order = OrderGenerator.getDefaultOrder(colors);

        orderClient.createOrder(order)
                .statusCode(201)
                .body("track", notNullValue());
    }
}