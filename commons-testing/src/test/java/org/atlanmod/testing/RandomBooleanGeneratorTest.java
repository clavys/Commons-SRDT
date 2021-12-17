package org.atlanmod.testing;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RandomBooleanGeneratorTest {

    @Test
    void generate() {
        RandomBooleanGenerator booleanGenerator = new RandomBooleanGenerator();
        Boolean booleen = booleanGenerator.generate();
        assertNotEquals(null,booleen);
        assertEquals (booleen.getClass(),Boolean.class);
    }

    @Test
    void types() {
        RandomBooleanGenerator genBool = new RandomBooleanGenerator();
        assertArrayEquals(genBool.types(), new Class[]{Boolean.class});
    }
}