package dto;

import com.github.javafaker.Faker;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SuiteFactory {
    public Suite getSuite(String title) {
        Faker faker = new Faker();
        return Suite.builder()
                .title(title)
                .description(faker.lorem().sentence())
                .preconditions(faker.lorem().sentence())
                .build();
    }
}
