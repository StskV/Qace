package api.steps;

import api.adapters.TestSuiteAdapter;
import api.models.testSuite.TestSuiteRq;
import com.github.javafaker.Faker;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class SuiteStep {

    private static final Faker faker = new Faker();

    @Step("Create suite '{projectCode}', '{title}' via API")
    public static int createSuiteViaApi(String projectCode, String title) {
        log.info("Creating suite '{}' in project '{}' via API", title, projectCode);
        TestSuiteRq rq = TestSuiteRq.builder()
                .code(projectCode)
                .title(title)
                .description(faker.lorem().sentence())
                .build();
        return TestSuiteAdapter.createTestSuite(rq, projectCode).result.id;
    }
}
