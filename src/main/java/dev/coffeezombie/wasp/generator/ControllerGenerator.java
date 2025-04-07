package dev.coffeezombie.wasp.generator;

import dev.coffeezombie.wasp.util.GeneratorStringUtil;
import dev.coffeezombie.wasp.util.model.GeneratorConfig;
import dev.coffeezombie.wasp.util.model.GeneratorEntity;

import java.util.ArrayList;
import java.util.StringJoiner;

import static dev.coffeezombie.wasp.util.GeneratorStringUtil.getCamelCaseEntityName;
import static dev.coffeezombie.wasp.util.GeneratorStringUtil.getLowerCaseEntityName;

public class ControllerGenerator {

    public final static String INDENT_ONE = "\t";

    public static String generateController(GeneratorConfig config, GeneratorEntity entity){
        var controllerBuilder = new StringJoiner("\n");

        String serviceName = getCamelCaseEntityName(entity.getName()) + "Service";
        String dtoName = entity.getName() + "Dto";

        // Package Name
        controllerBuilder.add("package " + config.getPackageName() + ".controller;\n");

        // Imports
        controllerBuilder.add(generateAllImports(config, entity));

        // Annotations
        controllerBuilder.add("@RestController");
        controllerBuilder.add("@RequiredArgsConstructor");
        controllerBuilder.add("@RequestMapping(\"" + getLowerCaseEntityName(entity.getName()) + "\")");

        // Class
        controllerBuilder.add("public class " + entity.getName() + "Controller {");

        // Service Import
        controllerBuilder.add("");
        controllerBuilder.add("\tprivate final " + entity.getName() + "Service " + serviceName + ";");

        // Methods
        var methods = new StringJoiner("\n\t");
        methods.add("");

        methods.add("@GetMapping(\"\")");
        methods.add("public ResponseEntity<List<" + dtoName + ">> findAll(){");
        methods.add(INDENT_ONE + "return ok(" + serviceName + ".findAll());");
        methods.add("}");
        methods.add("");

        methods.add("@PostMapping(\"\")");
        methods.add("public ResponseEntity<" + dtoName + "> createOrUpdate(@RequestBody " + dtoName + " dto){");
        methods.add(INDENT_ONE + "return ok(" + serviceName + ".createOrUpdate(dto));");
        methods.add("}");
        methods.add("");
        methods.add("@PutMapping(\"\")");
        methods.add("public ResponseEntity<" + dtoName + "> update(@RequestBody " + dtoName + " dto){");
        methods.add(INDENT_ONE + "return ok(" + serviceName + ".update(dto));");
        methods.add("}");
        methods.add("");
        methods.add("@GetMapping(\"{id}\")");
        methods.add("public ResponseEntity<" + dtoName + "> findById(@PathVariable Long id){");
        methods.add(INDENT_ONE + "return ok(" + serviceName + ".findById(id));");
        methods.add("}");
        methods.add("");
        methods.add("@DeleteMapping(\"{id}\")");
        methods.add("public ResponseEntity<String> deleteById(@PathVariable Long id){");
        methods.add(INDENT_ONE + serviceName + ".deleteById(id);");
        methods.add(INDENT_ONE + "return ok(\"Deleted.\");");
        methods.add("}");
        methods.add("");

        controllerBuilder.add(methods.toString());

        controllerBuilder.add("}");

        return GeneratorStringUtil.cleanOutput(controllerBuilder.toString());
    }

    public static String generateAllImports(GeneratorConfig config, GeneratorEntity entity){
        String packageImports = generatePackageImports(config, entity);

        // Language Imports
        var joiner = new StringJoiner("\n");
        joiner.add(packageImports);
        joiner.add("");
        joiner.add("import java.util.List;");
        joiner.add("");
        joiner.add("import static org.springframework.http.ResponseEntity.ok;");
        joiner.add("");

        return joiner.toString();
    }

    public static String generatePackageImports(GeneratorConfig config, GeneratorEntity entity){
        var packages = new ArrayList<String>();

        String basePackage = config.getPackageName();

        if(config.getClasses().getDto())
            packages.add(basePackage + ".dto." + entity.getName() + "Dto");

        packages.add(basePackage + ".service." + entity.getName() + "Service");

        packages.add("lombok.RequiredArgsConstructor");
        packages.add("org.springframework.http.ResponseEntity");
        packages.add("org.springframework.web.bind.annotation.*");

        return GeneratorStringUtil.generateImports(packages);
    }

}
