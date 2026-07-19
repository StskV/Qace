package tests.api;

import api.adapters.TestSuiteAdapter;
import api.steps.ProjectStep;
import api.models.testSuite.TestSuiteRq;
import api.models.testSuite.TestSuiteRs;
import com.github.javafaker.Faker;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

@Owner("Satsiuk Viktoriya")
@Epic("Qase API")
@Feature("Test Suite API")
public class TestSuiteAPITest {

    private final Faker faker = new Faker();
    private final String CODE = "QA" + System.currentTimeMillis() % 100000000L;
    private final String DESCRIPTION = faker.lorem().paragraph();
    private final String PRECONDITION = faker.lorem().sentence();
    private final String TITLE = faker.lorem().sentence(3);

    @BeforeMethod
    public void createProject() {
        ProjectStep.createProjectViaApi("QA", CODE);
    }

    @Test(
            description = "Verify a test suite can be created, read, updated, and deleted via the API",
            testName = "Test suite CRUD via API",
            groups = "api"
    )
    @Story("Test suite CRUD via API")
    @Severity(SeverityLevel.CRITICAL)
    public void checkTestSuiteCRUD() {
        SoftAssert softAssert = new SoftAssert();

        TestSuiteRq rq = TestSuiteRq.builder()
                .code(CODE)
                .description(DESCRIPTION)
                .preconditions(PRECONDITION)
                .title(TITLE)
                .build();
        TestSuiteRs rs = TestSuiteAdapter.createTestSuite(rq, CODE);
        int createdId = rs.result.id;
        softAssert.assertTrue(rs.status, "Status not received");
        softAssert.assertTrue(createdId > 0, "Id not received");

        TestSuiteRs fetchedRs = TestSuiteAdapter.getTestSuite(CODE, createdId);
        softAssert.assertTrue(fetchedRs.status, "Status failed");
        softAssert.assertEquals(fetchedRs.result.id, createdId, "Created id mismatch");
        softAssert.assertEquals(fetchedRs.result.title, TITLE, "Created title mismatch");

        String newTitle = faker.lorem().sentence(3);
        TestSuiteRq updateRq = TestSuiteRq.builder().title(newTitle).build();
        TestSuiteAdapter.updateTestSuite(updateRq, CODE, createdId);
        TestSuiteRs updatedRs = TestSuiteAdapter.getTestSuite(CODE, createdId);
        softAssert.assertEquals(updatedRs.result.title, newTitle, "Updated title mismatch");
        softAssert.assertEquals(updatedRs.result.description, fetchedRs.result.description,
                "Description changed after partial title update");
        softAssert.assertEquals(updatedRs.result.preconditions, fetchedRs.result.preconditions,
                "Preconditions changed after partial title update");

        boolean isDeleted = TestSuiteAdapter.deleteTestSuite(CODE, createdId);
        softAssert.assertTrue(isDeleted, "Test suite not deleted");

        softAssert.assertAll();
    }

    @AfterMethod
    public void deleteProject() {
        ProjectStep.cleanupProjectViaApi(CODE);
    }
}
