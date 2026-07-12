package tests.ui;

import com.codeborne.selenide.Condition;
import org.testng.annotations.Test;
import steps.LoginStep;
import steps.ProjectStep;
import tests.BaseTest;

import static dict.MemberAccess.DONT_ADD_MEMBERS;

public class ProjectsPageTest extends BaseTest {

    private final String CODE = "QA" + (System.currentTimeMillis() % 100000);

    @Test
    public void checkCreateProject() {
        String name = "New Project";
        try {
            LoginStep.loginAndOpenProjects(loginPage, projectsPage, email, password)
                    .createProject(name, CODE, DONT_ADD_MEMBERS)
                    .isPageOpened()
                    .getProjectTitle().shouldHave(
                            Condition.text(CODE).because("New Project title should be displayed"));
        } finally {
            ProjectStep.cleanupProjectViaApi(CODE);
        }
    }

    @Test
    public void checkDeleteProject() {
        String name = "Project To Delete";
        ProjectStep.createProjectViaApi(name, CODE);
        try {
            LoginStep.loginAndOpenProjects(loginPage, projectsPage, email, password)
                    .deleteProject(name)
                    .getProjectByName(name).shouldNotBe(
                            Condition.visible.because("Project " + name + " should not be displayed after being deleted"));
        } finally {
            ProjectStep.cleanupProjectViaApi(CODE);
        }
    }

    @Test
    public void checkCreateProjectWithEmptyFields() {
        LoginStep.loginAndOpenProjects(loginPage, projectsPage, email, password)
                .openCreateProjectModal()
                .submitCreateProjectForm()
                .getProjectNameField().shouldBe(
                        Condition.visible.because("Create project modal should stay open"));
    }

    @Test
    public void checkProjectCreationLimit() {
        String firstName = "Limit Test 1";
        String fitstCode = "QA1" + (System.currentTimeMillis() % 100000);
        String secondName = "Limit Test 2";
        String secondCode = "QA2" + (System.currentTimeMillis() % 100000);
        try {
            LoginStep.loginAndOpenProjects(loginPage, projectsPage, email, password)
                    .createProject(firstName, fitstCode, DONT_ADD_MEMBERS)
                    .isPageOpened();
            projectsPage.openPage().isPageOpened()
                    .createProject(secondName, secondCode, DONT_ADD_MEMBERS)
                    .isPageOpened();
            projectsPage.openPage().isPageOpened()
                    .openCreateProjectModal()
                    .getProjectLimitModalTitle().shouldBe(
                            Condition.visible.because("Project limit modal is not displayed after trying to create 3rd projects"));
        } finally {
            ProjectStep.cleanupProjectViaApi(fitstCode);
            ProjectStep.cleanupProjectViaApi(secondCode);
        }
    }

    @Test
    public void checkArchiveProject() {
        String name = "Project To Archive";
        ProjectStep.createProjectViaApi(name, CODE);
        try {
            LoginStep.loginAndOpenProjects(loginPage, projectsPage, email, password)
                    .openProjectSettings(name)
                    .archiveProject();
            projectsPage.openPage().isPageOpened()
                    .getProjectByName(name).shouldNotBe(
                            Condition.visible.because("Archived project " + name + " should not be displayed"));
        } finally {
            ProjectStep.cleanupProjectViaApi(CODE);
        }
    }

    @Test
    public void checkSearchProjectByName() {
        String name = "Project To Search" + (System.currentTimeMillis() % 100000);
        ProjectStep.createProjectViaApi(name, CODE);
        try {
            LoginStep.loginAndOpenProjects(loginPage, projectsPage, email, password)
                    .search(name)
                    .getProjectByName(name).shouldBe(
                            Condition.visible.because("Project " + name + " is not displayed in search results"));
        } finally {
            ProjectStep.cleanupProjectViaApi(CODE);
        }
    }
}
