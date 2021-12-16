package org.atlanmod.testing;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class RandomIntegerGeneratorTest {

    @Test
    void generate() {
        RandomIntegerGenerator ib = new RandomIntegerGenerator();
        assertEquals (ib.generate().getClass(),Integer.class);
    }

    @Test
    void types() {
        RandomIntegerGenerator ib = new RandomIntegerGenerator();
        assertArrayEquals(ib.types(), new Class[]{Integer.class, int.class});
    }
}
