package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import dk.sundhedsdatastyrelsen.ncpeh.authentication.bootstraptoken.BootstrapTokenTest;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TestUtils {
    public static String resource(String uri) {
        try (var is = BootstrapTokenTest.class.getClassLoader().getResourceAsStream(uri)) {
            assertThat(is, notNullValue());
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
