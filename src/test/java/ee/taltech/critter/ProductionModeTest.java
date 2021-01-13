package ee.taltech.critter;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ProductionModeTest {
    @Test
    void check() throws IOException {
        String currentPath = System.getProperty("user.dir");
        String configPath = currentPath + "/src/main/resources/application.yaml";
        List<String> s = Files.readAllLines(Paths.get(configPath));
        assertTrue(s.get(6).endsWith("\"h2\""));
    }
}
