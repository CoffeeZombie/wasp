package dev.coffeezombie.wasp.util;

import dev.coffeezombie.wasp.util.model.GeneratorConfig;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

@Slf4j
public class FileReadWriteUtil {

    public static String getFilePath(GeneratorConfig config, String entityName, String type){
        String filePath = config.getProjectLocation();

        String projectBase = "/src/main/java/";
        if(type.equals("test"))
            projectBase = "/src/test/java/";

        filePath += projectBase;
        filePath += config.getPackageName().replace(".", "/");
        filePath += "/" + type + "/";

        filePath += entityName;
        if(!type.equals("entity"))
            filePath += GeneratorStringUtil.capitalize(type);

        filePath += ".java";

        return filePath;
    }

    public static String writeClassFile(String content, GeneratorConfig config, String entityName, String type){
        String filePath = getFilePath(config, entityName, type);
        return writeStringToFile(content, filePath, config.getOverwriteFiles());
    }

    public static String writeStringToFile(String content, String fileName, boolean overwriteExisting){
        Path filePath = Path.of(fileName);

        if(Files.exists(filePath) && !overwriteExisting){
            log.info("Skipping file because it already exists: {}", fileName);
            return "Skipped: " + fileName;
        }

        try {
            // Ensure parent directories exist
            Path parent = filePath.getParent();
            if (parent != null && !Files.exists(parent)) {
                Files.createDirectories(parent);
            }

            // Write the file
            Files.writeString(filePath, content);
            System.out.println("File written successfully.");
        } catch (IOException e) {
            log.error("Unable to write file {}", fileName, e);
            return "Failed to write: " + fileName;
        }

        log.info("Created new file: {}", fileName);
        return "Wrote: " + fileName;
    }

    public static String readResourceFileToString(String resourcePath) throws IOException {
        try (InputStream inputStream = FileReadWriteUtil.class.getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                throw new IOException("Resource not found: " + resourcePath);
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                return reader.lines().collect(Collectors.joining(System.lineSeparator()));
            }
        }
    }

}
