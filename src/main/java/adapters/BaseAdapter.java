package adapters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import utils.PropertyReader;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class BaseAdapter {

    protected static final String token = System.getProperty("token", PropertyReader.getProperty("token"));

    public static Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .setPrettyPrinting()
            .create();

    public static RequestSpecification spec = new RequestSpecBuilder()
            .setBaseUri("https://api.qase.io")
            .setBasePath("/v1")
            .setContentType(ContentType.JSON)
            .addHeader("Token", token)
            .build();

    public static ResponseSpecification ok200 = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .build();
}
