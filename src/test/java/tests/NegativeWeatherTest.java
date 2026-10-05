package tests;

import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;
import static io.restassured.RestAssured.given;

public class NegativeWeatherTest extends BaseApiTest {

    @Test
    public void apiKeyNotProvidedTest() {
        stubs.stubApiKeyNotProvided();

        given().spec(spec)
                .when()
                .queryParam("q", "Sochi")
                .get("/v1/current.json")
                .then()
                .statusCode(401)
                .body("error.code", equalTo(1002));
    }

    @Test
    public void qNotProvidedTest() {
        stubs.stubQNotProvided();

        given().spec(spec)
                .when()
                .get("/v1/current.json")
                .then()
                .statusCode(400)
                .body("error.code", equalTo(1003));
    }

    @Test
    public void invalidUrlTest() {
        stubs.stubInvalidUrl();

        given().spec(spec)
                .when()
                .get("/v1/wrong")
                .then()
                .statusCode(400)
                .body("error.code", equalTo(1005));
    }

    @Test
    public void apiKeyInvalidTest() {
        stubs.stubApiKeyInvalid();

        given().spec(spec)
                .when()
                .queryParam("key", "invalid-key")
                .queryParam("q", "Sochi")
                .get("/v1/current.json")
                .then()
                .statusCode(401)
                .body("error.code", equalTo(2006));
    }
}
