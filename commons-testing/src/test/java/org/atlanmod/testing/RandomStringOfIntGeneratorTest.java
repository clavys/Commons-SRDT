package org.atlanmod.testing;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class RandomStringOfIntGeneratorTest {

    @Test
    void generate() {
        RandomStringOfIntGenerator stringOfIntGenerator = new RandomStringOfIntGenerator();
        String chaine = stringOfIntGenerator.generate();
        assertNotEquals(null,chaine);
        assertEquals (chaine.getClass(),String.class);
    }

    @Test
    void types() {
        RandomStringOfIntGenerator genString = new RandomStringOfIntGenerator();
        assertArrayEquals(genString.types(), new Class[]{String.class});
    }
}