/*
 * Copyright (c) 2022 Naomod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */
package org.atlanmod.testing.generator;

import org.atlanmod.testing.Generator;

public class BoundaryIntegerGenerator extends IntegerGenerator implements Generator<Integer> {

    private static final int[] VALUES = new int[]{Integer.MIN_VALUE, 0, Integer.MAX_VALUE};
    private int index = 0;

    @Override
    public Integer generate() {
        return this.VALUES[index++ % VALUES.length];
    }

}
