package dev.coffeezombie.wasp.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GeneratorStringUtilTest {

    @Test
    void capitalize() {
        String expected = "Something";
        String result = GeneratorStringUtil.capitalize("something");
        assertEquals(expected, result);
    }
}