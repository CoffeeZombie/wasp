package dev.coffeezombie.wasp.generator;

import dev.coffeezombie.wasp.util.GeneratorStringUtil;
import dev.coffeezombie.wasp.util.model.GeneratorConfig;
import dev.coffeezombie.wasp.util.model.GeneratorEntity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ServiceGenerator {

    public static String generateService(GeneratorConfig config, GeneratorEntity entity) throws IOException {
        Path resourcePath = Paths.get("src", "main", "resources", "class-templates", "Service.java.txt");
        String baseTemplate = Files.readString(resourcePath);

        // Package name and imports
        baseTemplate = baseTemplate.replace("{PC_SERVICE_NAME}", entity.getName() + "Service");
        baseTemplate = baseTemplate.replace("{PACKAGE_NAME}", config.getPackageName());

        // Entity
        baseTemplate = baseTemplate.replace("{PC_ENTITY_NAME}", entity.getName());
        baseTemplate = baseTemplate.replace("{KC_ENTITY_NAME}", GeneratorStringUtil.getLowerCaseName(entity.getName()));

        // DTO
        baseTemplate = baseTemplate.replace("{PC_DTO_NAME}", entity.getName() + "Dto");

        // Repo
        String repoName = entity.getName() + "Repository";
        baseTemplate = baseTemplate.replace("{PC_REPOSITORY_NAME}", repoName);
        baseTemplate = baseTemplate.replace("{CC_REPOSITORY_NAME}", GeneratorStringUtil.getCamelCaseName(repoName));

        return GeneratorStringUtil.cleanOutput(baseTemplate);
    }

}
