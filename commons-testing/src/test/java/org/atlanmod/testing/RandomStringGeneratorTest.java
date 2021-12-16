package org.atlanmod.testing;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class RandomStringGeneratorTest {

    @Test
    void generate() {
        RandomStringGenerator genString = new RandomStringGenerator();
        assertEquals (genString.generate().getClass(),String.class);
    }

    @Test
    void types() {
        RandomStringGenerator genString = new RandomStringGenerator();
        assertArrayEquals(genString.types(), new Class[]{String.class});
    }
}
