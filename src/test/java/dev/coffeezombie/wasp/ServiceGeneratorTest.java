package dev.coffeezombie.wasp;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.coffeezombie.wasp.generator.ServiceGenerator;
import dev.coffeezombie.wasp.util.model.GeneratorConfig;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ServiceGeneratorTest {

    @Test
    public void test_generateService() throws IOException {

        Path resourcePath = Paths.get("src", "test", "resources", "templates", "PersonService.java.txt");
        String expectedOutput = Files.readString(resourcePath);

        Path jsonPath = Paths.get("src", "test", "resources", "templates", "full-config.json");
        String json = Files.readString(jsonPath);

        var obj = new ObjectMapper();
        GeneratorConfig config = obj.readValue(json, GeneratorConfig.class);

        String result = ServiceGenerator.generateService(config, config.getEntities().get(0));

        assertEquals(expectedOutput, result);
    }

    @Test
    public void test_generateServiceWithDoubleBarrelName() throws IOException {

        Path resourcePath = Paths.get("src", "test", "resources", "templates", "PersonThingService.java.txt");
        String expectedOutput = Files.readString(resourcePath);

        Path jsonPath = Paths.get("src", "test", "resources", "templates", "full-config.json");
        String json = Files.readString(jsonPath);

        var obj = new ObjectMapper();
        GeneratorConfig config = obj.readValue(json, GeneratorConfig.class);

        String result = ServiceGenerator.generateService(config, config.getEntities().get(1));

        assertEquals(expectedOutput, result);
    }

}
