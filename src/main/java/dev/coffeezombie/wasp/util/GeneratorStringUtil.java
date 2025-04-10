package dev.coffeezombie.wasp.util;

import java.util.List;
import java.util.StringJoiner;

public class GeneratorStringUtil {

    public static String getLowerCaseName(String name) {
        return name.replaceAll("([a-z])([A-Z])", "$1-$2").toLowerCase();
    }

    public static String getCamelCaseName(String name) {
        String[] array = name.split("");
        array[0] = array[0].toLowerCase();
        return String.join("", array);
    }

    public static String cleanOutput(String input){
        // Remove empty tabbed lines
        input = input.replace("\n\t\n", "\n\n");

        // Replace tabs with 4 spaces to match IDE output
        return input.replace("\t", "    ");
    }

    public static String getPackage(String packageName, String type){
        return "package " + packageName + "." + type + ";";
    }

    public static String getImportString(String packageName){
        return "import " + packageName + ";";
    }

    public static String generateImports(List<String> packages){
        var joiner = new StringJoiner("\n");
        for(var packageName: packages){
            joiner.add(getImportString(packageName));
        }
        return joiner.toString();
    }

    public static String capitalize(String input) {
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }
}
