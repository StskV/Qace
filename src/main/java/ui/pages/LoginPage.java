package ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;

import java.time.Duration;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.shadowCss;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static dict.Elements.INVALID_CREDENTIALS_MESSAGE;
import static dict.Elements.REQUIRED_FIELD_MESSAGE;
import static dict.Elements.SIGN_IN;
import static dict.Urls.LOGIN_PATH;

@Log4j2
public class LoginPage extends BasePage {

    private final SelenideElement EMAIL_LOCATOR = $("[name=email]");
    private final SelenideElement PASSWORD_LOCATOR = $("[name=password]");
    private final SelenideElement ACCEPT_COOKIE_BUTTON = $(shadowCss("#accept", "#usercentrics-cmp-ui"));
    private final SelenideElement SIGN_IN_LOCATOR = $(byText(SIGN_IN));
    private final SelenideElement INVALID_CREDENTIALS_MESSAGE_LOCATOR = $(byText(INVALID_CREDENTIALS_MESSAGE));
    private final SelenideElement REQUIRED_FIELD_MESSAGE_LOCATOR = $(byText(REQUIRED_FIELD_MESSAGE));

    @Step("Open Login page")
    public LoginPage openPage() {
        log.info("Opening Login page");
        open(LOGIN_PATH);
        return this;
    }

    @Override
    @Step("Verify Login page is opened")
    public LoginPage isPageOpened() {
        log.info("Verifying Login page is opened");
        SIGN_IN_LOCATOR.shouldBe(Condition.visible);
        return this;
    }

    @Step("Log in")
    public ProjectsPage login(String email, String password) {
        log.info("Logging in");
        if (ACCEPT_COOKIE_BUTTON.is(Condition.visible, Duration.ofSeconds(5))) {
            ACCEPT_COOKIE_BUTTON.click();
        }
        EMAIL_LOCATOR.setValue(email);
        PASSWORD_LOCATOR.setValue(password);
        SIGN_IN_LOCATOR.click();
        return new ProjectsPage();
    }

    public SelenideElement getInvalidCredentialsMessage() {
        return INVALID_CREDENTIALS_MESSAGE_LOCATOR;
    }

    public SelenideElement getRequiredFieldMessage() {
        return REQUIRED_FIELD_MESSAGE_LOCATOR;
    }
}
