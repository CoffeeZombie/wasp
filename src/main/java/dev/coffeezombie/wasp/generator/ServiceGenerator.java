package dev.coffeezombie.wasp.generator;

import dev.coffeezombie.wasp.util.StringUtil;
import dev.coffeezombie.wasp.util.model.GeneratorConfig;
import dev.coffeezombie.wasp.util.model.GeneratorEntity;

import java.util.ArrayList;
import java.util.StringJoiner;

public class ServiceGenerator {

    public final static String INDENT_ONE = "\t";
    public final static String INDENT_TWO = "\t\t";
    public final static String INDENT_THREE = "\t\t\t";

    public static String generateService(GeneratorConfig config, GeneratorEntity entity){
        var serviceBuilder = new StringJoiner("\n");

        // Package Name
        serviceBuilder.add("package " + config.getPackageName() + ".service;\n");

        // Imports
        serviceBuilder.add(generateAllImports(config, entity));

        // Annotations
        serviceBuilder.add("@Slf4j");
        serviceBuilder.add("@Service");
        serviceBuilder.add("@RequiredArgsConstructor");

        // Class
        serviceBuilder.add("public class PersonService {");

        // Repo Import
        serviceBuilder.add("");
        serviceBuilder.add("\tprivate final " + entity.getName() + "Repository " + entity.getName().toLowerCase() + "Repository;");

        // Methods
        var methods = new StringJoiner("\n\t");
        methods.add("");

        methods.add("public List<PersonDto> findAll() {");
        methods.add(INDENT_ONE + "return personRepository.findAll()");
        methods.add(INDENT_THREE + ".stream()");
        methods.add(INDENT_THREE + ".map(PersonDto::new)");
        methods.add(INDENT_THREE + ".collect(Collectors.toList());");
        methods.add("}");
        methods.add("");
        methods.add("public PersonDto findById(Long id) {");
        methods.add(INDENT_ONE + "var person = personRepository.findById(id)");
        methods.add(INDENT_THREE + ".orElseThrow(() -> new RuntimeException(\"Unable to find person by id \" + id));");
        methods.add("");
        methods.add(INDENT_ONE + "return new PersonDto(person);");
        methods.add("}");
        methods.add("");
        methods.add("public void deleteById(Long id) {");
        methods.add(INDENT_ONE + "personRepository.deleteById(id);");
        methods.add("}");
        methods.add("");
        methods.add("public PersonDto createOrUpdate(PersonDto dto) {");
        methods.add(INDENT_ONE + "var entity = personRepository.save(new Person(dto));");
        methods.add(INDENT_ONE + "return new PersonDto(entity);");
        methods.add("}");
        methods.add("");
        methods.add("public PersonDto update(PersonDto dto) {");
        methods.add(INDENT_ONE + "var entity = findById(dto.getId()); // Throws exception if not found - this is used as a check only");
        methods.add(INDENT_ONE + "var updated = personRepository.save(new Person(dto));");
        methods.add(INDENT_ONE + "return new PersonDto(updated);");
        methods.add("}");

        serviceBuilder.add(methods.toString());

        serviceBuilder.add("}");

        return StringUtil.cleanOutput(serviceBuilder.toString());
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

        return StringUtil.generateImports(packages);
    }

}
