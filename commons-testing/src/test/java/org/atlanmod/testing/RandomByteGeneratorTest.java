package org.atlanmod.testing;

import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.*;

class RandomByteGeneratorTest {

    @Test
    void generate()  {
        RandomByteGenerator byteGenerator = new RandomByteGenerator();
        Byte aByte = byteGenerator.generate();
        assertNotEquals(null,aByte);
        assertEquals (aByte.getClass(),Byte.class);
    }

    @Test
    void types() {
        RandomByteGenerator genByte = new RandomByteGenerator();
        assertArrayEquals(genByte.types(), new Class[]{Byte.class,byte.class});
    }
}