package dev.coffeezombie.wasp.generator;

import dev.coffeezombie.wasp.util.GeneratorStringUtil;
import dev.coffeezombie.wasp.util.model.GeneratorEntity;

import java.util.ArrayList;
import java.util.StringJoiner;

public class RepositoryGenerator {

    public static String generateRepository(String basePackage, GeneratorEntity entity){

        var repoBuilder = new StringJoiner("\n");

        repoBuilder.add("package " + basePackage + ".repository;");
        repoBuilder.add("");

        var imports = new ArrayList<String>();
        imports.add(basePackage + ".entity." + entity.getName());
        imports.add("org.springframework.data.jpa.repository.JpaRepository");
        imports.add("org.springframework.stereotype.Repository");

        repoBuilder.add(GeneratorStringUtil.generateImports(imports));
        repoBuilder.add("");

        repoBuilder.add("@Repository");
        repoBuilder.add("public interface " + entity.getName() + "Repository extends JpaRepository<" + entity.getName() + ", Long> {");
        repoBuilder.add("}");

        return repoBuilder.toString();
    }

}
