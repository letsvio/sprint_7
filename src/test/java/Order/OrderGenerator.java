package Order;

import io.qameta.allure.Step;
import net.datafaker.Faker;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class OrderGenerator {

    private static final Faker faker = new Faker();

    @Step("Сгенерировать заказ по умолчанию (без цвета)")
    public static Order getDefaultOrder() {
        return getDefaultOrder(Collections.emptyList());
    }

    @Step("Сгенерировать заказ с цветами: {colors}")
    public static Order getDefaultOrder(List<String> colors) {
        return new Order(
                faker.name().firstName(),
                faker.name().lastName(),
                faker.address().fullAddress(),
                "Лубянка",
                faker.phoneNumber().cellPhone(),
                faker.number().numberBetween(1, 7),
                "2026-03-15",
                "Тестовый комментарий от " + faker.name().username(),
                colors
        );
    }

    public static Stream<List<String>> colorCombinations() {
        return Stream.of(
                Collections.emptyList(),
                Collections.singletonList("GREY"),
                Collections.singletonList("BLACK"),
                Arrays.asList("BLACK", "GREY")
        );
    }
}