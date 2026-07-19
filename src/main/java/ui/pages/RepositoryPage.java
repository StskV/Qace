package ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import dto.Suite;
import dto.TestCase;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

@Log4j2
public class RepositoryPage extends BasePage {

    private static final String[] SEVERITY_LABELS =
            {"Undefined", "Blocker", "Critical", "Major", "Normal", "Minor", "Trivial"};
    private static final String[] PRIORITY_LABELS = {"Not set", "Low", "Medium", "High"};

    private final SelenideElement REPOSITORY_NAV_LINK = $(byText("Repository"));
    private final SelenideElement MANUAL_TEST_BUTTON = $(byText("Manual test"));
    private final SelenideElement PROJECT_ACTIONS_MENU_BUTTON = $("button[aria-label='Show project actions menu']");
    private final SelenideElement TRASH_BIN_LINK = $(byText("Trash bin"));
    private final SelenideElement RESTORE_SELECTED_BUTTON = $(byText("Restore selected"));
    private final SelenideElement CREATE_NEW_SUITE_LINK = $(byText("Create new suite"));
    private final SelenideElement SUITE_TITLE_FIELD = $("#title");
    private final SelenideElement SUITE_DESCRIPTION_FIELD = $("#description");
    private final SelenideElement SUITE_PRECONDITIONS_FIELD = $("#preconditions");
    private final SelenideElement QUICK_TEST_BUTTON = $(byText("Quick test"));
    private final SelenideElement QUICK_TEST_TITLE_FIELD = $("input[placeholder='Test case title']");
    private final SelenideElement CASE_TITLE_FIELD = $("input[placeholder='For example: Authorization']");
    private final SelenideElement CREATE_BUTTON = $$("button").findBy(exactText("Create"));
    private final SelenideElement DUPLICATE_MENU_OPTION = $(byText("Duplicate"));
    private final SelenideElement CLONE_BUTTON = $$("button").findBy(exactText("Clone"));
    private final SelenideElement DELETE_MENU_OPTION = $(byText("Delete"));
    private final SelenideElement CONFIRM_DELETE_BUTTON = $$("button").findBy(exactText("Delete"));
    private final SelenideElement UNSORTED_CASES_FOLDER = $(byText("Test cases without suite"));
    private final SelenideElement TO_BE_AUTOMATED_CHECKBOX = $(byText("To be automated")).parent().find("input[type='checkbox']");
    private final SelenideElement SAVE_BUTTON = $$("button").findBy(exactText("Save"));
    private final SelenideElement DELETE_SELECTED_BUTTON = $("button[aria-label='Delete']");
    private final SelenideElement CONFIRM_DELETE_INPUT = $("input[placeholder='Type CONFIRM to continue']");
    private final SelenideElement PAGE_BODY = $("body");

    @Step("Open Repository page")
    public RepositoryPage openRepository() {
        log.info("Opening Repository page");
        REPOSITORY_NAV_LINK.click();
        return this;
    }

    @Override
    @Step("Verify Repository page is opened")
    public RepositoryPage isPageOpened() {
        log.info("Verifying Repository page is opened");
        MANUAL_TEST_BUTTON.shouldBe(Condition.visible);
        return this;
    }

    @Step("Create suite {data}")
    public RepositoryPage createSuite(Suite data) {
        log.info("Creating suite {}", data);
        CREATE_NEW_SUITE_LINK.click();
        SUITE_TITLE_FIELD.setValue(data.getTitle());
        SUITE_DESCRIPTION_FIELD.setValue(data.getDescription());
        SUITE_PRECONDITIONS_FIELD.setValue(data.getPreconditions());
        CREATE_BUTTON.click();
        return this;
    }

    private SelenideElement getSuiteActionsButton(String suiteName) {
        return $(String.format("button[aria-label='suite %s actions']", suiteName));
    }

    @Step("Open action menu for suite '{suiteName}'")
    public RepositoryPage openSuiteActionMenu(String suiteName) {
        log.info("Opening action menu for suite '{}'", suiteName);
        getSuiteActionsButton(suiteName).click();
        return this;
    }

    @Step("Duplicate suite '{suiteName}'")
    public RepositoryPage duplicateSuite(String suiteName) {
        log.info("Duplicating suite '{}'", suiteName);
        openSuiteActionMenu(suiteName);
        DUPLICATE_MENU_OPTION.click();
        CLONE_BUTTON.click();
        return this;
    }

