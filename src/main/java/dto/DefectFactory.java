package dto;

import com.github.javafaker.Faker;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DefectFactory {
    public Defect getDefect(String title) {
        Faker faker = new Faker();
        return Defect.builder()
                .title(title)
                .actualResult(faker.lorem().paragraph())
                .severity(faker.number().numberBetween(1, 6))
                .build();
    }
}
