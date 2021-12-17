package org.atlanmod.testing;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RandomStringGeneratorTest {

    @Test
    void generate() {
        RandomStringGenerator stringGenerator = new RandomStringGenerator();
        String chaine = stringGenerator.generate();
        assertNotEquals(null,chaine);
        assertEquals (chaine.getClass(),String.class);
    }

    @Test
    void types() {
        RandomStringGenerator genString = new RandomStringGenerator();
        assertArrayEquals(genString.types(), new Class[]{String.class});
    }
}
