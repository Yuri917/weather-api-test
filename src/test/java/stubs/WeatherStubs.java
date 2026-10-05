package stubs;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class WeatherStubs {
    private static final String CURRENT_WEATHER_PATH = "/v1/current.json";
    private final WireMockExtension wireMock;

    public WeatherStubs(WireMockExtension wireMock) {
        this.wireMock = wireMock;
    }

    // позитивный стаб
    public void stubCurrentWeather(String city, String file) {
        wireMock.stubFor(get(urlPathEqualTo(CURRENT_WEATHER_PATH))
                .withQueryParam("q", equalTo(city))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile(file)));
    }

    // негативные стабы

    // 1002
    public void stubApiKeyNotProvided() {
        wireMock.stubFor(get(urlPathEqualTo(CURRENT_WEATHER_PATH))
                .withQueryParam("key", absent())
                .withQueryParam("q", matching(".+"))
                .willReturn(aResponse()
                        .withStatus(401)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("error_1002_401.json")));
    }

    // 1003
    public void stubQNotProvided() {
        wireMock.stubFor(get(urlPathEqualTo(CURRENT_WEATHER_PATH))
                .withQueryParam("q", absent())
                .willReturn(aResponse()
                        .withStatus(400)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("error_1003_400.json")));
    }

    // 1005
    public void stubInvalidUrl() {
        wireMock.stubFor(get(urlPathEqualTo("/v1/wrong"))
                .willReturn(aResponse()
                        .withStatus(400)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("error_1005_400.json")));
    }

    // 2006
    public void stubApiKeyInvalid() {
        wireMock.stubFor(get(urlPathEqualTo(CURRENT_WEATHER_PATH))
                .withQueryParam("key", equalTo("invalid-key"))
                .withQueryParam("q", matching(".+"))
                .willReturn(aResponse()
                        .withStatus(401)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("error_2006_401.json")));
    }
}
