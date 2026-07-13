package ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import dto.Suite;
import dto.TestCase;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class RepositoryPage extends BasePage {

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

    private static final String[] SEVERITY_LABELS =
            {"Undefined", "Blocker", "Critical", "Major", "Normal", "Minor", "Trivial"};
    private static final String[] PRIORITY_LABELS = {"Not set", "Low", "Medium", "High"};

    public RepositoryPage openRepository() {
        REPOSITORY_NAV_LINK.click();
        return this;
    }

    @Override
    public RepositoryPage isPageOpened() {
        MANUAL_TEST_BUTTON.shouldBe(Condition.visible);
        return this;
    }

    public RepositoryPage createSuite(Suite data) {
        CREATE_NEW_SUITE_LINK.click();
        SUITE_TITLE_FIELD.setValue(data.getTitle());
        SUITE_DESCRIPTION_FIELD.setValue(data.getDescription());
        SUITE_PRECONDITIONS_FIELD.setValue(data.getPreconditions());
        CREATE_BUTTON.click();
        return this;
    }

    private SelenideElement getSuiteActionsButton(String suiteName) {
        return $("button[aria-label='suite " + suiteName + " actions']");
    }

    public RepositoryPage openSuiteActionMenu(String suiteName) {
        getSuiteActionsButton(suiteName).click();
        return this;
    }

    public RepositoryPage duplicateSuite(String suiteName) {
        openSuiteActionMenu(suiteName);
        DUPLICATE_MENU_OPTION.click();
        CLONE_BUTTON.click();
        return this;
    }

    public RepositoryPage deleteSuite(String suiteName) {
        openSuiteActionMenu(suiteName);
        DELETE_MENU_OPTION.click();
        CONFIRM_DELETE_BUTTON.click();
        return this;
    }

    public RepositoryPage openSuite(String suiteName) {
        $(byText(suiteName)).click();
        return this;
    }

    public RepositoryPage openUnsortedCases() {
        UNSORTED_CASES_FOLDER.click();
        return this;
    }

    public RepositoryPage createQuickTestCase(String title) {
        QUICK_TEST_BUTTON.click();
        QUICK_TEST_TITLE_FIELD.setValue(title).pressEnter();
        return this;
    }

    public RepositoryPage openManualTestForm() {
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

    public RepositoryPage saveTestCase(TestCase data) {
        CASE_TITLE_FIELD.setValue(data.getTitle());
        getRichTextFieldByLabel("Description").setValue(data.getDescription());
        getRichTextFieldByLabel("Pre-conditions").setValue(data.getPreconditions());
        selectDropdownOption("Severity", SEVERITY_LABELS[data.getSeverity()]);
        selectDropdownOption("Priority", PRIORITY_LABELS[data.getPriority()]);
        if (Integer.valueOf(1).equals(data.getIsToBeAutomated())) {
            clickViaJs(TO_BE_AUTOMATED_CHECKBOX);
        }
        SAVE_BUTTON.click();
        CASE_TITLE_FIELD.shouldNotBe(Condition.visible);
        return this;
    }

    private SelenideElement getCaseCheckbox(String caseTitle) {
        return $("[aria-label='select case " + caseTitle + "']");
    }

    private SelenideElement getTrashCaseCheckbox(String caseTitle) {
        return $("[aria-label='Select " + caseTitle + "']");
    }

    private void clickViaJs(SelenideElement element) {
        Selenide.executeJavaScript("arguments[0].click()", element);
    }

    public RepositoryPage selectCase(String caseTitle) {
        clickViaJs(getCaseCheckbox(caseTitle));
        return this;
    }

    public RepositoryPage deleteSelectedCases() {
        DELETE_SELECTED_BUTTON.click();
        CONFIRM_DELETE_INPUT.setValue("CONFIRM");
        CONFIRM_DELETE_BUTTON.click();
        return this;
    }

    public RepositoryPage openTrashBin() {
        PROJECT_ACTIONS_MENU_BUTTON.click();
        TRASH_BIN_LINK.click();
        return this;
    }

    public RepositoryPage restoreSelectedCase(String caseTitle) {
        clickViaJs(getTrashCaseCheckbox(caseTitle));
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
