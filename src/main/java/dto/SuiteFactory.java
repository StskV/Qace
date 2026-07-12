package dto;

import com.github.javafaker.Faker;

public class SuiteFactory {
    public static Suite getSuite(String title) {
        Faker faker = new Faker();
        return Suite.builder()
                .title(title)
                .description(faker.lorem().sentence())
                .preconditions(faker.lorem().sentence())
                .build();
    }
}
