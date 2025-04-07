package dev.coffeezombie.wasp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.coffeezombie.wasp.generator.*;
import dev.coffeezombie.wasp.util.model.GeneratorConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.StringJoiner;

import static dev.coffeezombie.wasp.util.FileReadWriteUtil.writeClassFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeneratorService {

    public String generate(String json) throws JsonProcessingException {
        var obj = new ObjectMapper();
        GeneratorConfig config = obj.readValue(json, GeneratorConfig.class);
        return generate(config);
    }

    public String generate(GeneratorConfig config) {

        var joiner = new StringJoiner("\n");

        String basePackageName = config.getPackageName();

        for(var entity : config.getEntities()){
            String entityString = EntityGenerator.generateEntity(config, entity);
            String entityFile = writeClassFile(entityString, config, entity.getName(), "entity");
            joiner.add(entityFile);

            if(config.getClasses().getDto()){
                String dto = DtoGenerator.generateDto(config.getDefaultPreferences(), entity, basePackageName);
                String dtoFile = writeClassFile(dto, config, entity.getName(), "dto");
                joiner.add(dtoFile);
            }

            if(config.getClasses().getRepository()){
                String repo = RepositoryGenerator.generateRepository(basePackageName, entity);
                String repoFile = writeClassFile(repo, config, entity.getName(), "repository");
                joiner.add(repoFile);
            }

            // Can't create service without repo
            if(config.getClasses().getService() && config.getClasses().getRepository()){
                String service = ServiceGenerator.generateService(config, entity);
                String serviceFile = writeClassFile(service, config, entity.getName(), "service");
                joiner.add(serviceFile);
            }

            // Can't create controller without service
            if(config.getClasses().getController() && config.getClasses().getService()){
                String controller = ControllerGenerator.generateController(config, entity);
                String controllerFile = writeClassFile(controller, config, entity.getName(), "controller");
                joiner.add(controllerFile);
            }

        }

        return joiner.toString();

    }

}
