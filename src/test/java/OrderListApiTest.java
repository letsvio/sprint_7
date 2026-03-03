import order.OrderClient;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

@Epic("API Яндекс Самокат")
@Feature("Заказы")
@Story("Список заказов")
public class OrderListApiTest {

    private final OrderClient orderClient = new OrderClient();

    @Test
    @DisplayName("Получение списка заказов → 200 + массив orders")
    @Description("Проверка, что список заказов возвращается и является массивом")
    public void getOrdersListSuccess() {
        orderClient.getOrders()
                .statusCode(200)
                .body("orders", instanceOf(java.util.List.class))
                .body("orders", notNullValue());
    }
}