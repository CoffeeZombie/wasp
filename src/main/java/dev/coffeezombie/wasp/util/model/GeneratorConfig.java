package dev.coffeezombie.wasp.util.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeneratorConfig {

    String projectLocation;
    String packageName;
    Boolean overwriteFiles = false;
    GeneratorClass classes;
    GeneratorDefaultPreference defaultPreferences;
    List<GeneratorEntity> entities;
}
