/*
 * Copyright (c) 2022 Naomod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */
package org.atlanmod.testing;

import org.atlanmod.testing.generator.RandomBooleanGenerator;
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
