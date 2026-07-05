package tests.ui;

import com.codeborne.selenide.Condition;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import tests.BaseTest;

import static dict.MemberAccess.DONT_ADD_MEMBERS;

public class ProjectTest extends BaseTest {

    private static final String PROJECT_NAME = "TMS02";
    private static final String PROJECT_CODE = "TMS02";

    @Test
    public void checkCreateProject() {
        loginPage.openPage()
                .isPageOpened()
                .login(email, password);
        projectsPage.isPageOpened()
                .createProject(PROJECT_NAME, PROJECT_CODE, DONT_ADD_MEMBERS);
        projectPage.isPageOpened();
        projectPage.getProjectTitle().shouldHave(
                Condition.text(PROJECT_NAME).because("Project was not created"));
    }

    @AfterMethod(alwaysRun = true)
    public void deleteProject() {
        projectsPage.openPage()
                .isPageOpened()
                .deleteProject(PROJECT_NAME);
    }
}
