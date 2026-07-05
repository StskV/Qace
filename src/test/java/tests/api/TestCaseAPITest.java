package tests.api;

import adapters.ProjectAdapter;
import adapters.TestCaseAdapter;
import api.models.project.ProjectRq;
import api.models.testCase.TestCaseRq;
import api.models.testCase.TestCaseRs;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class TestCaseAPITest {

    private final String CODE = "QA";
    private final String DESCRIPTION = "Test description";
    private final String PRECONDITION = "Test precondition";
    private final String POSTCONDITION = "Test postcondition";
    private final String TITLE = "Test title";
    private final int SEVERITY = 1;
    private final int PRIORITY = 2;
    private final int IS_TO_BE_AUTOMATED = 1;

    @BeforeMethod
    public void createProject() {
        ProjectRq projectRq = ProjectRq.builder()
                .title("QA")
                .code(CODE)
                .description("test")
                .access("none")
                .group("test")
                .build();
        ProjectAdapter.createProject(projectRq);
    }

    @Test
    public void checkTestCaseCRUD() {
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
        assertTrue(rs.status, "Status not received");
        assertNotNull(rs.result.id, "Id not received");

        TestCaseRs fetchedRs = TestCaseAdapter.getTestCase(CODE, createdId);
        assertTrue(fetchedRs.status, "Status failed");
        assertEquals(fetchedRs.result.id, createdId, "Created id mismatch");
        assertEquals(fetchedRs.result.title, TITLE, "Created title mismatch");

        String newTitle = "Updated title";
        TestCaseRq updateRq = TestCaseRq.builder().title(newTitle).build();
        TestCaseAdapter.updateTestCase(updateRq, CODE, createdId);
        TestCaseRs updatedRs = TestCaseAdapter.getTestCase(CODE, createdId);
        assertEquals(updatedRs.result.title, newTitle, "Updated title mismatch");

        boolean isDeleted = TestCaseAdapter.deleteTestCase(CODE, createdId);
        assertTrue(isDeleted, "Test case not deleted");
    }

    @AfterMethod
    public void deleteProject() {
        ProjectAdapter.deleteProject(CODE);
    }

}
