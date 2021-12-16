package org.atlanmod.testing;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RandomBooleanGeneratorTest {

    @Test
    void generate() {
        RandomBooleanGenerator genBool = new RandomBooleanGenerator();
        assertEquals (genBool.generate().getClass(),Boolean.class);
    }

    @Test
    void types() {
        RandomBooleanGenerator genBool = new RandomBooleanGenerator();
        assertArrayEquals(genBool.types(), new Class[]{Boolean.class});
    }
}