    @Step("Delete suite '{suiteName}'")
    public RepositoryPage deleteSuite(String suiteName) {
        log.info("Deleting suite '{}'", suiteName);
        openSuiteActionMenu(suiteName);
        DELETE_MENU_OPTION.click();
        CONFIRM_DELETE_BUTTON.click();
        return this;
    }

    @Step("Open suite '{suiteName}'")
    public RepositoryPage openSuite(String suiteName) {
        log.info("Opening suite '{}'", suiteName);
        $(byText(suiteName)).click();
        return this;
    }

    @Step("Open unsorted cases")
    public RepositoryPage openUnsortedCases() {
        log.info("Opening unsorted cases");
        UNSORTED_CASES_FOLDER.click();
        return this;
    }

    @Step("Create quick test case '{title}'")
    public RepositoryPage createQuickTestCase(String title) {
        log.info("Creating quick test case '{}'", title);
        QUICK_TEST_BUTTON.click();
        QUICK_TEST_TITLE_FIELD.setValue(title).pressEnter();
        return this;
    }

    @Step("Open manual test form")
    public RepositoryPage openManualTestForm() {
        log.info("Opening manual test form");
        MANUAL_TEST_BUTTON.click();
        return this;
    }

    private SelenideElement getRichTextFieldByLabel(String labelText) {
        return $(byText(labelText)).parent().find("[contenteditable='true']");
    }

    private void selectDropdownOption(String fieldLabel, String optionText) {
        $(byText(fieldLabel)).parent().find("[role='combobox']").click();
        $(byText(optionText)).click();
    }

    @Step("Save test case {data}")
    public RepositoryPage saveTestCase(TestCase data) {
        log.info("Saving test case {}", data);
        CASE_TITLE_FIELD.setValue(data.getTitle());
        getRichTextFieldByLabel("Description").setValue(data.getDescription());
        getRichTextFieldByLabel("Pre-conditions").setValue(data.getPreconditions());
        selectDropdownOption("Severity", SEVERITY_LABELS[data.getSeverity()]);
        selectDropdownOption("Priority", PRIORITY_LABELS[data.getPriority()]);
        if (Integer.valueOf(1).equals(data.getIsToBeAutomated())) {
            TO_BE_AUTOMATED_CHECKBOX.click();
        }
        SAVE_BUTTON.click();
        CASE_TITLE_FIELD.shouldNotBe(Condition.visible);
        return this;
    }

    private SelenideElement getCaseCheckbox(String caseTitle) {
        return $(String.format("[aria-label='select case %s']", caseTitle));
    }

    private SelenideElement getTrashCaseCheckbox(String caseTitle) {
        return $(String.format("[aria-label='Select %s']", caseTitle));
    }

    @Step("Select test case '{caseTitle}'")
    public RepositoryPage selectCase(String caseTitle) {
        log.info("Selecting test case '{}'", caseTitle);
        getCaseCheckbox(caseTitle).click();
        return this;
    }

    @Step("Delete selected test cases")
    public RepositoryPage deleteSelectedCases() {
        log.info("Deleting selected test cases");
        DELETE_SELECTED_BUTTON.click();
        CONFIRM_DELETE_INPUT.setValue("CONFIRM");
        CONFIRM_DELETE_BUTTON.click();
        return this;
    }

    @Step("Open trash bin")
    public RepositoryPage openTrashBin() {
        log.info("Opening trash bin");
        PROJECT_ACTIONS_MENU_BUTTON.click();
        TRASH_BIN_LINK.click();
        return this;
    }

    @Step("Restore test case '{caseTitle}' from trash bin")
    public RepositoryPage restoreSelectedCase(String caseTitle) {
        log.info("Restoring test case '{}' from trash bin", caseTitle);
        getTrashCaseCheckbox(caseTitle).click();
        RESTORE_SELECTED_BUTTON.click();
        return this;
    }

    public SelenideElement getSuiteByName(String suiteName) {
        return $(byText(suiteName));
    }

    public SelenideElement getCaseByTitle(String caseTitle) {
        return $(byText(caseTitle));
    }

    public SelenideElement getPageBody() {
        return PAGE_BODY;
    }
}
