package dev.coffeezombie.wasp;

import dev.coffeezombie.wasp.util.GeneratorStringUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GeneratorStringUtilTest {

    @Test
    public void testGetLowerCaseName(){
        String expected = "person-thing-stuff-item";
        String input = "PersonThingStuffItem";
        String result = GeneratorStringUtil.getLowerCaseName(input);
        assertEquals(expected, result);
    }

    @Test
    public void testGetCamelCaseName(){
        String expected = "personThingStuffItem";
        String input = "PersonThingStuffItem";
        String result = GeneratorStringUtil.getCamelCaseName(input);
        assertEquals(expected, result);
    }

}
