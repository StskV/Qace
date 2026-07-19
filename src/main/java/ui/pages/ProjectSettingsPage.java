package ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import dict.Urls;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static dict.Elements.ARCHIVE_PROJECT;
import static dict.Elements.ARCHIVE_SUCCESS_MESSAGE;
import static dict.Elements.UPDATE_SETTINGS_BUTTON;

@Log4j2
public class ProjectSettingsPage extends BasePage {

    private final SelenideElement ARCHIVE_PROJECT_LINK = $(byText(ARCHIVE_PROJECT));
    private final SelenideElement ARCHIVE_SUCCESS_MESSAGE_LOCATOR = $(byText(ARCHIVE_SUCCESS_MESSAGE));
    private final SelenideElement PROJECT_NAME_FIELD = $("#project-name");
    private final SelenideElement PROJECT_CODE_FIELD = $("#project-code");
    private final SelenideElement UPDATE_SETTINGS_BUTTON_LOCATOR = $(byText(UPDATE_SETTINGS_BUTTON));

    @Override
    @Step("Verify Project Settings page is opened")
    public ProjectSettingsPage isPageOpened() {
        log.info("Verifying Project Settings page is opened");
        ARCHIVE_PROJECT_LINK.shouldBe(Condition.visible);
        return this;
    }

    @Step("Archive project")
    public ProjectSettingsPage archiveProject() {
        log.info("Archiving project");
        ARCHIVE_PROJECT_LINK.click();
        ARCHIVE_SUCCESS_MESSAGE_LOCATOR.shouldBe(Condition.visible);
        return this;
    }

    @Step("Update project name to '{newName}'")
    public ProjectSettingsPage updateProjectName(String newName) {
        log.info("Updating project name to '{}'", newName);
        PROJECT_NAME_FIELD.setValue(newName);
        UPDATE_SETTINGS_BUTTON_LOCATOR.click();
        return this;
    }

    @Step("Update project code to '{newCode}'")
    public ProjectSettingsPage updateProjectCode(String newCode) {
        log.info("Updating project code to '{}'", newCode);
        PROJECT_CODE_FIELD.setValue(newCode);
        UPDATE_SETTINGS_BUTTON_LOCATOR.click();
        return this;
    }

    @Step("Open settings for project '{code}'")
    public ProjectSettingsPage openSettings(String code) {
        log.info("Opening settings for project '{}'", code);
        Selenide.open(String.format("%s%s/settings/general", Urls.PROJECT_PATH, code));
        return this;
    }

    public SelenideElement getProjectNameField() {
        return PROJECT_NAME_FIELD;
    }

    public SelenideElement getProjectCodeField() {
        return PROJECT_CODE_FIELD;
    }
}
