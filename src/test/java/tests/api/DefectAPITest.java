package tests.api;

import api.adapters.DefectAdapter;
import api.steps.ProjectStep;
import api.models.defect.DefectRq;
import api.models.defect.DefectRs;
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
@Feature("Defect API")
public class DefectAPITest {

    private final Faker faker = new Faker();
    private final String CODE = "QA" + System.currentTimeMillis() % 100000000L;
    private final String TITLE = faker.lorem().sentence(3);
    private final String ACTUAL_RESULT = faker.lorem().paragraph();
    private final int SEVERITY = faker.number().numberBetween(1, 6);

    @BeforeMethod
    public void createProject() {
        ProjectStep.createProjectViaApi("QA", CODE);
    }

    @Test(
            description = "Verify a defect can be created, read, updated, and deleted via the API",
            testName = "Defect CRUD via API",
            groups = "api"
    )
    @Story("Defect CRUD via API")
    @Severity(SeverityLevel.CRITICAL)
    public void checkDefectCRUD() {
        SoftAssert softAssert = new SoftAssert();

        DefectRq rq = DefectRq.builder()
                .code(CODE)
                .title(TITLE)
                .actualResult(ACTUAL_RESULT)
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
        ProjectStep.cleanupProjectViaApi(CODE);
    }
}
