package tests;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.io.InputStream;

import static io.restassured.RestAssured.given;
import static utils.JsonSoftAssertions.assertJsonEquals;

public class PositiveWeatherTest extends BaseApiTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @ParameterizedTest(name = "Get current weather for {0}")
    @CsvSource({
            "Sochi,     sochi.json,     sochi_expected.json",
            "London,    london.json,    london_expected.json",
            "Moscow,    moscow.json,    moscow_expected.json",
            "Volgograd, volgograd.json, volgograd_expected.json"
    })
    public void getCurrentWeatherForCityTest(String cityName, String actualFile, String expectedFile) throws IOException {
        stubs.stubCurrentWeather(cityName, actualFile);

        var response = given()
                .spec(spec)
                .queryParam("q", cityName)
                .when()
                .get("/v1/current.json");

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(response.statusCode())
                .as("HTTP статус код")
                .isEqualTo(200);

        // String actualJson = response.asString();
        // String expectedJson;
        JsonNode expected;
        try (InputStream expectedStream = getClass().getResourceAsStream("/expected/" + expectedFile)) {

            if (expectedStream == null) {
                throw new IllegalStateException("Не найден expected JSON");
            }

            expected = objectMapper.readTree(expectedStream);
            // expectedJson = new String(expectedStream.readAllBytes());
        }

        JsonNode actual = objectMapper.readTree(response.asInputStream());
        // JsonNode expected = objectMapper.readTree(expectedJson);

        assertJsonEquals(
                expected,
                actual,
                softAssertions
        );

        softAssertions.assertAll();
    }
}
