package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;

public class JsonTestUtils {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static JsonNode readJson(String resourcePath) {
        try (InputStream stream = JsonTestUtils.class.getResourceAsStream(resourcePath)) {
            if (stream == null) {
                throw new IllegalStateException("Не найден JSON: " + resourcePath);
            }

            return OBJECT_MAPPER.readTree(stream);

        } catch (IOException e) {
            throw new UncheckedIOException("Не удалось прочитать JSON: " + resourcePath, e);
        }
    }

    public static JsonNode readJson(Response response) {
        try {
            return OBJECT_MAPPER.readTree(response.asString());

        } catch (IOException e) {
            throw new UncheckedIOException(
                    "Не удалось прочитать JSON из HTTP response", e);
        }
    }
}