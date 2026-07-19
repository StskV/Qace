package tests.ui;

import api.steps.ProjectStep;
import api.steps.SuiteStep;
import api.steps.TestCaseStep;
import com.codeborne.selenide.Condition;
import dto.SuiteFactory;
import dto.TestCaseFactory;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.base.AuthenticatedBaseTest;

@Owner("Satsiuk Viktoriya")
@Epic("Qase UI")
@Feature("Repository Page")
public class ProjectPageTest extends AuthenticatedBaseTest {

    private String projectCode;

    @BeforeMethod
    public void createProject() {
        projectCode = "QA" + (System.currentTimeMillis() % 100000);
        ProjectStep.createProjectViaApi("Project Page Test", projectCode);
    }

    @Test(
            description = "Verify a new suite can be created",
            testName = "Create suite",
            groups = "smoke"
    )
    @Story("Create suite")
    @Severity(SeverityLevel.CRITICAL)
    public void checkCreateNewSuite() {
        String suiteName = "New Suite";
        repositoryPage.openRepository(projectCode)
                .createSuite(SuiteFactory.getSuite(suiteName))
                .getSuiteByName(suiteName).shouldBe(
                        Condition.visible.because("Created suite " + suiteName + " is not displayed in the repository"));
    }

    @Test(
            description = "Verify a suite can be duplicated",
            testName = "Duplicate suite",
            groups = "regression"
    )
    @Story("Duplicate suite")
    @Severity(SeverityLevel.NORMAL)
    public void checkDuplicateSuite() {
        String suiteName = "Suite To Duplicate";
        SuiteStep.createSuiteViaApi(projectCode, suiteName);
        repositoryPage.openRepository(projectCode)
                .duplicateSuite(suiteName)
                .getPageBody().shouldHave(Condition.text("2 suites").because("Suite count should be 2 after duplicating"));
    }

    @Test(
            description = "Verify a suite can be deleted",
            testName = "Delete suite",
            groups = "regression"
    )
    @Story("Delete suite")
    @Severity(SeverityLevel.NORMAL)
    public void checkDeleteSuite() {
        String suiteName = "Suite To Delete";
        SuiteStep.createSuiteViaApi(projectCode, suiteName);
        repositoryPage.openRepository(projectCode)
                .deleteSuite(suiteName)
                .getSuiteByName(suiteName).shouldNotBe(Condition.visible.because("Suite " + suiteName + " was not deleted"));
    }

    @Test(
            description = "Verify a quick test case can be created",
            testName = "Create quick test case",
            groups = "smoke"
    )
    @Story("Create quick test case")
    @Severity(SeverityLevel.CRITICAL)
    public void checkCreateQuickTestCase() {
        String suiteName = "Suite For Quick Case";
        String caseTitle = "Quick case";
        SuiteStep.createSuiteViaApi(projectCode, suiteName);
        repositoryPage.openRepository(projectCode)
                .openSuite(suiteName)
                .createQuickTestCase(caseTitle)
                .getCaseByTitle(caseTitle).shouldBe(Condition.visible.because("Quick test case " + caseTitle + " is not displayed"));
    }

    @Test(
            description = "Verify a new test case can be created",
            testName = "Create test case",
            groups = "smoke"
    )
    @Story("Create test case")
    @Severity(SeverityLevel.CRITICAL)
    public void checkCreateNewTestCase() {
        String caseTitle = "New test case";
        repositoryPage.openRepository(projectCode)
                .openManualTestForm()
                .saveTestCase(TestCaseFactory.getTestCase(caseTitle))
                .openRepository()
                .openUnsortedCases()
                .getCaseByTitle(caseTitle).shouldBe(Condition.visible.because("New test case " + caseTitle + " is not displayed"));
    }

    @Test(
            description = "Verify a test case can be deleted",
            testName = "Delete test case",
            groups = "regression"
    )
    @Story("Delete test case")
    @Severity(SeverityLevel.NORMAL)
    public void checkDeleteTestCase() {
        String suiteName = "Suite For Delete Case";
        String caseTitle = "Case to delete";
        int suiteId = SuiteStep.createSuiteViaApi(projectCode, suiteName);
        TestCaseStep.createTestCaseViaApi(projectCode, caseTitle, suiteId);
        repositoryPage.openRepository(projectCode)
                .openSuite(suiteName)
                .selectCase(caseTitle)
                .deleteSelectedCases()
                .getCaseByTitle(caseTitle).shouldNotBe(
                        Condition.visible.because("Deleted test case " + caseTitle + " should not be displayed"));
    }

    @Test(
            description = "Verify a deleted test case can be restored from the trash bin back",
            testName = "Restore test case",
            groups = "regression"
    )
    @Story("Restore test case")
    @Severity(SeverityLevel.NORMAL)
    public void checkRestoreTestCase() {
        String suiteName = "Suite For Restore Case";
        String caseTitle = "Case to restore";
        int suiteId = SuiteStep.createSuiteViaApi(projectCode, suiteName);
        int caseId = TestCaseStep.createTestCaseViaApi(projectCode, caseTitle, suiteId);
        TestCaseStep.deleteTestCaseViaApi(projectCode, caseId);
        repositoryPage.openRepository(projectCode)
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
