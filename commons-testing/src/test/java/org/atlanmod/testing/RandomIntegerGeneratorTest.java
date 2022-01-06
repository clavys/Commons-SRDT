/*
 * Copyright (c) 2022 Naomod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */
package org.atlanmod.testing;

import org.atlanmod.testing.generator.RandomIntegerGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RandomIntegerGeneratorTest {

    @Test
    void generate() {
        RandomIntegerGenerator integerGenerator = new RandomIntegerGenerator();
        Integer entier = integerGenerator.generate();
        assertNotEquals(null,entier);
        assertEquals (entier.getClass(),Integer.class);
    }

    @Test
    void types() {
        RandomIntegerGenerator entier = new RandomIntegerGenerator();
        assertArrayEquals(entier.types(), new Class[]{Integer.class, int.class});
    }
}
