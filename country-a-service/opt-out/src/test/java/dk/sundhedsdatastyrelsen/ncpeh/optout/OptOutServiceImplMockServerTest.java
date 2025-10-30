package dk.sundhedsdatastyrelsen.ncpeh.optout;

import com.fasterxml.jackson.databind.ObjectMapper;
import dk.sundhedsdatastyrelsen.ncpeh.base.utils.test.TestUtils;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

// The default lifecycle behaviour is "PER_METHOD", i.e., the test runner instantiates the class for each method.
// With "PER_CLASS" we can share state between the tests (e.g. ksPath, tsPath).
// PER_METHOD Try to see if this fixes it.
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class OptOutServiceImplMockServerTest {

    private MockWebServer server;
    private OptOutServiceImpl service;
    private final ObjectMapper json = new ObjectMapper();
    private String ksPath;
    private String tsPath;

    private String copy(String resource, Path dir) throws IOException {
        try (var is = TestUtils.resource(resource)) {
            var path = dir.resolve(resource);
            Files.copy(is, path);
            return path.toAbsolutePath().toString();
        }
    }

    @BeforeEach
    void setUp(@TempDir Path tempDir) throws Exception {
        server = new MockWebServer();
        server.start();

        this.ksPath = copy("opt-out-keystore.p12", tempDir);
        this.tsPath = copy("opt-out-truststore.p12", tempDir);

        var baseUrl = server.url("/").toString().replaceAll("/+$", "");
        var config = new OptOutService.Config(
                baseUrl,
                ksPath,
                "changeit",
                tsPath,
                "changeit"
        );
        service = new OptOutServiceImpl(config);
    }

    @AfterEach
    void tearDown() throws Exception {
        if (service != null) service.close();
        if (server != null) server.shutdown();
    }

    @Test
    void happyPathReturnsExpectedResponse() throws Exception {
        var mockResponse = Map.of(
                OptOutService.Service.EPRESCRIPTION, false,
                OptOutService.Service.PATIENT_SUMMARY, true);

        var okResponse = new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody(json.writeValueAsString(mockResponse));
        server.enqueue(okResponse);
        server.enqueue(okResponse);

        var epr = service.hasOptedOut("0101019999", OptOutService.Service.EPRESCRIPTION);
        var ps = service.hasOptedOut("0101019999", OptOutService.Service.PATIENT_SUMMARY);

        assertThat(epr, is(false));
        assertThat(ps, is(true));
    }

    @Test
    void serverReturns400() {
        server.enqueue(new MockResponse().setResponseCode(400).setBody("Bad request"));

        var ex = assertThrows(
                OptOutServiceException.class,
                () -> service.hasOptedOut("0101019999", OptOutService.Service.EPRESCRIPTION));
        assertThat(ex.getMessage(), containsString("Bad response from opt-out lookup service"));
    }

    @Test
    void serverUnavailable() throws Exception {
        server.shutdown();
        assertThrows(
                OptOutServiceException.class,
                () -> service.hasOptedOut("0101019999", OptOutService.Service.EPRESCRIPTION));
    }
}
