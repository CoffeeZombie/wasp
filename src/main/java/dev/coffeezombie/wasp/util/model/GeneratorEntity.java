package dev.coffeezombie.wasp.util.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class GeneratorEntity {

    String name;
    String idName;
    List<GeneratorEntityField> properties;

}
