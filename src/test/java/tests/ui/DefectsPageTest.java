package tests.ui;

import api.steps.DefectStep;
import api.steps.ProjectStep;
import com.codeborne.selenide.Condition;
import dto.DefectFactory;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tests.base.BaseTest;
import ui.steps.LoginStep;

@Owner("Satsiuk Viktoriya")
@Epic("Qase UI")
@Feature("Defects Page")
public class DefectsPageTest extends BaseTest {

    private String projectCode;

    @BeforeMethod
    public void createProject() {
        projectCode = "QA" + (System.currentTimeMillis() % 100000);
        ProjectStep.createProjectViaApi("Defects Page Test", projectCode);
    }

    @Test(
            description = "Verify a new defect can be created and is displayed in the defects list",
            testName = "Create defect",
            groups = "smoke"
    )
    @Story("Create defect")
    @Severity(SeverityLevel.CRITICAL)
    public void checkCreateNewDefect() {
        String title = "New defect";
        LoginStep.loginAndOpenDefects(loginPage, projectsPage, defectsPage, email, password, projectCode)
                .openNewDefectForm()
                .saveDefect(DefectFactory.getDefect(title))
                .getDefectByTitle(title).shouldBe(
                        Condition.visible.because("New defect " + title + " should be displayed in the defects list"));
    }

    @Test(
            description = "Verify the create defect form stays open when required fields are empty",
            testName = "Create defect with empty fields",
            groups = "regression"
    )
    @Story("Create defect with empty fields")
    @Severity(SeverityLevel.NORMAL)
    public void checkCreateDefectWithEmptyFields() {
        LoginStep.loginAndOpenDefects(loginPage, projectsPage, defectsPage, email, password, projectCode)
                .openNewDefectForm()
                .submitNewDefectForm()
                .getDefectTitleField().shouldBe(
                        Condition.visible.because("Create defect form should stay open when required fields are empty"));
    }

    @DataProvider(name = "statuses")
    public Object[][] statuses() {
        return new Object[][]{
                {"In Progress"},
                {"Resolved"},
                {"Invalid"}
        };
    }

    @Test(
            dataProvider = "statuses",
            description = "Verify a defect's status can be changed to 'In Progress', 'Resolved', or 'Invalid'",
            testName = "Change defect status",
            groups = "regression"
    )
    @Story("Change defect status")
    @Severity(SeverityLevel.NORMAL)
    public void checkChangeDefectStatus(String status) {
        String title = "Defect To " + status;
        int defectId = DefectStep.createDefectViaApi(projectCode, title);
        LoginStep.loginAndOpenDefects(loginPage, projectsPage, defectsPage, email, password, projectCode)
                .changeStatus(title, status)
                .openDefect(projectCode, defectId)
                .getStatusValue().shouldHave(
                        Condition.text(status).because("Defect " + title + " status should be changed to " + status));
    }

    @Test(
            description = "Verify a defect can be deleted from the defects list",
            testName = "Delete defect",
            groups = "regression"
    )
    @Story("Delete defect")
    @Severity(SeverityLevel.NORMAL)
    public void checkDeleteDefect() {
        String title = "Defect To Delete";
        DefectStep.createDefectViaApi(projectCode, title);
        LoginStep.loginAndOpenDefects(loginPage, projectsPage, defectsPage, email, password, projectCode)
                .deleteDefect(title)
                .getDefectByTitle(title).shouldNotBe(
                        Condition.visible.because("Deleted defect " + title + " should not be displayed in the defects list"));
    }

    @AfterMethod(alwaysRun = true)
    public void deleteProject() {
        ProjectStep.cleanupProjectViaApi(projectCode);
    }
}
