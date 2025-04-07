package dev.coffeezombie.wasp;

import dev.coffeezombie.wasp.util.model.GeneratorConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@RequestMapping("generator")
public class GeneratorController {

    private final GeneratorService generatorService;

    @PostMapping()
    public ResponseEntity<String> generate(@RequestBody GeneratorConfig config){
        return ok(generatorService.generate(config));
    }

}
