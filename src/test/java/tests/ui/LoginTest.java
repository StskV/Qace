package tests.ui;

import com.codeborne.selenide.Condition;
import org.testng.annotations.Test;
import tests.BaseTest;

public class LoginTest extends BaseTest {

    private static final String INVALID_PASSWORD = "12345678";

    @Test
    public void checkLoginWithValidCredentials() {
        loginPage.openPage()
                .isPageOpened()
                .login(email, password)
                .getCreateNewProjectButton().shouldBe(
                        Condition.visible.because("Projects page should be displayed after logging in with valid credentials"));
    }

    @Test
    public void checkLoginWithInvalidCredentials() {
        loginPage.openPage()
                .isPageOpened()
                .login(email, INVALID_PASSWORD);
        loginPage.getInvalidCredentialsMessage().shouldBe(
                Condition.visible.because("Invalid credentials error message should be displayed after logging in with the wrong password"));
    }
}
