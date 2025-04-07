package dev.coffeezombie.wasp;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.coffeezombie.wasp.generator.ControllerGenerator;
import dev.coffeezombie.wasp.util.model.GeneratorConfig;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ControllerGeneratorTest {

    @Test
    public void test_generateController() throws IOException {

        Path resourcePath = Paths.get("src", "test", "resources", "templates", "PersonController.java.txt");
        String expectedOutput = Files.readString(resourcePath);

        Path jsonPath = Paths.get("src", "test", "resources", "templates", "full-config.json");
        String json = Files.readString(jsonPath);

        var obj = new ObjectMapper();
        GeneratorConfig config = obj.readValue(json, GeneratorConfig.class);

        String result = ControllerGenerator.generateController(config, config.getEntities().get(0));

        assertEquals(expectedOutput, result);
    }

    @Test
    public void test_generateControllerWithDoubleBarrelEntityName() throws IOException {

        Path resourcePath = Paths.get("src", "test", "resources", "templates", "PersonThingController.java.txt");
        String expectedOutput = Files.readString(resourcePath);

        Path jsonPath = Paths.get("src", "test", "resources", "templates", "full-config.json");
        String json = Files.readString(jsonPath);

        var obj = new ObjectMapper();
        GeneratorConfig config = obj.readValue(json, GeneratorConfig.class);

        String result = ControllerGenerator.generateController(config, config.getEntities().get(1));

        assertEquals(expectedOutput, result);
    }

}
