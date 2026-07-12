package tests.api;

import adapters.ProjectAdapter;
import adapters.TestSuiteAdapter;
import api.models.project.ProjectRq;
import api.models.testSuite.TestSuiteRq;
import api.models.testSuite.TestSuiteRs;
import com.github.javafaker.Faker;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class TestSuiteAPITest {

    private final Faker faker = new Faker();
    private final String CODE = "QA" + System.currentTimeMillis() % 100000000L;
    private final String DESCRIPTION = faker.lorem().paragraph();
    private final String PRECONDITION = faker.lorem().sentence();
    private final String TITLE = faker.lorem().sentence(3);
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
        ProjectAdapter.deleteProject(CODE);
    }
}
