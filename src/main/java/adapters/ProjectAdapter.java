package adapters;

import api.models.project.ProjectRq;
import api.models.project.ProjectRs;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class ProjectAdapter extends BaseAdapter {

    public static ProjectRs createProject(ProjectRq rq) {
        return given()
                .spec(spec)
                .body(rq)
                .log().all()
                .when()
                .post("/project")
                .then()
                .log().all()
                .body(matchesJsonSchemaInClasspath("schemas/create_project.schema.json"))
                .spec(ok200)
                .extract()
                .as(ProjectRs.class);
    }

    public static ProjectRs getProject(String code) {
        return given()
                .spec(spec)
                .pathParam("code", code)
                .when()
                .get("/project/{code}")
                .then()
                .body(matchesJsonSchemaInClasspath("schemas/get_project.schema.json"))
                .spec(ok200)
                .extract()
                .as(ProjectRs.class);
    }

    public static boolean deleteProject(String code) {
        return given()
                .spec(spec)
                .pathParams("code", code)
                .log().all()
                .when()
                .delete("/project/{code}")
                .then()
                .log().all()
                .body(matchesJsonSchemaInClasspath("schemas/status_only.schema.json"))
                .spec(ok200)
                .extract()
                .path("status");
    }
}
