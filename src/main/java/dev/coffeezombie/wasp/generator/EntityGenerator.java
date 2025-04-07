package dev.coffeezombie.wasp.generator;

import dev.coffeezombie.wasp.util.StringUtil;
import dev.coffeezombie.wasp.util.model.GeneratorConfig;
import dev.coffeezombie.wasp.util.model.GeneratorDefaultPreference;
import dev.coffeezombie.wasp.util.model.GeneratorEntity;
import dev.coffeezombie.wasp.util.model.GeneratorEntityField;

import java.util.ArrayList;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class EntityGenerator {

    public static String generateEntity(GeneratorConfig config, GeneratorEntity entity){
        boolean generateDto = config.getClasses().getDto();

        var entityBuilder = new StringJoiner("\n");

        var types = entity.getProperties().stream()
                .map(GeneratorEntityField::getType)
                .collect(Collectors.toSet());

        // Package Name
        entityBuilder.add("package " + config.getPackageName() + ".entity;\n");

        // Import DTO
        if(generateDto)
            entityBuilder.add("import " + config.getPackageName() + ".dto." + entity.getName() + "Dto;");

        entityBuilder.add(generateAllImports(types, config.getDefaultPreferences()));

        // Annotations
        entityBuilder.add("@Entity");
        entityBuilder.add("@Data");
        entityBuilder.add("@NoArgsConstructor");

        // Class
        entityBuilder.add("public class " + entity.getName() + " {");

        // Properties
        var properties = getProperties(entity);
        entityBuilder.add(properties);

        // Convert from Dto
        if(generateDto)
            entityBuilder.add(getDtoConversion(entity));

        entityBuilder.add("\n}");

        return StringUtil.cleanOutput(entityBuilder.toString());
    }

    private static String getDtoConversion(GeneratorEntity entity){
        var joiner = new StringJoiner("\n");
        joiner.add("");
        joiner.add("\tpublic " + entity.getName() + "(" + entity.getName() + "Dto dto) {");

        joiner.add("\t\t" + "var mapper = new ModelMapper();");
        joiner.add("\t\t" + "return mapper.map(entity, " + entity.getName() + ".class)");

        joiner.add("\t}");
        return joiner.toString();
    }

    private static String getProperties(GeneratorEntity entity) {
        var properties = new StringJoiner("\n\t");
        properties.add("");

        properties.add("@Id");
        properties.add("@GeneratedValue(strategy = GenerationType.IDENTITY)");
        properties.add("private Long " + entity.getIdName() + ";");
        properties.add("");

        for(var property : entity.getProperties()){
            if(property.getName().equals(entity.getIdName())) continue;
            properties.add("private " + property.getType() + " " + property.getName() + ";");
        }
        return properties.toString();
    }

    public static String generateUniversalImports(GeneratorDefaultPreference preferences){
        var packages = new ArrayList<String>();

        // Default
        packages.add("jakarta.persistence.GeneratedValue");
        packages.add("jakarta.persistence.GenerationType");
        packages.add("jakarta.persistence.Id");
        packages.add("lombok.Data");
        packages.add("lombok.NoArgsConstructor");

        if(preferences.getModelMapper())
            packages.add("org.modelmapper.ModelMapper");

        return StringUtil.generateImports(packages);
    }

    public static String generatePostgresImports(){
        var packages = new ArrayList<String>();
        packages.add("org.hibernate.annotations.JdbcTypeCode");
        packages.add("org.hibernate.annotations.Type");
        packages.add("org.hibernate.type.SqlTypes");
        return StringUtil.generateImports(packages);
    }

    public static String generateAllImports(Set<String> types, GeneratorDefaultPreference preference){
        var joiner = new StringJoiner("\n");

        // Base package imports
        joiner.add(generateUniversalImports(preference));

        // Postgres Imports
        if(types.contains("JsonBinaryType"))
            joiner.add(generatePostgresImports());

        joiner.add(""); // Space between package and language imports

        var languageImports = new ArrayList<String>();
        if(types.contains("BigDecimal"))
            languageImports.add("java.Math.BigDecimal");

        if(types.contains("BigInteger"))
            languageImports.add("java.Math.BigInteger");

        if(types.contains("Date"))
            languageImports.add("java.util.Date");

        joiner.add(StringUtil.generateImports(languageImports));

        if(!languageImports.isEmpty())
            joiner.add("");

        return joiner.toString();
    }

}
