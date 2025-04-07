package dev.coffeezombie.wasp.generator;

import dev.coffeezombie.wasp.util.GeneratorStringUtil;
import dev.coffeezombie.wasp.util.model.GeneratorConfig;
import dev.coffeezombie.wasp.util.model.GeneratorEntity;

import java.util.ArrayList;
import java.util.StringJoiner;

import static dev.coffeezombie.wasp.util.GeneratorStringUtil.getCamelCaseEntityName;

public class ServiceGenerator {

    public final static String INDENT_ONE = "\t";
    public final static String INDENT_TWO = "\t\t";
    public final static String INDENT_THREE = "\t\t\t";

    public static String generateService(GeneratorConfig config, GeneratorEntity entity){
        var serviceBuilder = new StringJoiner("\n");

        String repoName = getCamelCaseEntityName(entity.getName()) + "Repository";
        String dtoName = entity.getName() + "Dto";
        String ccEntityName = getCamelCaseEntityName(entity.getName());

        // Package Name
        serviceBuilder.add("package " + config.getPackageName() + ".service;\n");

        // Imports
        serviceBuilder.add(generateAllImports(config, entity));

        // Annotations
        serviceBuilder.add("@Slf4j");
        serviceBuilder.add("@Service");
        serviceBuilder.add("@RequiredArgsConstructor");

        // Class
        serviceBuilder.add("public class " + entity.getName() + "Service {");

        // Repo Import
        serviceBuilder.add("");
        serviceBuilder.add("\tprivate final " + entity.getName() + "Repository " + ccEntityName + "Repository;");

        // Methods
        var methods = new StringJoiner("\n\t");
        methods.add("");

        methods.add("public List<" + dtoName + "> findAll() {");
        methods.add(INDENT_ONE + "return " + repoName + ".findAll()");
        methods.add(INDENT_THREE + ".stream()");
        methods.add(INDENT_THREE + ".map(" + dtoName + "::new)");
        methods.add(INDENT_THREE + ".collect(Collectors.toList());");
        methods.add("}");
        methods.add("");
        methods.add("public " + dtoName + " findById(Long id) {");
        methods.add(INDENT_ONE + "var " + ccEntityName + " = " + repoName + ".findById(id)");
        methods.add(INDENT_THREE + ".orElseThrow(() -> new RuntimeException(\"Unable to find " + ccEntityName + " by id \" + id));");
        methods.add("");
        methods.add(INDENT_ONE + "return new " + dtoName + "(" + ccEntityName + ");");
        methods.add("}");
        methods.add("");
        methods.add("public void deleteById(Long id) {");
        methods.add(INDENT_ONE + repoName + ".deleteById(id);");
        methods.add("}");
        methods.add("");
        methods.add("public " + dtoName + " createOrUpdate(" + dtoName + " dto) {");
        methods.add(INDENT_ONE + "var entity = " + repoName + ".save(new " + entity.getName() + "(dto));");
        methods.add(INDENT_ONE + "return new " + dtoName + "(entity);");
        methods.add("}");
        methods.add("");
        methods.add("public " + dtoName + " update(" + dtoName + " dto) {");
        methods.add(INDENT_ONE + "var entity = findById(dto.getId()); // Throws exception if not found - this is used as a check only");
        methods.add(INDENT_ONE + "var updated = " + repoName + ".save(new " + entity.getName() + "(dto));");
        methods.add(INDENT_ONE + "return new " + dtoName + "(updated);");
        methods.add("}");

        serviceBuilder.add(methods.toString());

        serviceBuilder.add("}");

        return GeneratorStringUtil.cleanOutput(serviceBuilder.toString());
    }

    public static String generateAllImports(GeneratorConfig config, GeneratorEntity entity){
        String packageImports = generatePackageImports(config, entity);

        // Language Imports
        var joiner = new StringJoiner("\n");
        joiner.add(packageImports);
        joiner.add("");
        joiner.add("import java.util.List;");
        joiner.add("import java.util.stream.Collectors;");
        joiner.add("");

        return joiner.toString();
    }

    public static String generatePackageImports(GeneratorConfig config, GeneratorEntity entity){
        var packages = new ArrayList<String>();

        String basePackage = config.getPackageName();

        if(config.getClasses().getDto())
            packages.add(basePackage + ".dto." + entity.getName() + "Dto");

        packages.add(basePackage + ".entity." + entity.getName());
        packages.add(basePackage + ".repository." + entity.getName() + "Repository");

        packages.add("lombok.RequiredArgsConstructor");
        packages.add("lombok.extern.slf4j.Slf4j");
        packages.add("org.springframework.stereotype.Service");

        return GeneratorStringUtil.generateImports(packages);
    }

}
