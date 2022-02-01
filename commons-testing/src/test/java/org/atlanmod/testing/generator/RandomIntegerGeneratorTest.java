/*
 * Copyright (c) 2022 Naomod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */
package org.atlanmod.testing.generator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.atlanmod.testing.Assertions.assertThat;

public class RandomIntegerGeneratorTest {
    private RandomIntegerGenerator generator;

    @BeforeEach
    void setup() {
        generator = new RandomIntegerGenerator();
    }

    @Test
    void generate() {
        Integer first = generator.generate();
        Integer second = generator.generate();
        assertThat(first).isDifferentFrom(second);
    }

    @Test
    void types() {
        assertThat(generator.types()).containsAll(new Class[]{Integer.class, int.class});
    }
}
