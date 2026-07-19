package api.adapters;

import api.models.defect.DefectRq;
import api.models.defect.DefectRs;
import dict.Urls;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class DefectAdapter extends BaseAdapter {

    public static DefectRs createDefect(DefectRq rq, String code) {
        return given()
                .spec(spec)
                .pathParam("code", code)
                .body(rq)
                .log().all()
                .when()
                .post(Urls.DEFECT_BY_CODE_ENDPOINT)
                .then()
                .log().all()
                .body(matchesJsonSchemaInClasspath("schemas/create_defect.schema.json"))
                .spec(ok200)
                .extract()
                .as(DefectRs.class);
    }

    public static DefectRs getDefect(String code, int id) {
        return given()
                .spec(spec)
                .pathParam("code", code)
                .pathParam("id", id)
                .when()
                .get(Urls.DEFECT_BY_ID_ENDPOINT)
                .then()
                .body(matchesJsonSchemaInClasspath("schemas/get_defect.schema.json"))
                .spec(ok200)
                .extract()
                .as(DefectRs.class);
    }

    public static boolean updateDefect(DefectRq rq, String code, int id) {
        return given()
                .spec(spec)
                .pathParam("code", code)
                .pathParam("id", id)
                .body(rq)
                .when()
                .patch(Urls.DEFECT_BY_ID_ENDPOINT)
                .then()
                .body(matchesJsonSchemaInClasspath("schemas/id_result.schema.json"))
                .spec(ok200)
                .extract()
                .path("status");
    }

    public static boolean deleteDefect(String code, int id) {
        return given()
                .spec(spec)
                .pathParam("code", code)
                .pathParam("id", id)
                .when()
                .delete(Urls.DEFECT_BY_ID_ENDPOINT)
                .then()
                .body(matchesJsonSchemaInClasspath("schemas/id_result.schema.json"))
                .spec(ok200)
                .extract()
                .path("status");
    }
}
