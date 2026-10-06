//package utils;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import io.restassured.response.Response;
//import io.restassured.specification.RequestSpecification;
//import org.assertj.core.api.SoftAssertions;
//
//import java.io.IOException;
//import java.io.InputStream;
//
//import static io.restassured.RestAssured.given;
//import static utils.JsonSoftAssertions.assertJsonEquals;
//
//public class JsonTestUtils {
//
//    public static JsonNode assertResponseJson(
//            ObjectMapper objectMapper,
//            InputStream inputStream,
//            RequestSpecification spec,
//            SoftAssertions softAssertions) throws IOException {
//
//        Response response = given()
//                .spec(spec)
//                .queryParam("q", cityName)
//                .when()
//                .get("/v1/current.json");
//
//        softAssertions.assertThat(response.statusCode())
//                .as("HTTP статус код")
//                .isEqualTo(200);
//
//        JsonNode expected;
//        try (InputStream expectedStream = JsonTestUtils.class.getResourceAsStream("/expected/" + expectedFile)) {
//
//            if (expectedStream == null) {
//                throw new IllegalStateException("Не найден expected JSON");
//            }
//
//            expected = objectMapper.readTree(expectedStream);
//        }
//
//        JsonNode actual = objectMapper.readTree(response.asInputStream());
//
//        assertJsonEquals(
//                expected,
//                actual,
//                softAssertions
//        );
//
//        softAssertions.assertAll();
//
//
//        return null;
//    }
//}
//
