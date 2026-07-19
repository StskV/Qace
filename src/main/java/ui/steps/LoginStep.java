package ui.steps;

import lombok.extern.log4j.Log4j2;
import ui.pages.LoginPage;

@Log4j2
public class LoginStep {

    public static void login(LoginPage loginPage, String email, String password) {
        log.info("Logging in");
        loginPage.openPage()
                .isPageOpened()
                .login(email, password);
    }
}
