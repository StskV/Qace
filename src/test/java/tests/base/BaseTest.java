package tests.base;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import dict.Urls;
import io.qameta.allure.selenide.AllureSelenide;
import io.qameta.allure.testng.AllureTestNg;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import ui.pages.DefectsPage;
import ui.pages.LoginPage;
import ui.pages.ProjectPage;
import ui.pages.ProjectsPage;
import ui.pages.RepositoryPage;
import utils.PropertyReader;

import java.util.HashMap;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

@Listeners(AllureTestNg.class)
public class BaseTest {

    protected LoginPage loginPage;
    protected ProjectPage projectPage;
    protected ProjectsPage projectsPage;
    protected RepositoryPage repositoryPage;
    protected DefectsPage defectsPage;
    protected String email = System.getProperty("email", PropertyReader.getProperty("email"));
    protected String password = System.getProperty("password", PropertyReader.getProperty("password"));

    @BeforeClass
    @Parameters({"browser"})
    public void setUp(@Optional("chrome") String browser) {
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));
        Configuration.browser = browser;
        Configuration.baseUrl = Urls.BASE_URL;
        Configuration.timeout = 30000;
        Configuration.clickViaJs = true;
        Configuration.headless = headless;
        Configuration.browserSize = "1920x1080";

        if ("chrome".equalsIgnoreCase(browser)) {
            ChromeOptions options = new ChromeOptions();
            HashMap<String, Object> chromePrefs = new HashMap<>();
            chromePrefs.put("credentials_enable_service", false);
            chromePrefs.put("profile.password_manager_enabled", false);
            options.setExperimentalOption("prefs", chromePrefs);
            options.addArguments("--incognito");
            options.addArguments("--disable-notifications");
            options.addArguments("--disable-popup-blocking");
            options.addArguments("--disable-infobars");
            Configuration.browserCapabilities = options;
        } else if ("firefox".equalsIgnoreCase(browser)) {
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("-private");
            options.addPreference("dom.webnotifications.enabled", false);
            options.addPreference("signon.rememberSignons", false);
            options.addPreference("browser.popups.showPopupBlocked", false);
            Configuration.browserCapabilities = options;
        } else if ("edge".equalsIgnoreCase(browser)) {
            EdgeOptions options = new EdgeOptions();
            HashMap<String, Object> edgePrefs = new HashMap<>();
            edgePrefs.put("credentials_enable_service", false);
            edgePrefs.put("profile.password_manager_enabled", false);
            options.setExperimentalOption("prefs", edgePrefs);
            options.addArguments("--inprivate");
            options.addArguments("--disable-notifications");
            options.addArguments("--disable-popup-blocking");
            options.addArguments("--disable-infobars");
            Configuration.browserCapabilities = options;
        }

        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .screenshots(true)
                .savePageSource(true)
        );

        loginPage = new LoginPage();
        projectPage = new ProjectPage();
        projectsPage = new ProjectsPage();
        repositoryPage = new RepositoryPage();
        defectsPage = new DefectsPage();
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        getWebDriver().quit();
    }
}
