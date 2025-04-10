package dev.coffeezombie.wasp.generator;

import dev.coffeezombie.wasp.util.GeneratorStringUtil;
import dev.coffeezombie.wasp.util.model.GeneratorConfig;
import dev.coffeezombie.wasp.util.model.GeneratorEntity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static dev.coffeezombie.wasp.util.GeneratorStringUtil.getCamelCaseName;
import static dev.coffeezombie.wasp.util.GeneratorStringUtil.getLowerCaseName;

public class ControllerGenerator {

    public static String generateController(GeneratorConfig config, GeneratorEntity entity) throws IOException {
        Path resourcePath = Paths.get("src", "main", "resources", "class-templates", "Controller.java.txt");
        String baseTemplate = Files.readString(resourcePath);

        baseTemplate = baseTemplate.replace("{PACKAGE_NAME}", config.getPackageName());
        baseTemplate = baseTemplate.replace("{PC_CONTROLLER_NAME}", entity.getName() + "Controller");
        baseTemplate = baseTemplate.replace("{KC_ENTITY_NAME}", getLowerCaseName(entity.getName()));
        baseTemplate = baseTemplate.replace("{PC_SERVICE_NAME}", entity.getName() + "Service");
        baseTemplate = baseTemplate.replace("{CC_SERVICE_NAME}", getCamelCaseName(entity.getName()) + "Service");
        baseTemplate = baseTemplate.replace("{PC_DTO_NAME}", entity.getName() + "Dto");

        return GeneratorStringUtil.cleanOutput(baseTemplate);
    }

}
