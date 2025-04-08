package dev.coffeezombie.wasp.generator;

import dev.coffeezombie.wasp.util.GeneratorStringUtil;
import dev.coffeezombie.wasp.util.model.GeneratorDefaultPreference;
import dev.coffeezombie.wasp.util.model.GeneratorEntity;
import dev.coffeezombie.wasp.util.model.GeneratorEntityField;

import java.util.ArrayList;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class DtoGenerator {

    public static String generateDto(GeneratorDefaultPreference defaultPreferences, GeneratorEntity entity, String basePackageName) {
        String dtoName = entity.getName() + "Dto";
        var dtoBuilder = new StringJoiner("\n");

        var types = entity.getProperties()
                .stream()
                .map(GeneratorEntityField::getType)
                .collect(Collectors.toSet());

        dtoBuilder.add("package " + basePackageName + ".dto;\n");

        dtoBuilder.add("import " + basePackageName + ".entity." + entity.getName() + ";");

        dtoBuilder.add(generateAllImports(types, defaultPreferences));

        // Annotations
        dtoBuilder.add("@Data");
        dtoBuilder.add("@NoArgsConstructor");
        if(defaultPreferences.getJsonIgnoreUnknown())
            dtoBuilder.add("@JsonIgnoreProperties(ignoreUnknown = true)");

        // Class
        dtoBuilder.add("public class " + dtoName + " {");

        // Properties
        dtoBuilder.add(generateProperties(entity));

        // Convert from Entity
        var conversion = getEntityConversion(entity, dtoName);
        dtoBuilder.add(conversion);

        // Class close
        dtoBuilder.add("}");

        return GeneratorStringUtil.cleanOutput(dtoBuilder.toString());
    }

    public static String getEntityConversion(GeneratorEntity entity, String dtoName){
        var joiner = new StringJoiner("\n");

        joiner.add("");
        joiner.add("\tpublic " + entity.getName() + " toEntity() {");

        joiner.add("\t\t" + "var mapper = new ModelMapper();");
        joiner.add("\t\t" + "return mapper.map(this, " + entity.getName() + ".class);");

        joiner.add("\t}");
        joiner.add("");

        return joiner.toString();
    }

    public static String generateUniversalImports(GeneratorDefaultPreference preferences){
        var packages = new ArrayList<String>();

        // Default
        packages.add("lombok.Data");
        packages.add("lombok.NoArgsConstructor");

        if(preferences.getJsonIgnoreUnknown())
            packages.add("com.fasterxml.jackson.annotation.JsonIgnoreProperties");

        if(preferences.getModelMapper())
            packages.add("org.modelmapper.ModelMapper");

        return GeneratorStringUtil.generateImports(packages);
    }

    public static String generateAllImports(Set<String> types, GeneratorDefaultPreference preference){
        var joiner = new StringJoiner("\n");

        // Base package imports
        joiner.add(generateUniversalImports(preference));

        joiner.add(""); // Space between package and language imports

        var languageImports = new ArrayList<String>();
        if(types.contains("BigDecimal"))
            languageImports.add("java.math.BigDecimal");

        if(types.contains("BigInteger"))
            languageImports.add("java.math.BigInteger");

        if(types.contains("Date"))
            languageImports.add("java.util.Date");

        joiner.add(GeneratorStringUtil.generateImports(languageImports));

        if(!languageImports.isEmpty())
            joiner.add("");

        return joiner.toString();
    }

    public static String generateProperties(GeneratorEntity entity){
        var properties = new StringJoiner("\n\t");
        properties.add("\t");

        for(var property : entity.getProperties()){
            properties.add("private " + property.getType() + " " + property.getName() + ";");
        }

        return properties.toString();
    }


}
