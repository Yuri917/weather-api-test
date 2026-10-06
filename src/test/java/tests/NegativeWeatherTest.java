package tests;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

import static io.restassured.RestAssured.given;
import static utils.JsonSoftAssertions.assertJsonEquals;

public class NegativeWeatherTest extends BaseApiTest {

    @Test
    void apiKeyNotProvidedTest() throws IOException {
        stubs.stubApiKeyNotProvided();

        var response = given()
                .spec(spec)
                .when()
                .queryParam("q", "Sochi")
                .get("/v1/current.json");

        softAssertions.assertThat(response.statusCode())
                .as("HTTP статус код")
                .isEqualTo(401);

        JsonNode expected;
        try (InputStream expectedStream = getClass().getResourceAsStream("/expected/error_1002_401_expected.json")) {
            if (expectedStream == null) {
                throw new IllegalStateException("Не найден expected JSON");
            }

            expected = objectMapper.readTree(expectedStream);
        }

        JsonNode actual = objectMapper.readTree(response.asInputStream());

        assertJsonEquals(
                expected,
                actual,
                softAssertions
        );

        softAssertions.assertAll();
    }

    @Test
    void qNotProvidedTest() throws IOException {
        stubs.stubQNotProvided();

        var response = given()
                .spec(spec)
                .when()
                .get("/v1/current.json");

        softAssertions.assertThat(response.statusCode())
                .as("HTTP статус код")
                .isEqualTo(400);

        JsonNode expected;
        try (InputStream expectedStream = getClass().getResourceAsStream("/expected/error_1003_400_expected.json")) {
            if (expectedStream == null) {
                throw new IllegalStateException("Не найден expected JSON");
            }

            expected = objectMapper.readTree(expectedStream);
        }

        JsonNode actual = objectMapper.readTree(response.asInputStream());

        assertJsonEquals(
                expected,
                actual,
                softAssertions
        );

        softAssertions.assertAll();
    }

    @Test
    void invalidUrlTest() throws IOException {
        stubs.stubInvalidUrl();

        var response = given()
                .spec(spec)
                .when()
                .get("/v1/wrong");

        softAssertions.assertThat(response.statusCode())
                .as("HTTP статус код")
                .isEqualTo(400);

        JsonNode expected;
        try (InputStream expectedStream = getClass().getResourceAsStream("/expected/error_1005_400_expected.json")) {
            if (expectedStream == null) {
                throw new IllegalStateException("Не найден expected JSON");
            }

            expected = objectMapper.readTree(expectedStream);
        }

        JsonNode actual = objectMapper.readTree(response.asInputStream());

        assertJsonEquals(
                expected,
                actual,
                softAssertions
        );

        softAssertions.assertAll();
    }

    @Test
    void apiKeyInvalidTest() throws IOException {
        stubs.stubApiKeyInvalid();

        var response = given()
                .spec(spec)
                .when()
                .queryParam("key", "invalid-key")
                .queryParam("q", "Sochi")
                .get("/v1/current.json");

        softAssertions.assertThat(response.statusCode())
                .as("HTTP статус код")
                .isEqualTo(401);

        JsonNode expected;
        try (InputStream expectedStream = getClass().getResourceAsStream("/expected/error_2006_401_expected.json")) {
            if (expectedStream == null) {
                throw new IllegalStateException("Не найден expected JSON");
            }

            expected = objectMapper.readTree(expectedStream);
        }

        JsonNode actual = objectMapper.readTree(response.asInputStream());

        assertJsonEquals(
                expected,
                actual,
                softAssertions
        );

        softAssertions.assertAll();
    }
}
