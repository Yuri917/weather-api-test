package tests;

import com.fasterxml.jackson.databind.JsonNode;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import utils.JsonTestUtils;

import static io.restassured.RestAssured.given;
import static utils.JsonSoftAssertions.assertJsonEquals;

@Epic("Weather API")
@Feature("Positive API tests")
public class PositiveWeatherTest extends BaseApiTest {

    @ParameterizedTest(name = "Get current weather for {0}")
    @CsvSource({
            "Sochi,     sochi.json,     /expected/sochi_expected.json",
            "London,    london.json,    /expected/london_expected.json",
            "Moscow,    moscow.json,    /expected/moscow_expected.json",
            "Volgograd, volgograd.json, /expected/volgograd_expected.json"
    })
    @Story("Получение текущей погоды для города")
    @Severity(SeverityLevel.NORMAL)
    @Description("Получение текущей погоды для города и сравнение фактического JSON с ожидаемым.")
    void getCurrentWeatherForCityTest(String cityName, String actualFile, String resourcePath) {
        stubs.stubCurrentWeather(cityName, actualFile);

        Response response = given()
                .spec(spec)
                .queryParam("q", cityName)
                .when()
                .get("/v1/current.json");

        softAssertions.assertThat(response.statusCode())
                .as("HTTP статус код")
                .isEqualTo(200);

        JsonNode expected = JsonTestUtils.readJson(resourcePath);
        JsonNode actual = JsonTestUtils.readJson(response);

        assertJsonEquals(expected, actual, softAssertions);
        softAssertions.assertAll();
    }
}
