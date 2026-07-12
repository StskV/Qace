package steps;

import adapters.TestSuiteAdapter;
import api.models.testSuite.TestSuiteRq;
import com.github.javafaker.Faker;
import io.qameta.allure.Step;

public class SuiteStep {

    private static final Faker faker = new Faker();

    @Step("Create suite 'projectCode', '{title}' via API")
    public static int createSuiteViaApi(String projectCode, String title) {
        TestSuiteRq rq = TestSuiteRq.builder()
                .code(projectCode)
                .title(title)
                .description(faker.lorem().sentence())
                .build();
        return TestSuiteAdapter.createTestSuite(rq, projectCode).result.id;
    }
}
