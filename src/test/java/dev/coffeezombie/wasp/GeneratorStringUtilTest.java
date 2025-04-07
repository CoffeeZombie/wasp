package dev.coffeezombie.wasp;

import dev.coffeezombie.wasp.util.GeneratorStringUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GeneratorStringUtilTest {

    @Test
    public void testGetLowerCaseEntityName(){
        String expected = "person-thing-stuff-item";
        String input = "PersonThingStuffItem";
        String result = GeneratorStringUtil.getLowerCaseEntityName(input);
        assertEquals(expected, result);
    }

    @Test
    public void testGetCamelCaseEntityName(){
        String expected = "personThingStuffItem";
        String input = "PersonThingStuffItem";
        String result = GeneratorStringUtil.getCamelCaseEntityName(input);
        assertEquals(expected, result);
    }

}
