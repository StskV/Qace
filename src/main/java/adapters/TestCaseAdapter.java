package adapters;

import api.models.testCase.TestCaseRq;
import api.models.testCase.TestCaseRs;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class TestCaseAdapter extends BaseAdapter {

    public static TestCaseRs createTestCase(TestCaseRq rq, String code) {
        return given()
                .spec(spec)
                .body(rq)
                .log().all()
                .when()
                .post("/case/" + code)
                .then()
                .log().all()
                .body(matchesJsonSchemaInClasspath("schemas/create_test_case.schema.json"))
                .spec(ok200)
                .extract()
                .as(TestCaseRs.class);
    }

    public static TestCaseRs getTestCase(String code, int id) {
        return given()
                .spec(spec)
                .pathParam("code", code)
                .pathParam("id", id)
                .when()
                .get("/case/{code}/{id}")
                .then()
                .spec(ok200)
                .extract()
                .as(TestCaseRs.class);
    }

    public static boolean updateTestCase(TestCaseRq rq, String code, int id) {
        return given()
                .spec(spec)
                .pathParam("code", code)
                .pathParam("id", id)
                .body(rq)
                .when()
                .patch("/case/{code}/{id}")
                .then()
                .spec(ok200)
                .extract()
                .path("status");
    }

    public static boolean deleteTestCase(String code, int id) {
        return given()
                .spec(spec)
                .pathParam("code", code)
                .pathParam("id", id)
                .when()
                .delete("/case/{code}/{id}")
                .then()
                .spec(ok200)
                .extract()
                .path("status");
    }
}
