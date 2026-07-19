package api.adapters;

import api.models.testCase.TestCaseRq;
import api.models.testCase.TestCaseRs;
import dict.Urls;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class TestCaseAdapter extends BaseAdapter {

    public static TestCaseRs createTestCase(TestCaseRq rq, String code) {
        return given()
                .spec(spec)
                .pathParam("code", code)
                .body(rq)
                .log().all()
                .when()
                .post(Urls.CASE_BY_CODE_ENDPOINT)
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
                .get(Urls.CASE_BY_ID_ENDPOINT)
                .then()
                .body(matchesJsonSchemaInClasspath("schemas/get_test_case.schema.json"))
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
                .patch(Urls.CASE_BY_ID_ENDPOINT)
                .then()
                .body(matchesJsonSchemaInClasspath("schemas/id_result.schema.json"))
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
                .delete(Urls.CASE_BY_ID_ENDPOINT)
                .then()
                .body(matchesJsonSchemaInClasspath("schemas/id_result.schema.json"))
                .spec(ok200)
                .extract()
                .path("status");
    }
}
