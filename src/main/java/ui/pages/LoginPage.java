package ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.shadowCss;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static dict.Elements.INVALID_CREDENTIALS_MESSAGE;
import static dict.Elements.SIGN_IN;
import static dict.Urls.LOGIN_PATH;

public class LoginPage extends BasePage {

    private final SelenideElement EMAIL_LOCATOR = $("[name=email]");
    private final SelenideElement PASSWORD_LOCATOR = $("[name=password]");
    private final SelenideElement ACCEPT_COOKIE_BUTTON = $(shadowCss("#accept", "#usercentrics-cmp-ui"));
    private final SelenideElement SIGN_IN_LOCATOR = $(byText(SIGN_IN));
    private final SelenideElement INVALID_CREDENTIALS_MESSAGE_LOCATOR = $(byText(INVALID_CREDENTIALS_MESSAGE));

    public LoginPage openPage() {
        open(LOGIN_PATH);
        return this;
    }

    @Override
    public LoginPage isPageOpened() {
        SIGN_IN_LOCATOR.shouldBe(Condition.visible);
        return this;
    }

    public ProjectsPage login(String email, String password) {
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
}
