package adapters;

import api.models.testSuite.TestSuiteRq;
import api.models.testSuite.TestSuiteRs;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class TestSuiteAdapter extends BaseAdapter {

    public static TestSuiteRs createTestSuite(TestSuiteRq rq, String code) {
        return given()
                .spec(spec)
                .body(rq)
                .log().all()
                .when()
                .post("/suite/" + code)
                .then()
                .log().all()
                .body(matchesJsonSchemaInClasspath("schemas/create_test_suite.schema.json"))
                .spec(ok200)
                .extract()
                .as(TestSuiteRs.class);
    }

    public static TestSuiteRs getTestSuite(String code, int id) {
        return given()
                .spec(spec)
                .pathParam("code", code)
                .pathParam("id", id)
                .when()
                .get("/suite/{code}/{id}")
                .then()
                .body(matchesJsonSchemaInClasspath("schemas/get_test_suite.schema.json"))
                .spec(ok200)
                .extract()
                .as(TestSuiteRs.class);
    }

    public static boolean updateTestSuite(TestSuiteRq rq, String code, int id) {
        return given()
                .spec(spec)
                .pathParam("code", code)
                .pathParam("id", id)
                .body(rq)
                .when()
                .patch("/suite/{code}/{id}")
                .then()
                .body(matchesJsonSchemaInClasspath("schemas/id_result.schema.json"))
                .spec(ok200)
                .extract()
                .path("status");
    }

    public static boolean deleteTestSuite(String code, int id) {
        return given()
                .spec(spec)
                .pathParam("code", code)
                .pathParam("id", id)
                .when()
                .delete("/suite/{code}/{id}")
                .then()
                .body(matchesJsonSchemaInClasspath("schemas/id_result.schema.json"))
                .spec(ok200)
                .extract()
                .path("status");
    }
}
