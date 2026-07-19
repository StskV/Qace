package api.steps;

import api.adapters.DefectAdapter;
import api.models.defect.DefectRq;
import com.github.javafaker.Faker;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class DefectStep {

    private static final Faker faker = new Faker();

    @Step("Create defect '{projectCode}', '{title}' via API")
    public static int createDefectViaApi(String projectCode, String title) {
        log.info("Creating defect '{}' in project '{}' via API", title, projectCode);
        DefectRq rq = DefectRq.builder()
                .code(projectCode)
                .title(title)
                .actualResult(faker.lorem().sentence())
                .severity(faker.number().numberBetween(1, 6))
                .build();
        return DefectAdapter.createDefect(rq, projectCode).result.id;
    }
}
