package ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static dict.Elements.ARCHIVE_PROJECT;
import static dict.Elements.ARCHIVE_SUCCESS_MESSAGE;

public class ProjectSettingsPage extends BasePage {

    private final SelenideElement ARCHIVE_PROJECT_LINK = $(byText(ARCHIVE_PROJECT));
    private final SelenideElement ARCHIVE_SUCCESS_MESSAGE_LOCATOR = $(byText(ARCHIVE_SUCCESS_MESSAGE));

    @Override
    public ProjectSettingsPage isPageOpened() {
        ARCHIVE_PROJECT_LINK.shouldBe(Condition.visible);
        return this;
    }

    public ProjectSettingsPage archiveProject() {
        ARCHIVE_PROJECT_LINK.click();
        ARCHIVE_SUCCESS_MESSAGE_LOCATOR.shouldBe(Condition.visible);
        return this;
    }
}
