package tests.ui;

import com.codeborne.selenide.Condition;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import dto.SuiteFactory;
import dto.TestCaseFactory;
import steps.LoginStep;
import steps.ProjectStep;
import steps.SuiteStep;
import steps.TestCaseStep;
import tests.BaseTest;

public class ProjectPageTest extends BaseTest {

    private String projectCode;

    @BeforeMethod
    public void createProject() {
        projectCode = "QA" + (System.currentTimeMillis() % 100000);
        ProjectStep.createProjectViaApi("Project Page Test", projectCode);
    }

    @Test
    public void checkCreateNewSuite() {
        String suiteName = "New Suite";
        LoginStep.loginAndOpenRepository(loginPage, projectsPage, repositoryPage, email, password, projectCode)
                .createSuite(SuiteFactory.getSuite(suiteName))
                .getSuiteByName(suiteName).shouldBe(
                        Condition.visible.because("Created suite " + suiteName + " is not displayed in the repository"));
    }

    @Test
    public void checkDuplicateSuite() {
        String suiteName = "Suite To Duplicate";
        SuiteStep.createSuiteViaApi(projectCode, suiteName);
        LoginStep.loginAndOpenRepository(loginPage, projectsPage, repositoryPage, email, password, projectCode)
                .duplicateSuite(suiteName)
                .getPageBody().shouldHave(Condition.text("2 suites").because("Suite count should be 2 after duplicating"));
    }

    @Test
    public void checkDeleteSuite() {
        String suiteName = "Suite To Delete";
        SuiteStep.createSuiteViaApi(projectCode, suiteName);
        LoginStep.loginAndOpenRepository(loginPage, projectsPage, repositoryPage, email, password, projectCode)
                .deleteSuite(suiteName)
                .getSuiteByName(suiteName).shouldNotBe(Condition.visible.because("Suite " + suiteName + " was not deleted"));
    }

    @Test
    public void checkCreateQuickTestCase() {
        String suiteName = "Suite For Quick Case";
        String caseTitle = "Quick case";
        SuiteStep.createSuiteViaApi(projectCode, suiteName);
        LoginStep.loginAndOpenRepository(loginPage, projectsPage, repositoryPage, email, password, projectCode)
                .openSuite(suiteName)
                .createQuickTestCase(caseTitle)
                .getCaseByTitle(caseTitle).shouldBe(Condition.visible.because("Quick test case " + caseTitle + " is not displayed"));
    }

    @Test
    public void checkCreateNewTestCase() {
        String caseTitle = "New test case";
        LoginStep.loginAndOpenRepository(loginPage, projectsPage, repositoryPage, email, password, projectCode)
                .openManualTestForm()
                .saveTestCase(TestCaseFactory.getTestCase(caseTitle))
                .openRepository()
                .openUnsortedCases()
                .getCaseByTitle(caseTitle).shouldBe(Condition.visible.because("New test case " + caseTitle + " is not displayed"));
    }

    @Test
    public void checkDeleteTestCase() {
        String suiteName = "Suite For Delete Case";
        String caseTitle = "Case to delete";
        int suiteId = SuiteStep.createSuiteViaApi(projectCode, suiteName);
        TestCaseStep.createTestCaseViaApi(projectCode, caseTitle, suiteId);
        LoginStep.loginAndOpenRepository(loginPage, projectsPage, repositoryPage, email, password, projectCode)
                .openSuite(suiteName)
                .selectCase(caseTitle)
                .deleteSelectedCases()
                .getCaseByTitle(caseTitle).shouldNotBe(
                        Condition.visible.because("Deleted test case " + caseTitle + " should not be displayed"));
    }

    @Test
    public void checkRestoreTestCase() {
        String suiteName = "Suite For Restore Case";
        String caseTitle = "Case to restore";
        int suiteId = SuiteStep.createSuiteViaApi(projectCode, suiteName);
        int caseId = TestCaseStep.createTestCaseViaApi(projectCode, caseTitle, suiteId);
        TestCaseStep.deleteTestCaseViaApi(projectCode, caseId);
        LoginStep.loginAndOpenRepository(loginPage, projectsPage, repositoryPage, email, password, projectCode)
                .openTrashBin()
                .restoreSelectedCase(caseTitle)
                .getCaseByTitle(caseTitle).shouldNotBe(
                        Condition.visible.because("Restored test case " + caseTitle + " should not be displayed in the trash bin"));
        repositoryPage.openRepository()
                .openSuite(suiteName)
                .getCaseByTitle(caseTitle).shouldBe(
                        Condition.visible.because("Restored test case " + caseTitle + " should be displayed in suite " + suiteName));
    }

    @AfterMethod(alwaysRun = true)
    public void deleteProject() {
        ProjectStep.cleanupProjectViaApi(projectCode);
    }
}
