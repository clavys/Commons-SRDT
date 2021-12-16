package org.atlanmod.testing;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class RandomStringOfIntGeneratorTest {

    @Test
    void generate() {
        RandomStringOfIntGenerator genString = new RandomStringOfIntGenerator();
        assertEquals (genString.generate().getClass(),String.class);
        //assertThat(genString.generate()).hasNoCause()
        //assertDoesNotThrow(Integer.valueOf(genString.generate()));
    }

    @Test
    void types() {
        RandomStringOfIntGenerator genString = new RandomStringOfIntGenerator();
        assertArrayEquals(genString.types(), new Class[]{String.class});
    }
}