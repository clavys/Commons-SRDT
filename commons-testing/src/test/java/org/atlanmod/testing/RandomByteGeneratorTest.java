/*
 * Copyright (c) 2022 Naomod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */
package org.atlanmod.testing;

import org.atlanmod.testing.generator.RandomByteGenerator;
import org.junit.jupiter.api.Test;

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
