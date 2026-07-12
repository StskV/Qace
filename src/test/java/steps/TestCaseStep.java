package steps;

import adapters.TestCaseAdapter;
import api.models.testCase.TestCaseRq;
import com.github.javafaker.Faker;
import io.qameta.allure.Step;

public class TestCaseStep {

    private static final Faker faker = new Faker();

    @Step("Create test case 'projectCode', '{title}' in suite {suiteId} via API")
    public static int createTestCaseViaApi(String projectCode, String title, int suiteId) {
        TestCaseRq rq = TestCaseRq.builder()
                .code(projectCode)
                .title(title)
                .description(faker.lorem().sentence())
                .severity(faker.number().numberBetween(1, 6))
                .priority(faker.number().numberBetween(1, 3))
                .isToBeAutomated(faker.number().numberBetween(0, 1))
                .suiteId(suiteId)
                .build();
        return TestCaseAdapter.createTestCase(rq, projectCode).result.id;
    }

    @Step("Delete test case {id} via API")
    public static void deleteTestCaseViaApi(String projectCode, int id) {
        TestCaseAdapter.deleteTestCase(projectCode, id);
    }
}
