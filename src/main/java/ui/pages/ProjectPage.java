package ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class ProjectPage extends BasePage {
    private final SelenideElement projectTitle = $("h1");

    @Override
    public ProjectPage isPageOpened() {
        projectTitle.shouldBe(Condition.visible);
        return this;
    }

    public SelenideElement getProjectTitle() {
        return projectTitle;
    }
}
