package tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.RegisterExtension;
import stubs.WeatherStubs;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static com.github.tomakehurst.wiremock.junit5.WireMockExtension.newInstance;

public class BaseApiTest {

    // Один WireMock-сервер на все тесты класса.
    // static — потому что JUnit создаёт новый инстанс тест-класса на каждый тест,
    // без static сервер пересоздавался бы перед каждым тестом.
    @RegisterExtension
    static WireMockExtension wm = newInstance()
            .options(wireMockConfig().dynamicPort())
            .build();

    // RequestSpecification — переиспользуемый объект с настройками запроса.
    // Заполняется в @BeforeEach, потому что порт WireMock становится известен
    // только после старта сервера (runtime).
    protected RequestSpecification spec;
    protected WeatherStubs stubs;
//    protected ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        spec = new RequestSpecBuilder()
                .setBaseUri("http://localhost")
                .setPort(wm.getPort())
                .addFilter(new AllureRestAssured())
                .build();

        stubs = new WeatherStubs(wm);
//        mapper = new ObjectMapper();
    }
}