package org.atlanmod.testing;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RandomIntegerGeneratorTest {

    @Test
    void generate() {
        RandomIntegerGenerator integerGenerator = new RandomIntegerGenerator();
        Integer entier = integerGenerator.generate();
        assertNotEquals(null,entier);
        assertEquals (entier.getClass(),Integer.class);
    }

    @Test
    void types() {
        RandomIntegerGenerator entier = new RandomIntegerGenerator();
        assertArrayEquals(entier.types(), new Class[]{Integer.class, int.class});
    }
}
