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

    private final SelenideElement repositoryNavLink = $(byText("Repository"));
    private final SelenideElement manualTestButton = $(byText("Manual test"));
    private final SelenideElement projectActionsMenuButton = $("button[aria-label='Show project actions menu']");
    private final SelenideElement trashBinLink = $(byText("Trash bin"));
    private final SelenideElement restoreSelectedButton = $(byText("Restore selected"));

    private final SelenideElement createNewSuiteLink = $(byText("Create new suite"));
    private final SelenideElement suiteTitleField = $("#title");
    private final SelenideElement suiteDescriptionField = $("#description");
    private final SelenideElement suitePreconditionsField = $("#preconditions");
    private final SelenideElement quickTestButton = $(byText("Quick test"));
    private final SelenideElement quickTestTitleField = $("input[placeholder='Test case title']");
    private final SelenideElement caseTitleField = $("input[placeholder='For example: Authorization']");

    private static final String[] SEVERITY_LABELS =
            {"Undefined", "Blocker", "Critical", "Major", "Normal", "Minor", "Trivial"};
    private static final String[] PRIORITY_LABELS = {"Not set", "Low", "Medium", "High"};

    public RepositoryPage openRepository() {
        repositoryNavLink.click();
        return this;
    }

    @Override
    public RepositoryPage isPageOpened() {
        manualTestButton.shouldBe(Condition.visible);
        return this;
    }

    public RepositoryPage createSuite(Suite data) {
        createNewSuiteLink.click();
        suiteTitleField.setValue(data.getTitle());
        suiteDescriptionField.setValue(data.getDescription());
        suitePreconditionsField.setValue(data.getPreconditions());
        $$("button").findBy(exactText("Create")).click();
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
        $(byText("Duplicate")).click();
        $$("button").findBy(exactText("Clone")).click();
        return this;
    }

    public RepositoryPage deleteSuite(String suiteName) {
        openSuiteActionMenu(suiteName);
        $(byText("Delete")).click();
        $$("button").findBy(exactText("Delete")).click();
        return this;
    }

    public RepositoryPage openSuite(String suiteName) {
        $(byText(suiteName)).click();
        return this;
    }

    public RepositoryPage openUnsortedCases() {
        $(byText("Test cases without suite")).click();
        return this;
    }

    public RepositoryPage createQuickTestCase(String title) {
        quickTestButton.click();
        quickTestTitleField.setValue(title).pressEnter();
        return this;
    }

    public RepositoryPage openManualTestForm() {
        manualTestButton.click();
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
        caseTitleField.setValue(data.getTitle());
        getRichTextFieldByLabel("Description").setValue(data.getDescription());
        getRichTextFieldByLabel("Pre-conditions").setValue(data.getPreconditions());
        selectDropdownOption("Severity", SEVERITY_LABELS[data.getSeverity()]);
        selectDropdownOption("Priority", PRIORITY_LABELS[data.getPriority()]);
        if (Integer.valueOf(1).equals(data.getIsToBeAutomated())) {
            clickViaJs($(byText("To be automated")).parent().find("input[type='checkbox']"));
        }
        $$("button").findBy(exactText("Save")).click();
        caseTitleField.shouldNotBe(Condition.visible);
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
        $("button[aria-label='Delete']").click();
        $("input[placeholder='Type CONFIRM to continue']").setValue("CONFIRM");
        $$("button").findBy(exactText("Delete")).click();
        return this;
    }

    public RepositoryPage openTrashBin() {
        projectActionsMenuButton.click();
        trashBinLink.click();
        return this;
    }

    public RepositoryPage restoreSelectedCase(String caseTitle) {
        clickViaJs(getTrashCaseCheckbox(caseTitle));
        restoreSelectedButton.click();
        return this;
    }

    public SelenideElement getSuiteByName(String suiteName) {
        return $(byText(suiteName));
    }

    public SelenideElement getCaseByTitle(String caseTitle) {
        return $(byText(caseTitle));
    }

    public SelenideElement getPageBody() {
        return $("body");
    }
}
