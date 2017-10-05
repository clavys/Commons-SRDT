/*
 * Copyright (c) 2017 Atlanmod, Inria, LS2N, and IMT Nantes.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package fr.inria.atlanmod.commons.primitive;

import fr.inria.atlanmod.commons.AbstractTest;

import org.junit.jupiter.api.Test;

import javax.annotation.ParametersAreNonnullByDefault;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * A test-case that checks the behavior of {@link Booleans}.
 */
@ParametersAreNonnullByDefault
public class BooleansTest extends AbstractTest {

    @Test
    public void testToBytes() {
        final byte zero = 0;

        byte[] actual0 = Booleans.toBytes(Boolean.TRUE);
        assertThat(actual0[0]).isNotEqualTo(zero);

        byte[] actual1 = Booleans.toBytes(Boolean.FALSE);
        assertThat(actual1[0]).isEqualTo(zero);
    }
}