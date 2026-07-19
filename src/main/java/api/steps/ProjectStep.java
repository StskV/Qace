package api.steps;

import api.adapters.ProjectAdapter;
import api.models.project.ProjectRq;
import com.github.javafaker.Faker;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ProjectStep {

    private static final Faker faker = new Faker();

    @Step("Create project '{title}', '{code}' via API")
    public static void createProjectViaApi(String title, String code) {
        log.info("Creating project '{}', '{}' via API", title, code);
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
        log.info("Cleaning up project '{}' via API", code);
        try {
            ProjectAdapter.deleteProject(code);
        } catch (Throwable e) {
            log.warn("Cleanup skipped for project '{}': {}", code, e.getMessage());
        }
    }
}
