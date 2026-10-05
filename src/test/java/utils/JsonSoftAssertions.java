package utils;

import com.fasterxml.jackson.databind.JsonNode;
import org.assertj.core.api.SoftAssertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonSoftAssertions {

    private static final Logger log = LoggerFactory.getLogger(JsonSoftAssertions.class);

    public static void assertJsonEquals(
            JsonNode expected,
            JsonNode actual,
            SoftAssertions softAssertions) {

        recursiveComparison(expected, actual, "$", softAssertions);
    }

    private static void recursiveComparison(
            JsonNode expected,
            JsonNode actual,
            String path,
            SoftAssertions softAssertions) {

        expected.fieldNames().forEachRemaining(field -> {

            JsonNode expectedValue = expected.get(field);
            JsonNode actualValue = actual.get(field);
            String currentPath = path + "." + field;

            if (expectedValue.isObject()
                    && actualValue != null
                    && actualValue.isObject()) {

                recursiveComparison(expectedValue, actualValue, currentPath, softAssertions);

            } else {
                if (expectedValue.equals(actualValue)) {
                    log.info("{} OK | expected {} | actual={}",
                            currentPath,
                            expectedValue,
                            actualValue
                    );
                } else {
                    log.error("{} DIFFERENCE | expected {} | actual={}",
                            currentPath,
                            expectedValue,
                            actualValue
                    );
                }

                softAssertions.assertThat(actualValue).as(currentPath).isEqualTo(expectedValue);
            }
        });

        actual.fieldNames().forEachRemaining(field -> {

            if (!expected.has(field)) {
                String currentPath = path + "." + field;
                log.error("{} DIFFERENCE | expected=<missing> | actual = {}",
                        currentPath,
                        actual.get(field)
                );

                softAssertions.assertThat(expected.has(field))
                        .as(currentPath)
                        .isTrue();
            }
        });
    }
}
