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


class RandomByteGeneratorTest {
    private RandomByteGenerator generator;

    @BeforeEach
    void setup() {
        generator = new RandomByteGenerator();
    }

    @Test
    void generate() {
        Byte first = generator.generate();
        Byte second = generator.generate();
        assertThat(first).isDifferentFrom(second);
    }

    @Test
    void types() {
        assertThat(generator.types()).containsAll(new Class[]{Byte.class, byte.class});
    }
}
