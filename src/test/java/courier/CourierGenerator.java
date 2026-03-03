package courier;

import net.datafaker.Faker;

public class CourierGenerator {

    private static final Faker faker = new Faker();

    public static Courier randomCourier() {
        String uniqueSuffix = "_" + System.currentTimeMillis();
        return new Courier(
                faker.internet().domainWord() + uniqueSuffix,
                faker.internet().password(6, 12),
                faker.name().firstName()
        );
    }

    public static Courier randomCourierWithFixedLogin(String login) {
        return new Courier(
                login,
                faker.internet().password(6, 12),
                faker.name().firstName()
        );
    }
}