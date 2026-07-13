package ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import dict.MemberAccess;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static dict.Elements.*;
import static dict.Urls.PROJECTS_PATH;

public class ProjectsPage extends BasePage {

    private final SelenideElement CREATE_NEW_PROJECT_LOCATOR = $(byText(CREATE_NEW_PROJECT));
    private final SelenideElement PROJECT_NAME_FIELD = $("#project-name");
    private final SelenideElement PROJECT_CODE_FIELD = $("#project-code");
    private final String TABLE_ROW_TAG = "tr";
    private final String ACTION_MENU_CSS = "button[aria-label='Open action menu']";
    private final SelenideElement CREATE_PROJECT_BUTTON = $(byText(CREATE_PROJECT));
    private final SelenideElement REMOVE_MENU_OPTION = $("[data-testid=remove]");
    private final SelenideElement CONFIRM_DELETE_BUTTON = $x("//span[text()='Delete project']");
    private final SelenideElement SETTINGS_MENU_OPTION = $(byText(SETTINGS));
    private final SelenideElement SEARCH_FIELD = $("input[placeholder='Search for projects']");
    private final SelenideElement PROJECT_LIMIT_MODAL_TITLE_LOCATOR = $(byText(PROJECT_LIMIT_MODAL_TITLE));

    public ProjectsPage openPage() {
        open(PROJECTS_PATH);
        return this;
    }

    @Override
    public ProjectsPage isPageOpened() {
        CREATE_NEW_PROJECT_LOCATOR.shouldBe(Condition.visible);
        return this;
    }

    public ProjectPage createProject(String name, String code, MemberAccess accessType) {
        CREATE_NEW_PROJECT_LOCATOR.click();
        PROJECT_NAME_FIELD.setValue(name);
        PROJECT_CODE_FIELD.setValue(code);
        $(byText(accessType.getLabel())).click();
        CREATE_PROJECT_BUTTON.click();
        return new ProjectPage();
    }

    public ProjectsPage openCreateProjectModal() {
        CREATE_NEW_PROJECT_LOCATOR.click();
        return this;
    }

    public ProjectsPage submitCreateProjectForm() {
        CREATE_PROJECT_BUTTON.click();
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

    public ProjectsPage deleteProject(String project) {
        getActionMenuByProjectName(project).click();
        REMOVE_MENU_OPTION.click();
        CONFIRM_DELETE_BUTTON.click();
        return this;
    }

    public ProjectSettingsPage openProjectSettings(String project) {
        getActionMenuByProjectName(project).click();
        SETTINGS_MENU_OPTION.click();
        return new ProjectSettingsPage();
    }

    public ProjectsPage search(String query) {
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
