package ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;

import static com.codeborne.selenide.Selenide.$;

@Log4j2
public class ProjectPage extends BasePage {
    private final SelenideElement PROJECT_TITLE = $("h1");

    @Override
    @Step("Verify Project page is opened")
    public ProjectPage isPageOpened() {
        log.info("Verifying Project page is opened");
        PROJECT_TITLE.shouldBe(Condition.visible);
        return this;
    }

    public SelenideElement getProjectTitle() {
        return PROJECT_TITLE;
    }
}
