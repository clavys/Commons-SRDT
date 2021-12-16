package org.atlanmod.testing;

import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class RandomByteGeneratorTest {

    @Test
    void generate()  {
        RandomByteGenerator genByte = new RandomByteGenerator();
        assertEquals (genByte.generate().getClass(),Byte.class);
    }

    @Test
    void types() {
        RandomByteGenerator genByte = new RandomByteGenerator();
        assertArrayEquals(genByte.types(), new Class[]{Byte.class,byte.class});
    }
}