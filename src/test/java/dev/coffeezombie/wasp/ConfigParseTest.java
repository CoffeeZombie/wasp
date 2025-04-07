package dev.coffeezombie.wasp;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.coffeezombie.wasp.generator.DtoGenerator;
import dev.coffeezombie.wasp.util.model.GeneratorConfig;
import dev.coffeezombie.wasp.util.model.GeneratorEntity;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConfigParseTest {

    @Test
    public void test_ConfigValuesParsed() throws IOException {

        Path jsonPath = Paths.get("src", "test", "resources", "templates", "full-config.json");
        String json = Files.readString(jsonPath);

        var obj = new ObjectMapper();
        GeneratorConfig config = obj.readValue(json, GeneratorConfig.class);

        assertEquals("~/Dev/wasp-test", config.getProjectLocation());
        assertEquals("dev.coffeezombie.wasp_test", config.getPackageName());
        assertEquals(true, config.getOverwriteFiles());

        var classes = config.getClasses();
        assertEquals(true, classes.getEntity());
        assertEquals(true, classes.getDto());
        assertEquals(true, classes.getRepository());
        assertEquals(true, classes.getService());
        assertEquals(true, classes.getController());
        assertEquals(true, classes.getTests());

        var defaultPreferences = config.getDefaultPreferences();
        assertEquals(true, defaultPreferences.getJsonIgnoreUnknown());
        assertEquals(true, defaultPreferences.getModelMapper());

        var entities = config.getEntities();

        GeneratorEntity e1 = entities.get(0);
        assertEquals("Person", e1.getName());
        assertEquals("id", e1.getIdName());
        assertEquals(15, e1.getProperties().size());

        GeneratorEntity e2 = entities.get(1);
        assertEquals("PersonThing", e2.getName());
        assertEquals("id", e2.getIdName());
        assertEquals(3, e2.getProperties().size());

    }

}
