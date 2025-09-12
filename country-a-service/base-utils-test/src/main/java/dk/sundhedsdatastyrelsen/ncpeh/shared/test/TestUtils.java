package dk.sundhedsdatastyrelsen.ncpeh.shared.test;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class TestUtils {
    public static String resource(String uri, ClassLoader classLoaderWithResourcePresent) {
        try (var is = classLoaderWithResourcePresent.getResourceAsStream(uri)) {
            MatcherAssert.assertThat(is, Matchers.notNullValue());
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
