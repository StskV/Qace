package tests.api;

import adapters.DefectAdapter;
import adapters.ProjectAdapter;
import api.models.defect.DefectRq;
import api.models.defect.DefectRs;
import api.models.project.ProjectRq;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class DefectAPITest {

    private final String CODE = "QA" + System.currentTimeMillis() % 100000000L;
    private final String TITLE = "Test title";
    private final String ACTUAL_RESULT = "Test actual result";
    private final int SEVERITY = 3;

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
    public void checkDefectCRUD() {
        SoftAssert softAssert = new SoftAssert();

        DefectRq rq = DefectRq.builder()
                .code(CODE)
                .title(TITLE)
                .actual_result(ACTUAL_RESULT)
                .severity(SEVERITY)
                .build();
        DefectRs rs = DefectAdapter.createDefect(rq, CODE);
        int createdId = rs.result.id;
        softAssert.assertTrue(rs.status, "Status not received");
        softAssert.assertTrue(createdId > 0, "Id not received");

        DefectRs fetchedRs = DefectAdapter.getDefect(CODE, createdId);
        softAssert.assertTrue(fetchedRs.status, "Status failed");
        softAssert.assertEquals(fetchedRs.result.id, createdId, "Created id mismatch");
        softAssert.assertEquals(fetchedRs.result.title, TITLE, "Created title mismatch");

        String newTitle = "Updated title";
        DefectRq updateRq = DefectRq.builder().title(newTitle).build();
        DefectAdapter.updateDefect(updateRq, CODE, createdId);
        DefectRs updatedRs = DefectAdapter.getDefect(CODE, createdId);
        softAssert.assertEquals(updatedRs.result.title, newTitle,
                "Updated title mismatch after partial title update");
        softAssert.assertEquals(updatedRs.result.actualResult, fetchedRs.result.actualResult,
                "Actual result changed after partial title update");
        softAssert.assertEquals(updatedRs.result.severity, fetchedRs.result.severity,
                "Severity changed after partial title update");

        boolean isDeleted = DefectAdapter.deleteDefect(CODE, createdId);
        softAssert.assertTrue(isDeleted, "Defect not deleted");

        softAssert.assertAll();
    }

    @AfterMethod
    public void deleteProject() {
        ProjectAdapter.deleteProject(CODE);
    }
}
