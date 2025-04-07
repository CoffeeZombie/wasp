package dev.coffeezombie.wasp;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.coffeezombie.wasp.generator.RepositoryGenerator;
import dev.coffeezombie.wasp.util.model.GeneratorConfig;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RepositoryGeneratorTest {

    @Test
    public void test_generateDto() throws IOException {

        Path resourcePath = Paths.get("src", "test", "resources", "templates", "PersonRepository.java.txt");
        String expectedOutput = Files.readString(resourcePath);

        Path jsonPath = Paths.get("src", "test", "resources", "templates", "full-config.json");
        String json = Files.readString(jsonPath);

        var obj = new ObjectMapper();
        GeneratorConfig config = obj.readValue(json, GeneratorConfig.class);

        String result = RepositoryGenerator.generateRepository("dev.coffeezombie.wasp", config.getEntities().get(0));

        assertEquals(expectedOutput, result);
    }

}
