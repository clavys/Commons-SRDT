/*
 * Copyright (c) 2022 Naomod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */
package org.atlanmod.testing;

import org.atlanmod.testing.generator.RandomStringGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RandomStringGeneratorTest {

    @Test
    void generate() {
        RandomStringGenerator stringGenerator = new RandomStringGenerator();
        String chaine = stringGenerator.generate();
        assertNotEquals(null,chaine);
        assertEquals (chaine.getClass(),String.class);
    }

    @Test
    void types() {
        RandomStringGenerator genString = new RandomStringGenerator();
        assertArrayEquals(genString.types(), new Class[]{String.class});
    }
}
