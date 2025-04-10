package dev.coffeezombie.wasp.generator;

import dev.coffeezombie.wasp.util.GeneratorStringUtil;
import dev.coffeezombie.wasp.util.model.GeneratorDefaultPreference;
import dev.coffeezombie.wasp.util.model.GeneratorEntity;
import dev.coffeezombie.wasp.util.model.GeneratorEntityField;

import java.util.ArrayList;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class ClassPropertyGenerator {

    public static String generateEntityProperties(GeneratorEntity entity) {
        var properties = new StringJoiner("\n\t");

        for(var property : entity.getProperties()){
            if(property.getName().equals(entity.getIdName())) continue;
            // TODO: JSON Properties
            properties.add("private " + property.getType() + " " + property.getName() + ";");
        }
        return properties.toString();
    }

    public static String generateDtoProperties(GeneratorEntity entity) {
        var properties = new StringJoiner("\n\t");

        for(var property : entity.getProperties()){
            // TODO: JSON Properties
            properties.add("private " + property.getType() + " " + property.getName() + ";");
        }
        return properties.toString();
    }

    public static String generateTypeImports(GeneratorEntity entity, GeneratorDefaultPreference preferences, boolean entityImports){
        var joiner = new StringJoiner("\n");

        var types = entity.getProperties().stream()
                .map(GeneratorEntityField::getType)
                .collect(Collectors.toSet());


        if(preferences.getJsonIgnoreUnknown() && !entityImports)
            joiner.add(GeneratorStringUtil.getImportString("com.fasterxml.jackson.annotation.JsonIgnoreProperties"));

        // Model Mapper
        if(preferences.getModelMapper())
            joiner.add(GeneratorStringUtil.getImportString("org.modelmapper.ModelMapper"));

        // Postgres Imports
        if(types.contains("JsonBinaryType") && entityImports)
            joiner.add(generatePostgresImports());

        joiner.add(""); // Space between package and language imports

        var languageImports = new ArrayList<String>();
        if(types.contains("BigDecimal"))
            languageImports.add("java.math.BigDecimal");

        if(types.contains("BigInteger"))
            languageImports.add("java.math.BigInteger");

        if(types.contains("Date"))
            languageImports.add("java.util.Date");

        joiner.add(GeneratorStringUtil.generateImports(languageImports));

        return joiner.toString();
    }

    public static String generatePostgresImports(){
        var packages = new ArrayList<String>();
        packages.add("org.hibernate.annotations.JdbcTypeCode");
        packages.add("org.hibernate.annotations.Type");
        packages.add("org.hibernate.type.SqlTypes");
        return GeneratorStringUtil.generateImports(packages);
    }

}
