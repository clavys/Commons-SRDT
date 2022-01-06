/*
 * Copyright (c) 2022 Naomod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */
package org.atlanmod.testing;

import org.atlanmod.testing.generator.RandomCharGenerator;
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
