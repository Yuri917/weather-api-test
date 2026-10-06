package tests;

import com.fasterxml.jackson.databind.JsonNode;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import utils.JsonTestUtils;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static utils.JsonSoftAssertions.assertJsonEquals;

@Epic("Weather API")
@Feature("Negative API tests")
public class NegativeWeatherTest extends BaseApiTest {

    @Test
    @Story("API key отсутствует")
    @Severity(SeverityLevel.NORMAL)
    @Description("Проверка ошибки при отсутствии API key.")
    void apiKeyNotProvidedTest() throws IOException {
        stubs.stubApiKeyNotProvided();

        Response response = given()
                .spec(spec)
                .when()
                .queryParam("q", "Sochi")
                .get("/v1/current.json");

        softAssertions.assertThat(response.statusCode())
                .as("HTTP статус код")
                .isEqualTo(401);

        JsonNode expected = JsonTestUtils.readJson("/expected/error_1002_401_expected.json");
        JsonNode actual = JsonTestUtils.readJson(response);

        assertJsonEquals(expected, actual, softAssertions);
        softAssertions.assertAll();
    }

    @Test
    @Story("Параметр q отсутствует")
    @Severity(SeverityLevel.NORMAL)
    @Description("Проверка ошибки при отсутствии обязательного параметра q.")
    void qNotProvidedTest() throws IOException {
        stubs.stubQNotProvided();

        Response response = given()
                .spec(spec)
                .when()
                .get("/v1/current.json");

        softAssertions.assertThat(response.statusCode())
                .as("HTTP статус код")
                .isEqualTo(400);

        JsonNode expected = JsonTestUtils.readJson("/expected/error_1003_400_expected.json");
        JsonNode actual = JsonTestUtils.readJson(response);

        assertJsonEquals(expected, actual, softAssertions);
        softAssertions.assertAll();
    }

    @Test
    @Story("Некорректный URL")
    @Severity(SeverityLevel.NORMAL)
    @Description("Проверка ошибки при обращении к некорректному URL API.")
    void invalidUrlTest() throws IOException {
        stubs.stubInvalidUrl();

        Response response = given()
                .spec(spec)
                .when()
                .get("/v1/wrong");

        softAssertions.assertThat(response.statusCode())
                .as("HTTP статус код")
                .isEqualTo(400);

        JsonNode expected = JsonTestUtils.readJson("/expected/error_1005_400_expected.json");
        JsonNode actual = JsonTestUtils.readJson(response);

        assertJsonEquals(expected, actual, softAssertions);
        softAssertions.assertAll();
    }

    @Test
    @Story("Некорректный API key")
    @Severity(SeverityLevel.NORMAL)
    @Description("Проверка ошибки при использовании некорректного API key.")
    void apiKeyInvalidTest() throws IOException {
        stubs.stubApiKeyInvalid();

        Response response = given()
                .spec(spec)
                .when()
                .queryParam("key", "invalid-key")
                .queryParam("q", "Sochi")
                .get("/v1/current.json");

        softAssertions.assertThat(response.statusCode())
                .as("HTTP статус код")
                .isEqualTo(401);


        JsonNode expected = JsonTestUtils.readJson("/expected/error_2006_401_expected.json");
        JsonNode actual = JsonTestUtils.readJson(response);

        assertJsonEquals(expected, actual, softAssertions);
        softAssertions.assertAll();
    }
}
