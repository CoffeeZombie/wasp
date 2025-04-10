package dev.coffeezombie.wasp.generator;

import dev.coffeezombie.wasp.util.GeneratorStringUtil;
import dev.coffeezombie.wasp.util.model.GeneratorConfig;
import dev.coffeezombie.wasp.util.model.GeneratorEntity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.StringJoiner;

import static dev.coffeezombie.wasp.generator.ClassPropertyGenerator.generateEntityProperties;
import static dev.coffeezombie.wasp.generator.ClassPropertyGenerator.generateTypeImports;

public class EntityGenerator {

    public static String generateEntity(GeneratorConfig config, GeneratorEntity entity) throws IOException {
        Path resourcePath = Paths.get("src", "main", "resources", "class-templates", "Entity.java.txt");
        String baseTemplate = Files.readString(resourcePath);

        // Package name and imports
        baseTemplate = baseTemplate.replace("{PACKAGE_NAME}", config.getPackageName());
        baseTemplate = baseTemplate.replace("{IMPORTS}", generateTypeImports(entity, config.getDefaultPreferences(), true));

        // Entity and ID Names
        baseTemplate = baseTemplate.replace("{PC_ENTITY_NAME}", entity.getName());
        baseTemplate = baseTemplate.replace("{CC_ENTITY_ID_NAME}", entity.getIdName());

        // Properties and type imports
        baseTemplate = baseTemplate.replace("{ENTITY_PROPERTIES}", generateEntityProperties(entity));

        // DTO Conversion
        if(config.getClasses().getDto()){
            baseTemplate = baseTemplate.replace("{PC_DTO_NAME}", entity.getName() + "Dto");

            if(config.getDefaultPreferences().getModelMapper()){
                baseTemplate = baseTemplate.replace("{DTO_CONVERSION}", getDtoConversion(entity));
            }
        }

        return GeneratorStringUtil.cleanOutput(baseTemplate);
    }

    private static String getDtoConversion(GeneratorEntity entity){
        var joiner = new StringJoiner("\n");
        joiner.add("public " + entity.getName() + "Dto toDto() {");

        joiner.add("\t\t" + "var mapper = new ModelMapper();");
        joiner.add("\t\t" + "return mapper.map(this, " + entity.getName() + "Dto.class);");

        joiner.add("\t}");
        return joiner.toString();
    }

}
