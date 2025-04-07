package dev.coffeezombie.wasp.util;

import java.util.List;
import java.util.StringJoiner;

public class StringUtil {

    public static String getLowerCaseEntityName(String name){
        // TODO: handle multiple words e.g. UserPreference to user-preference
        return name.toLowerCase();
    }

    public static String getCamelCaseEntityName(String name){
        // TODO: handle multiple words e.g. UserPreference to userPreference
        return name.toLowerCase();
    }

    public static String cleanOutput(String input){
        // Remove empty tabbed lines
        input = input.replace("\n\t\n", "\n\n");

        // Replace tabs with spaces to match IDE output
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

}
