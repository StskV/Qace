package tests.api;

import adapters.ProjectAdapter;
import adapters.TestCaseAdapter;
import api.models.project.ProjectRq;
import api.models.testCase.TestCaseRq;
import api.models.testCase.TestCaseRs;
import com.github.javafaker.Faker;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class TestCaseAPITest {

    private final Faker faker = new Faker();
    private final String CODE = "QA" + System.currentTimeMillis() % 100000000L;
    private final String DESCRIPTION = faker.lorem().paragraph();
    private final String PRECONDITION = faker.lorem().sentence();
    private final String POSTCONDITION = faker.lorem().sentence();
    private final String TITLE = faker.lorem().sentence(3);
    private final int SEVERITY = faker.number().numberBetween(1, 6);
    private final int PRIORITY = faker.number().numberBetween(1, 3);
    private final int IS_TO_BE_AUTOMATED = faker.number().numberBetween(0, 1);
    private final String PROJECT_DESCRIPTION = faker.lorem().sentence();
    private final String PROJECT_GROUP = faker.company().industry();

    @BeforeMethod
    public void createProject() {
        ProjectRq projectRq = ProjectRq.builder()
                .title("QA")
                .code(CODE)
                .description(PROJECT_DESCRIPTION)
                .access("none")
                .group(PROJECT_GROUP)
                .build();
        ProjectAdapter.createProject(projectRq);
    }

    @Test
    public void checkTestCaseCRUD() {
        SoftAssert softAssert = new SoftAssert();

        TestCaseRq rq = TestCaseRq.builder()
                .code(CODE)
                .description(DESCRIPTION)
                .preconditions(PRECONDITION)
                .postconditions(POSTCONDITION)
                .title(TITLE)
                .severity(SEVERITY)
                .priority(PRIORITY)
                .isToBeAutomated(IS_TO_BE_AUTOMATED)
                .build();
        TestCaseRs rs = TestCaseAdapter.createTestCase(rq, CODE);
        int createdId = rs.result.id;
        softAssert.assertTrue(rs.status, "Status not received");
        softAssert.assertTrue(createdId > 0, "Id not received");

        TestCaseRs fetchedRs = TestCaseAdapter.getTestCase(CODE, createdId);
        softAssert.assertTrue(fetchedRs.status, "Status failed");
        softAssert.assertEquals(fetchedRs.result.id, createdId, "Created id mismatch");
        softAssert.assertEquals(fetchedRs.result.title, TITLE, "Created title mismatch");

        String newTitle = faker.lorem().sentence(3);
        TestCaseRq updateRq = TestCaseRq.builder().title(newTitle).build();
        TestCaseAdapter.updateTestCase(updateRq, CODE, createdId);
        TestCaseRs updatedRs = TestCaseAdapter.getTestCase(CODE, createdId);
        softAssert.assertEquals(updatedRs.result.title, newTitle, "Updated title mismatch");
        softAssert.assertEquals(updatedRs.result.description, fetchedRs.result.description,
                "Description changed after partial title update");
        softAssert.assertEquals(updatedRs.result.preconditions, fetchedRs.result.preconditions,
                "Preconditions changed after partial title update");
        softAssert.assertEquals(updatedRs.result.postconditions, fetchedRs.result.postconditions,
                "Postconditions changed after partial title update");
        softAssert.assertEquals(updatedRs.result.severity, fetchedRs.result.severity,
                "Severity changed after partial title-only update");
        softAssert.assertEquals(updatedRs.result.priority, fetchedRs.result.priority,
                "Priority changed after partial title update");
        softAssert.assertEquals(updatedRs.result.automation, fetchedRs.result.automation,
                "Automation changed after partial title update");

        boolean isDeleted = TestCaseAdapter.deleteTestCase(CODE, createdId);
        softAssert.assertTrue(isDeleted, "Test case not deleted");

        softAssert.assertAll();
    }

    @AfterMethod
    public void deleteProject() {
        ProjectAdapter.deleteProject(CODE);
    }
}
