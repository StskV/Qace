package ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import dict.MemberAccess;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static dict.Elements.*;
import static dict.Urls.PROJECTS_PATH;

@Log4j2
public class ProjectsPage extends BasePage {

    private final String TABLE_ROW_TAG = "tr";
    private final String ACTION_MENU_CSS = "button[aria-label='Open action menu']";

    private final SelenideElement CREATE_NEW_PROJECT_LOCATOR = $(byText(CREATE_NEW_PROJECT));
    private final SelenideElement PROJECT_NAME_FIELD = $("#project-name");
    private final SelenideElement PROJECT_CODE_FIELD = $("#project-code");
    private final SelenideElement CREATE_PROJECT_BUTTON = $(byText(CREATE_PROJECT));
    private final SelenideElement REMOVE_MENU_OPTION = $("[data-testid=remove]");
    private final SelenideElement CONFIRM_DELETE_BUTTON = $x("//span[text()='Delete project']");
    private final SelenideElement SETTINGS_MENU_OPTION = $(byText(SETTINGS));
    private final SelenideElement SEARCH_FIELD = $("input[placeholder='Search for projects']");
    private final SelenideElement PROJECT_LIMIT_MODAL_TITLE_LOCATOR = $(byText(PROJECT_LIMIT_MODAL_TITLE));

    @Step("Open Projects page")
    public ProjectsPage openPage() {
        log.info("Opening Projects page");
        open(PROJECTS_PATH);
        return this;
    }

    @Override
    @Step("Verify Projects page is opened")
    public ProjectsPage isPageOpened() {
        log.info("Verifying Projects page is opened");
        CREATE_NEW_PROJECT_LOCATOR.shouldBe(Condition.visible);
        return this;
    }

    @Step("Create project '{name}', '{code}' with access '{accessType}'")
    public ProjectPage createProject(String name, String code, MemberAccess accessType) {
        log.info("Creating project '{}', '{}' with access '{}'", name, code, accessType);
        CREATE_NEW_PROJECT_LOCATOR.click();
        PROJECT_NAME_FIELD.setValue(name);
        PROJECT_CODE_FIELD.setValue(code);
        $(byText(accessType.getLabel())).click();
        CREATE_PROJECT_BUTTON.click();
        return new ProjectPage();
    }

    @Step("Open create project modal")
    public ProjectsPage openCreateProjectModal() {
        log.info("Opening create project modal");
        CREATE_NEW_PROJECT_LOCATOR.click();
        return this;
    }

    @Step("Submit create project form")
    public ProjectsPage submitCreateProjectForm() {
        log.info("Submitting create project form");
        CREATE_PROJECT_BUTTON.click();
        return this;
    }

    @Step("Fill project form with name '{name}', code '{code}'")
    public ProjectsPage fillProjectForm(String name, String code) {
        log.info("Filling project form with name '{}', code '{}'", name, code);
        PROJECT_NAME_FIELD.setValue(name);
        PROJECT_CODE_FIELD.setValue(code);
        return this;
    }

    public SelenideElement getCreateNewProjectButton() {
        return CREATE_NEW_PROJECT_LOCATOR;
    }

    public SelenideElement getProjectNameField() {
        return PROJECT_NAME_FIELD;
    }

    private SelenideElement getActionMenuByProjectName(String projectName) {
        return $(byText(projectName))
                .ancestor(TABLE_ROW_TAG)
                .find(ACTION_MENU_CSS);
    }

    @Step("Delete project '{project}'")
    public ProjectsPage deleteProject(String project) {
        log.info("Deleting project '{}'", project);
        getActionMenuByProjectName(project).click();
        REMOVE_MENU_OPTION.click();
        CONFIRM_DELETE_BUTTON.click();
        return this;
    }

    @Step("Open project settings for '{project}'")
    public ProjectSettingsPage openProjectSettings(String project) {
        log.info("Opening project settings for '{}'", project);
        getActionMenuByProjectName(project).click();
        SETTINGS_MENU_OPTION.click();
        return new ProjectSettingsPage();
    }

    @Step("Search for project '{query}'")
    public ProjectsPage search(String query) {
        log.info("Searching for project '{}'", query);
        SEARCH_FIELD.setValue(query);
        return this;
    }

    public SelenideElement getProjectByName(String project) {
        return $(byText(project));
    }

    public SelenideElement getProjectLimitModalTitle() {
        return PROJECT_LIMIT_MODAL_TITLE_LOCATOR;
    }
}
