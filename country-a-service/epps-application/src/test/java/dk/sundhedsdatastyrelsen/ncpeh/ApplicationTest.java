package dk.sundhedsdatastyrelsen.ncpeh;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource("/application-test.properties")
class ApplicationTest {
    @Test
    void contextLoads() {
        // This test verifies that the Spring application context loads successfully.
        // It ensures all required beans are properly configured and wired together.
        // If there are any configuration issues, the test will fail during context initialization.
    }
}
