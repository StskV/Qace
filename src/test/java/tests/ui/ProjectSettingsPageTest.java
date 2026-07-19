package tests.ui;

import api.steps.ProjectStep;
import com.codeborne.selenide.Condition;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.base.AuthenticatedBaseTest;
import ui.pages.ProjectSettingsPage;

@Owner("Satsiuk Viktoriya")
@Epic("Qase UI")
@Feature("Project Settings Page")
public class ProjectSettingsPageTest extends AuthenticatedBaseTest {

    private static final String PROJECT_NAME = "Settings Test Project";
    private String projectCode;

    @BeforeMethod
    public void createProject() {
        projectCode = "QA" + (System.currentTimeMillis() % 100000);
        ProjectStep.createProjectViaApi(PROJECT_NAME, projectCode);
    }

    @Test(
            description = "Verify the project name can be changed",
            testName = "Change project name",
            groups = "regression"
    )
    @Story("Change project name")
    @Severity(SeverityLevel.NORMAL)
    public void checkChangeProjectName() {
        String newName = "Renamed Project";
        ProjectSettingsPage settingsPage = projectsPage.openPage().isPageOpened()
                .openProjectSettings(PROJECT_NAME)
                .updateProjectName(newName)
                .openSettings(projectCode);
        settingsPage.getProjectNameField().shouldHave(
                Condition.value(newName).because("Project name should be updated to " + newName));
    }

    @Test(
            description = "Verify the project code can be changed",
            testName = "Change project code",
            groups = "regression"
    )
    @Story("Change project code")
    @Severity(SeverityLevel.NORMAL)
    public void checkChangeProjectCode() {
        String newCode = "QANEW" + (System.currentTimeMillis() % 100000);
        ProjectSettingsPage settingsPage = projectsPage.openPage().isPageOpened()
                .openProjectSettings(PROJECT_NAME)
                .updateProjectCode(newCode)
                .openSettings(newCode);
        settingsPage.getProjectCodeField().shouldHave(
                Condition.value(newCode).because("Project code should be updated to " + newCode));
        projectCode = newCode;
    }

    @AfterMethod(alwaysRun = true)
    public void deleteProject() {
        ProjectStep.cleanupProjectViaApi(projectCode);
    }
}
