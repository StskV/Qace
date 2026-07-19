package tests.base;

import org.testng.annotations.BeforeClass;
import ui.steps.LoginStep;

public class AuthenticatedBaseTest extends BaseTest {

    @BeforeClass
    public void loginOnce() {
        LoginStep.login(loginPage, email, password);
        projectsPage.isPageOpened();
    }
}
