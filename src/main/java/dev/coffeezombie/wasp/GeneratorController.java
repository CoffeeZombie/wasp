package dev.coffeezombie.wasp;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.coffeezombie.wasp.util.GeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GeneratorController {

    private final GeneratorService generatorService;

    @GetMapping("/test")
    public ResponseEntity<String> test() throws JsonProcessingException {
        String json = "{\n" +
                "\t\"projectLocation\": \"/home/bla/bla\",\n" +
                "\t\"packageName\": \"dev.coffeezombie.wasp\",\n" +
                "\t\"classes\": {\n" +
                "\t\t\"entity\": true,\n" +
                "\t\t\"dto\": true,\n" +
                "\t\t\"repository\": true,\n" +
                "\t\t\"service\": true,\n" +
                "\t\t\"controller\": true,\n" +
                "\t\t\"tests\": true\n" +
                "\t},\n" +
                "\t\"defaultPreferences\": {\n" +
                "\t\t\"jsonIgnoreUnknown\": true,\n" +
                "\t\t\"modelMapper\": true\n" +
                "\t},\n" +
                "\t\"entities\": [\n" +
                "\t\t{\n" +
                "\t\t\t\"name\": \"Person\",\n" +
                "\t\t\t\"idName\": \"id\",\n" +
                "\t\t\t\"properties\": [\n" +
                "\t\t\t\t{ \"name\": \"id\", \"type\": \"Long\" },\n" +
                "\t\t\t\t{ \"name\": \"myLong\", \"type\": \"Long\" },\n" +
                "\t\t\t\t{ \"name\": \"myBd\", \"type\": \"BigDecimal\" },\n" +
                "\t\t\t\t{ \"name\": \"myBi\", \"type\": \"BigInteger\" },\n" +
                "\t\t\t\t{ \"name\": \"myBool\", \"type\": \"boolean\" },\n" +
                "\t\t\t\t{ \"name\": \"myPBool\", \"type\": \"Boolean\" },\n" +
                "\t\t\t\t{ \"name\": \"myPDouble\", \"type\": \"Double\" },\n" +
                "\t\t\t\t{ \"name\": \"myDouble\", \"type\": \"double\" },\n" +
                "\t\t\t\t{ \"name\": \"myFloat\", \"type\": \"float\" },\n" +
                "\t\t\t\t{ \"name\": \"myInt\", \"type\": \"int\" },\n" +
                "\t\t\t\t{ \"name\": \"myInteger\", \"type\": \"Integer\" },\n" +
                "\t\t\t\t{ \"name\": \"myPLong\", \"type\": \"long\" },\n" +
                "\t\t\t\t{ \"name\": \"myString\", \"type\": \"String\" },\n" +
                "\t\t\t\t{ \"name\": \"myDate\", \"type\": \"Date\" },\n" +
                "\t\t\t\t{ \"name\": \"myJson\", \"type\": \"JsonBinaryType\" }\n" +
                "\t\t\t]\n" +
                "\t\t}\n" +
                "\t]\n" +
                "}";

        return ResponseEntity.ok(generatorService.generate(json));
    }

}
