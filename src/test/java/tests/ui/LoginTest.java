package tests.ui;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tests.base.BaseTest;

@Owner("Satsiuk Viktoriya")
@Epic("Qase UI")
@Feature("Login Page")
public class LoginTest extends BaseTest {

    private static final String INVALID_PASSWORD = "invalidPassword193";

    @Test(
            description = "Verify user can log in with valid credentials",
            testName = "Login with valid credentials",
            groups = "smoke"
    )
    @Story("Login with valid credentials")
    @Severity(SeverityLevel.BLOCKER)
    public void checkLoginWithValidCredentials() {
        loginPage.openPage()
                .isPageOpened()
                .login(email, password)
                .getCreateNewProjectButton().shouldBe(
                        Condition.visible.because("Projects page should be displayed after logging in with valid credentials"));
    }

    @Test(
            description = "Verify an error message is shown when logging in with a wrong password",
            testName = "Login with invalid credentials",
            groups = "regression"
    )
    @Story("Login with invalid credentials")
    @Severity(SeverityLevel.NORMAL)
    public void checkLoginWithInvalidCredentials() {
        loginPage.openPage()
                .isPageOpened()
                .login(email, INVALID_PASSWORD);
        loginPage.getInvalidCredentialsMessage().shouldBe(
                Condition.visible.because("Invalid credentials error message should be displayed after logging in with the wrong password"));
    }

    @DataProvider(name = "incompleteCredentials")
    public Object[][] incompleteCredentials() {
        return new Object[][]{
                {"", password},
                {email, ""},
                {"", ""}
        };
    }

    @Test(
            dataProvider = "incompleteCredentials",
            description = "Verify a required field message is shown when logging in with an empty email and/or password",
            testName = "Login with incomplete credentials",
            groups = "regression"
    )
    @Story("Login with incomplete credentials")
    @Severity(SeverityLevel.NORMAL)
    public void checkLoginWithEmptyFields(String testEmail, String testPassword) {
        loginPage.openPage()
                .isPageOpened()
                .login(testEmail, testPassword);
        loginPage.getRequiredFieldMessage().shouldBe(
                Condition.visible.because("Required field message should be displayed for email=" + testEmail + ", password=" + testPassword));
    }
}
