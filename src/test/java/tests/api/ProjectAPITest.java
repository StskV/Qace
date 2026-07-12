package tests.api;

import adapters.ProjectAdapter;
import api.models.project.ProjectRq;
import api.models.project.ProjectRs;
import com.github.javafaker.Faker;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class ProjectAPITest {

    private final Faker faker = new Faker();
    private final String CODE = "QA" + System.currentTimeMillis() % 100000000L;
    private final String TITLE = faker.company().name();
    private final String DESCRIPTION = faker.lorem().paragraph();
    private final String ACCESS = "none";
    private final String GROUP = faker.company().industry();

    @Test
    public void checkProjectCRUD() {
        SoftAssert softAssert = new SoftAssert();

        ProjectRq rq = ProjectRq.builder()
                .title(TITLE)
                .code(CODE)
                .description(DESCRIPTION)
                .access(ACCESS)
                .group(GROUP)
                .build();
        ProjectRs rs = ProjectAdapter.createProject(rq);
        softAssert.assertTrue(rs.status, "Status not received");
        softAssert.assertEquals(rs.result.code, CODE, "Created code mismatch");

        ProjectRs fetchedRs = ProjectAdapter.getProject(CODE);
        softAssert.assertTrue(fetchedRs.status, "Status failed");
        softAssert.assertEquals(fetchedRs.result.code, CODE, "Fetched code mismatch");
        softAssert.assertEquals(fetchedRs.result.title, TITLE, "Fetched title mismatch");

        boolean isDeleted = ProjectAdapter.deleteProject(CODE);
        softAssert.assertTrue(isDeleted, "Project not deleted");

        softAssert.assertAll();
    }
}
