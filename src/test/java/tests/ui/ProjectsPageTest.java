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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tests.base.AuthenticatedBaseTest;

import static dict.MemberAccess.DONT_ADD_MEMBERS;

@Owner("Satsiuk Viktoriya")
@Epic("Qase UI")
@Feature("Projects")
public class ProjectsPageTest extends AuthenticatedBaseTest {

    private static final String PROJECT_NAME = "Projects Page Test";

    private String projectCode;
    private String extraCode;

    @BeforeMethod
    public void createProject() {
        extraCode = null;
        projectCode = "QA" + (System.currentTimeMillis() % 100000);
        ProjectStep.createProjectViaApi(PROJECT_NAME, projectCode);
    }

    @Test(
            description = "Verify a new project can be created",
            testName = "Create project",
            groups = "smoke"
    )
    @Story("Create project")
    @Severity(SeverityLevel.CRITICAL)
    public void checkCreateProject() {
        String name = "New Project";
        extraCode = "QAC" + (System.currentTimeMillis() % 100000);
        projectsPage.openPage().isPageOpened()
                .createProject(name, extraCode, DONT_ADD_MEMBERS)
                .isPageOpened()
                .getProjectTitle().shouldHave(
                        Condition.text(extraCode).because("New Project title should be displayed"));
    }

    @Test(
            description = "Verify a project can be deleted",
            testName = "Delete project",
            groups = "smoke"
    )
    @Story("Delete project")
    @Severity(SeverityLevel.CRITICAL)
    public void checkDeleteProject() {
        projectsPage.openPage().isPageOpened()
                .deleteProject(PROJECT_NAME)
                .getProjectByName(PROJECT_NAME).shouldNotBe(
                        Condition.visible.because("Project " + PROJECT_NAME + " should not be displayed after being deleted"));
    }

    @DataProvider(name = "incompleteProjectData")
    public Object[][] incompleteProjectData() {
        return new Object[][]{
                {"", ""},
                {"Only Name", ""},
                {"", "ONLYCODE"}
        };
    }

    @Test(
            dataProvider = "incompleteProjectData",
            description = "Verify the create project modal stays open when the name and/or code are empty",
            testName = "Create project with incomplete data",
            groups = "regression"
    )
    @Story("Create project with incomplete data")
    @Severity(SeverityLevel.NORMAL)
    public void checkCreateProjectWithEmptyFields(String name, String code) {
        projectsPage.openPage().isPageOpened()
                .openCreateProjectModal()
                .fillProjectForm(name, code)
                .submitCreateProjectForm()
                .getProjectNameField().shouldBe(
                        Condition.visible.because("Create project modal should stay open when name=" + name + ", code=" + code));
    }

    @Test(
            description = "Verify the project limit modal is displayed after trying to create a 3rd project",
            testName = "Project creation limit",
            groups = "regression"
    )
    @Story("Project creation limit")
    @Severity(SeverityLevel.NORMAL)
    public void checkProjectCreationLimit() {
        String name = "Limit Test";
        extraCode = "QAL" + (System.currentTimeMillis() % 100000);
        projectsPage.openPage().isPageOpened()
                .createProject(name, extraCode, DONT_ADD_MEMBERS)
                .isPageOpened();
        projectsPage.openPage().isPageOpened()
                .openCreateProjectModal()
                .getProjectLimitModalTitle().shouldBe(
                        Condition.visible.because("Project limit modal is not displayed after trying to create a 3rd project"));
    }

    @Test(
            description = "Verify an archived project disappears from the projects list",
            testName = "Archive project",
            groups = "regression"
    )
    @Story("Archive project")
    @Severity(SeverityLevel.NORMAL)
    public void checkArchiveProject() {
        projectsPage.openPage().isPageOpened()
                .openProjectSettings(PROJECT_NAME)
                .archiveProject();
        projectsPage.openPage().isPageOpened()
                .getProjectByName(PROJECT_NAME).shouldNotBe(
                        Condition.visible.because("Archived project " + PROJECT_NAME + " should not be displayed"));
    }

    @Test(
            description = "Verify a project can be found in the projects list by searching its name",
            testName = "Search project by name",
            groups = "regression"
    )
    @Story("Search project by name")
    @Severity(SeverityLevel.MINOR)
    public void checkSearchProjectByName() {
        projectsPage.openPage().isPageOpened()
                .search(PROJECT_NAME)
                .getProjectByName(PROJECT_NAME).shouldBe(
                        Condition.visible.because("Project " + PROJECT_NAME + " is not displayed in search results"));
    }

    @AfterMethod(alwaysRun = true)
    public void deleteProject() {
        ProjectStep.cleanupProjectViaApi(projectCode);
        if (extraCode != null) {
            ProjectStep.cleanupProjectViaApi(extraCode);
        }
    }
}
