package dk.sundhedsdatastyrelsen.ncpeh.base.utils.test;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;

public class TestUtils {

    public static String slurp(Path filePath){
        try {
            return slurp(Files.newInputStream(filePath));
        } catch (IOException e) {
            throw new IllegalArgumentException(String.format("Cannot parse file on path: %s", filePath.toString()), e);
        }
    }

    public static String slurp(URL url){
        try {
            return slurp(url.openStream());
        } catch (IOException e) {
            throw new IllegalArgumentException(String.format("Cannot parse url: %s", url.toString()), e);
        }
    }

    public static String slurp(InputStream inputStream){
        try {
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e){
            throw new IllegalArgumentException("Cannot parse file", e);
        }
    }

    /**
     * Get resource as string, using the default classloader
     * Overloads with overwriting classLoader, if desired
     * @param uri - Path to resource for current classloader
     * @return Content of file in UTF-8
     */
    public static String resource(String uri) {
        var classLoader = Thread.currentThread().getContextClassLoader();
        return resource(uri,classLoader);
    }

    public static String resource(String uri, ClassLoader classLoaderWithResourcePresent) {
        var is = classLoaderWithResourcePresent.getResourceAsStream(uri);
        MatcherAssert.assertThat(is, Matchers.notNullValue());
        return slurp(is);
    }
}
