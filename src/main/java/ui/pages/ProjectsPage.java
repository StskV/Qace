package ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import dict.MemberAccess;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static dict.Elements.CREATE_NEW_PROJECT;
import static dict.Elements.CREATE_PROJECT;

public class ProjectsPage extends BasePage {

    private final SelenideElement createNewProject = $(byText(CREATE_NEW_PROJECT));
    private final SelenideElement projectNameField = $("#project-name");
    private final SelenideElement projectCodeField = $("#project-code");
    private final String tableRowTag = "tr";
    private final String actionMenuCss = "button[aria-label='Open action menu']";
    private final SelenideElement createProjectButton = $(byText(CREATE_PROJECT));
    private final SelenideElement removeMenuOption = $("[data-testid=remove]");
    private final SelenideElement confirmDeleteButton = $x("//span[text()='Delete project']");

    public ProjectsPage openPage() {
        open("/projects");
        return this;
    }

    @Override
    public ProjectsPage isPageOpened() {
        createNewProject.shouldBe(Condition.visible);
        return this;
    }

    public ProjectPage createProject(String name, String code) {
        createNewProject.click();
        projectNameField.setValue(name);
        projectCodeField.setValue(code);
        createProjectButton.click();
        return new ProjectPage();
    }

    public ProjectPage createProject(String name, String code, MemberAccess accessType) {
        createNewProject.click();
        projectNameField.setValue(name);
        projectCodeField.setValue(code);
        $(byText(accessType.getLabel())).click();
        createProjectButton.click();
        return new ProjectPage();
    }

    private SelenideElement getActionMenuByProjectName(String projectName) {
        return $(byText(projectName))
                .ancestor(tableRowTag)
                .find(actionMenuCss);
    }

    public ProjectsPage deleteProject(String project) {
        getActionMenuByProjectName(project).click();
        removeMenuOption.click();
        confirmDeleteButton.click();
        return this;
    }
}
