package adapters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dict.Urls;
import utils.PropertyReader;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.LogConfig;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class BaseAdapter {

    protected static final String token = System.getProperty("token", PropertyReader.getProperty("token"));

    public static Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .setPrettyPrinting()
            .create();

    public static RequestSpecification spec = new RequestSpecBuilder()
            .setBaseUri(Urls.API_BASE_URL)
            .setBasePath(Urls.API_BASE_PATH)
            .setContentType(ContentType.JSON)
            .addHeader("Token", token)
            .setConfig(RestAssuredConfig.config()
                    .objectMapperConfig(ObjectMapperConfig.objectMapperConfig()
                            .defaultObjectMapperType(ObjectMapperType.GSON)
                            .gsonObjectMapperFactory((cls, charset) -> gson))
                    .logConfig(LogConfig.logConfig()
                            .enableLoggingOfRequestAndResponseIfValidationFails()))
            .build();

    public static ResponseSpecification ok200 = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .build();
}
