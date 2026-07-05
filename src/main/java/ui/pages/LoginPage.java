package ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.shadowCss;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static dict.Elements.SIGN_IN;

public class LoginPage extends BasePage {

    private final SelenideElement emailLocator = $("[name=email]");
    private final SelenideElement passwordLocator = $("[name=password]");
    private final SelenideElement acceptCookieButton = $(shadowCss("#accept", "#usercentrics-cmp-ui"));
    private final SelenideElement signInLocator = $(byText(SIGN_IN));

    public LoginPage openPage() {
        open("/login");
        return this;
    }

    @Override
    public LoginPage isPageOpened() {
        signInLocator.shouldBe(Condition.visible);
        return this;
    }

    public ProjectsPage login(String email, String password) {
        acceptCookieButton.click();
        emailLocator.setValue(email);
        passwordLocator.setValue(password);
        signInLocator.click();
        return new ProjectsPage();
    }
}
