package ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import dict.Urls;
import dto.Defect;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

@Log4j2
public class DefectsPage extends BasePage {

    private final String TABLE_ROW_TAG = "tr";
    private final String ACTION_MENU_CSS = "button[aria-label='Open action menu']";

    private final SelenideElement DEFECTS_NAV_LINK = $(byText("Defects"));
    private final SelenideElement NEW_DEFECT_BUTTON = $(byText("Create new defect"));
    private final SelenideElement DEFECT_TITLE_FIELD = $("#title");
    private final SelenideElement DEFECT_ACTUAL_RESULT_FIELD = $("#actual_result");
    private final SelenideElement SUBMIT_DEFECT_BUTTON = $$("button").findBy(exactText("Create defect"));
    private final SelenideElement CONFIRM_BUTTON = $$("button").findBy(exactText("Confirm"));
    private final SelenideElement DELETE_CONFIRM_BUTTON = $$("button").findBy(exactText("Delete"));
    private final SelenideElement DELETE_MENU_OPTION = $(byText("Delete"));
    private final SelenideElement STATUS_VALUE = $(byText("Status")).parent().find("[role='combobox']");

    @Step("Open Defects page")
    public DefectsPage openDefects() {
        log.info("Opening Defects page");
        DEFECTS_NAV_LINK.click();
        return this;
    }

    @Step("Open Defects page of project '{projectCode}'")
    public DefectsPage openDefects(String projectCode) {
        log.info("Opening Defects page of project '{}'", projectCode);
        Selenide.open(String.format("%s%s", Urls.PROJECT_PATH, projectCode));
        return openDefects().isPageOpened();
    }

    @Override
    @Step("Verify Defects page is opened")
    public DefectsPage isPageOpened() {
        log.info("Verifying Defects page is opened");
        NEW_DEFECT_BUTTON.shouldBe(Condition.visible);
        return this;
    }

    @Step("Open new defect form")
    public DefectsPage openNewDefectForm() {
        log.info("Opening new defect form");
        NEW_DEFECT_BUTTON.click();
        return this;
    }

    @Step("Save defect {data}")
    public DefectsPage saveDefect(Defect data) {
        log.info("Saving defect {}", data);
        DEFECT_TITLE_FIELD.setValue(data.getTitle());
        DEFECT_ACTUAL_RESULT_FIELD.setValue(data.getActualResult());
        SUBMIT_DEFECT_BUTTON.click();
        return this;
    }

    @Step("Submit new defect form")
    public DefectsPage submitNewDefectForm() {
        log.info("Submitting new defect form");
        SUBMIT_DEFECT_BUTTON.click();
        return this;
    }

    public SelenideElement getDefectTitleField() {
        return DEFECT_TITLE_FIELD;
    }

    private SelenideElement getDefectActionsButton(String defectTitle) {
        return $(byText(defectTitle)).ancestor(TABLE_ROW_TAG).find(ACTION_MENU_CSS);
    }

    @Step("Open action menu for defect '{defectTitle}'")
    public DefectsPage openDefectActionMenu(String defectTitle) {
        log.info("Opening action menu for defect '{}'", defectTitle);
        getDefectActionsButton(defectTitle).click();
        return this;
    }

    @Step("Change status of defect '{defectTitle}' to '{newStatus}'")
    public DefectsPage changeStatus(String defectTitle, String newStatus) {
        log.info("Changing status of defect '{}' to '{}'", defectTitle, newStatus);
        openDefectActionMenu(defectTitle);
        $(byText(newStatus)).click();
        CONFIRM_BUTTON.click();
        return this;
    }

    @Step("Delete defect '{defectTitle}'")
    public DefectsPage deleteDefect(String defectTitle) {
        log.info("Deleting defect '{}'", defectTitle);
        openDefectActionMenu(defectTitle);
        DELETE_MENU_OPTION.click();
        DELETE_CONFIRM_BUTTON.click();
        return this;
    }

    @Step("Open defect {defectId} in project '{projectCode}'")
    public DefectsPage openDefect(String projectCode, int defectId) {
        log.info("Opening defect {} in project '{}'", defectId, projectCode);
        Selenide.open(String.format("%s%s/view/%d", Urls.DEFECT_PATH, projectCode, defectId));
        return this;
    }

    public SelenideElement getDefectByTitle(String title) {
        return $(byText(title));
    }

    public SelenideElement getStatusValue() {
        return STATUS_VALUE;
    }
}
