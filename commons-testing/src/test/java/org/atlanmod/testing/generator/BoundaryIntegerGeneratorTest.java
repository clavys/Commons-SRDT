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

public class BoundaryIntegerGeneratorTest {
    private BoundaryIntegerGenerator generator;

    @BeforeEach
    void init() {
        generator = new BoundaryIntegerGenerator();
    }

    @Test
    void generate() {
        assertThat(generator.generate()).isEqualTo(Integer.MIN_VALUE);
        assertThat(generator.generate()).isEqualTo(Integer.valueOf(0));
        assertThat(generator.generate()).isEqualTo(Integer.MAX_VALUE);
        assertThat(generator.generate()).isEqualTo(Integer.MIN_VALUE);
    }

    @Test
    void types() {
        assertThat(generator.types()).contains(Integer.class);
        assertThat(generator.types()).contains(int.class);
    }
}
