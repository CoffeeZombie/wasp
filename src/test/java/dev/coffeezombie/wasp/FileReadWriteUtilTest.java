package dev.coffeezombie.wasp;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.coffeezombie.wasp.util.FileReadWriteUtil;
import dev.coffeezombie.wasp.util.model.GeneratorConfig;
import dev.coffeezombie.wasp.util.model.GeneratorEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileReadWriteUtilTest {

    static GeneratorConfig config = null;
    static GeneratorEntity entity = null;

    @BeforeAll
    public static void setup() throws IOException {
        Path jsonPath = Paths.get("src", "test", "resources", "templates", "full-config.json");
        String json = Files.readString(jsonPath);

        var obj = new ObjectMapper();
        config = obj.readValue(json, GeneratorConfig.class);

        entity = config.getEntities().get(0);
    }

    @Test
    public void test_getFilePathForEntity() throws IOException {
        String result = FileReadWriteUtil.getFilePath(config, entity.getName(), "entity");

        assertEquals("~/Dev/wasp-test/src/main/java/dev/coffeezombie/wasp_test/entity/Person.java", result);
    }

    @Test
    public void test_getFilePathForDto() throws IOException {
        String result = FileReadWriteUtil.getFilePath(config, entity.getName(), "dto");

        assertEquals("~/Dev/wasp-test/src/main/java/dev/coffeezombie/wasp_test/dto/PersonDto.java", result);
    }

    @Test
    public void test_getFilePathForService() throws IOException {
        String result = FileReadWriteUtil.getFilePath(config, entity.getName(), "service");

        assertEquals("~/Dev/wasp-test/src/main/java/dev/coffeezombie/wasp_test/service/PersonService.java", result);
    }

    @Test
    public void test_getFilePathForRepository() throws IOException {
        String result = FileReadWriteUtil.getFilePath(config, entity.getName(), "repository");

        assertEquals("~/Dev/wasp-test/src/main/java/dev/coffeezombie/wasp_test/repository/PersonRepository.java", result);
    }

    @Test
    public void test_getFilePathForController() throws IOException {
        String result = FileReadWriteUtil.getFilePath(config, entity.getName(), "controller");

        assertEquals("~/Dev/wasp-test/src/main/java/dev/coffeezombie/wasp_test/controller/PersonController.java", result);
    }


}
