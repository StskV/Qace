package ui.steps;

import com.codeborne.selenide.Selenide;
import dict.Urls;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import ui.pages.DefectsPage;
import ui.pages.LoginPage;
import ui.pages.ProjectsPage;
import ui.pages.RepositoryPage;

@Log4j2
public class LoginStep {

    @Step("Log in and open Projects page")
    public static ProjectsPage loginAndOpenProjects(LoginPage loginPage, ProjectsPage projectsPage, String email, String password) {
        log.info("Logging in and opening Projects page");
        loginPage.openPage().isPageOpened()
                .login(email, password);
        return projectsPage.isPageOpened();
    }

    @Step("Log in and open Repository page of project '{projectCode}'")
    public static RepositoryPage loginAndOpenRepository(LoginPage loginPage, ProjectsPage projectsPage, RepositoryPage repositoryPage, String email, String password, String projectCode) {
        log.info("Logging in and opening Repository page of project '{}'", projectCode);
        loginPage.openPage()
                .isPageOpened()
                .login(email, password);
        projectsPage.isPageOpened();
        Selenide.open(String.format("%s%s", Urls.PROJECT_PATH, projectCode));
        return repositoryPage.openRepository()
                .isPageOpened();
    }

    @Step("Log in and open Defects page of project '{projectCode}'")
    public static DefectsPage loginAndOpenDefects(LoginPage loginPage, ProjectsPage projectsPage, DefectsPage defectsPage, String email, String password, String projectCode) {
        log.info("Logging in and opening Defects page of project '{}'", projectCode);
        loginPage.openPage()
                .isPageOpened()
                .login(email, password);
        projectsPage.isPageOpened();
        Selenide.open(String.format("%s%s", Urls.PROJECT_PATH, projectCode));
        return defectsPage.openDefects()
                .isPageOpened();
    }
}
