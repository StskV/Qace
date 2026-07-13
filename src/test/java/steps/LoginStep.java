package steps;

import com.codeborne.selenide.Selenide;
import dict.Urls;
import io.qameta.allure.Step;
import ui.pages.LoginPage;
import ui.pages.ProjectsPage;
import ui.pages.RepositoryPage;

public class LoginStep {

    @Step("Log in and open Projects page")
    public static ProjectsPage loginAndOpenProjects(LoginPage loginPage, ProjectsPage projectsPage, String email, String password) {
        loginPage.openPage().isPageOpened()
                .login(email, password);
        return projectsPage.isPageOpened();
    }

    @Step("Log in and open Repository page of project '{projectCode}'")
    public static RepositoryPage loginAndOpenRepository(LoginPage loginPage, ProjectsPage projectsPage, RepositoryPage repositoryPage, String email, String password, String projectCode) {
        loginPage.openPage()
                .isPageOpened()
                .login(email, password);
        projectsPage.isPageOpened();
        Selenide.open(Urls.PROJECT_PATH + projectCode);
        return repositoryPage.openRepository()
                .isPageOpened();
    }
}
