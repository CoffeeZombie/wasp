package dev.coffeezombie.wasp.generator;

import dev.coffeezombie.wasp.util.GeneratorStringUtil;
import dev.coffeezombie.wasp.util.model.GeneratorConfig;
import dev.coffeezombie.wasp.util.model.GeneratorEntity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RepositoryGenerator {

    public static String generateRepository(GeneratorConfig config, GeneratorEntity entity) throws IOException {
        Path resourcePath = Paths.get("src", "main", "resources", "class-templates", "Repository.java.txt");
        String baseTemplate = Files.readString(resourcePath);

        baseTemplate = baseTemplate.replace("{PACKAGE_NAME}", config.getPackageName());
        baseTemplate = baseTemplate.replace("{PC_REPOSITORY_NAME}", entity.getName() + "Repository");
        baseTemplate = baseTemplate.replace("{PC_ENTITY_NAME}", entity.getName());

        return GeneratorStringUtil.cleanOutput(baseTemplate);
    }

}
