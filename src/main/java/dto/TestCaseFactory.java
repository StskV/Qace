package dto;

import com.github.javafaker.Faker;

public class TestCaseFactory {
    public static TestCase getTestCase(String title) {
        Faker faker = new Faker();
        return TestCase.builder()
                .title(title)
                .description(faker.lorem().paragraph())
                .preconditions(faker.lorem().sentence())
                .postconditions(faker.lorem().sentence())
                .severity(faker.number().numberBetween(1, 6))
                .priority(faker.number().numberBetween(1, 3))
                .isToBeAutomated(faker.number().numberBetween(0, 1))
                .build();
    }
}
