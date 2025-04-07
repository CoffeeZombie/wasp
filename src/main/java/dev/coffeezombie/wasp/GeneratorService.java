package dev.coffeezombie.wasp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.coffeezombie.wasp.generator.DtoGenerator;
import dev.coffeezombie.wasp.generator.EntityGenerator;
import dev.coffeezombie.wasp.generator.RepositoryGenerator;
import dev.coffeezombie.wasp.generator.ServiceGenerator;
import dev.coffeezombie.wasp.util.model.GeneratorConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GeneratorService {

    public String generate(String json) throws JsonProcessingException {

        var obj = new ObjectMapper();
        GeneratorConfig config = obj.readValue(json, GeneratorConfig.class);

        var sb = new StringBuilder();

        String basePackageName = config.getPackageName();

        for(var entity : config.getEntities()){
            String entityString = EntityGenerator.generateEntity(config, entity);
            sb.append(entityString);

            System.out.println(sb.toString());

            if(config.getClasses().getDto()){
                String dto = DtoGenerator.generateDto(config.getDefaultPreferences(), entity, basePackageName);
                System.out.println(dto);
            }

            if(config.getClasses().getRepository()){
                String repo = RepositoryGenerator.generateRepository(basePackageName, entity);
                System.out.println(repo);
            }

            // Can't create service without repo
            if(config.getClasses().getService() && config.getClasses().getRepository()){
                String service = ServiceGenerator.generateService(config, entity);
                System.out.println(service);
            }

        }

        return sb.toString();

    }

    private String getPackage(String packageName, String type){
        return "package " + packageName + "." + type + ";";
    }

}
