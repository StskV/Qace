package ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static dict.Elements.ARCHIVE_PROJECT;
import static dict.Elements.ARCHIVE_SUCCESS_MESSAGE;

public class ProjectSettingsPage extends BasePage {

    private final SelenideElement archiveProjectLink = $(byText(ARCHIVE_PROJECT));
    private final SelenideElement archiveSuccessMessage = $(byText(ARCHIVE_SUCCESS_MESSAGE));

    @Override
    public ProjectSettingsPage isPageOpened() {
        archiveProjectLink.shouldBe(Condition.visible);
        return this;
    }

    public ProjectSettingsPage archiveProject() {
        archiveProjectLink.click();
        archiveSuccessMessage.shouldBe(Condition.visible);
        return this;
    }
}
