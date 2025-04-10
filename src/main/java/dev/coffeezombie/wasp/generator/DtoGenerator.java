package dev.coffeezombie.wasp.generator;

import dev.coffeezombie.wasp.util.GeneratorStringUtil;
import dev.coffeezombie.wasp.util.model.GeneratorConfig;
import dev.coffeezombie.wasp.util.model.GeneratorEntity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.StringJoiner;

import static dev.coffeezombie.wasp.generator.ClassPropertyGenerator.generateDtoProperties;
import static dev.coffeezombie.wasp.generator.ClassPropertyGenerator.generateTypeImports;

public class DtoGenerator {

    public static String generateDto(GeneratorConfig config, GeneratorEntity entity) throws IOException {
        Path resourcePath = Paths.get("src", "main", "resources", "class-templates", "Dto.java.txt");
        String baseTemplate = Files.readString(resourcePath);

        baseTemplate = baseTemplate.replace("{PACKAGE_NAME}", config.getPackageName());
        baseTemplate = baseTemplate.replace("{IMPORTS}", generateTypeImports(entity, config.getDefaultPreferences(), false));

        // Annotations
        baseTemplate = baseTemplate.replace("{ANNOTATIONS}", getDtoAnnotations(config));

        baseTemplate = baseTemplate.replace("{PC_ENTITY_NAME}", entity.getName());

        // DTO Name
        baseTemplate = baseTemplate.replace("{PC_DTO_NAME}", entity.getName() + "Dto");

        // Properties and type imports
        baseTemplate = baseTemplate.replace("{DTO_PROPERTIES}", generateDtoProperties(entity));

        // Entity Conversion
        if(config.getDefaultPreferences().getModelMapper()){
            baseTemplate = baseTemplate.replace("{ENTITY_CONVERSION}", getEntityConversion(entity));
        }

        return GeneratorStringUtil.cleanOutput(baseTemplate);
    }

    private static String getDtoAnnotations(GeneratorConfig config) {
        var joiner = new StringJoiner("\n");
        joiner.setEmptyValue("");

        if(config.getDefaultPreferences().getJsonIgnoreUnknown())
            joiner.add("@JsonIgnoreProperties(ignoreUnknown = true)");
        return joiner.toString();
    }

    private static String getEntityConversion(GeneratorEntity entity){
        var joiner = new StringJoiner("\n");
        joiner.add("public " + entity.getName() + " toEntity() {");

        joiner.add("\t\t" + "var mapper = new ModelMapper();");
        joiner.add("\t\t" + "return mapper.map(this, " + entity.getName() + ".class);");

        joiner.add("\t}");
        return joiner.toString();
    }

}
