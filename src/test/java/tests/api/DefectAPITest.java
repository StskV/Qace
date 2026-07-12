package tests.api;

import adapters.DefectAdapter;
import adapters.ProjectAdapter;
import api.models.defect.DefectRq;
import api.models.defect.DefectRs;
import api.models.project.ProjectRq;
import com.github.javafaker.Faker;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class DefectAPITest {

    private final Faker faker = new Faker();
    private final String CODE = "QA" + System.currentTimeMillis() % 100000000L;
    private final String TITLE = faker.lorem().sentence(3);
    private final String ACTUAL_RESULT = faker.lorem().paragraph();
    private final int SEVERITY = faker.number().numberBetween(1, 6);
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

        String newTitle = faker.lorem().sentence(3);
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
