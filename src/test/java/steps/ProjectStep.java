package steps;

import adapters.ProjectAdapter;
import api.models.project.ProjectRq;
import com.github.javafaker.Faker;
import io.qameta.allure.Step;

public class ProjectStep {

    private static final Faker faker = new Faker();

    @Step("Create project {title}, '{code}' via API")
    public static void createProjectViaApi(String title, String code) {
        ProjectRq rq = ProjectRq.builder()
                .title(title)
                .code(code)
                .description(faker.lorem().sentence())
                .access("none")
                .group(faker.company().industry())
                .build();
        ProjectAdapter.createProject(rq);
    }

    @Step("Clean up project '{code}' via API")
    public static void cleanupProjectViaApi(String code) {
        try {
            ProjectAdapter.deleteProject(code);
        } catch (Throwable e) {
        }
    }
}
