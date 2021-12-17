package org.atlanmod.testing;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RandomCharGeneratorTest {

    @Test
    void generate() {
        RandomCharGenerator charGenerator = new RandomCharGenerator();
        Character caractere = charGenerator.generate();
        assertNotEquals(null,caractere);
        assertEquals (caractere.getClass(),Character.class);
    }

    @Test
    void types() {
        RandomCharGenerator genChar = new RandomCharGenerator();
        assertArrayEquals(genChar.types(), new Class[]{Character.class});
    }
}