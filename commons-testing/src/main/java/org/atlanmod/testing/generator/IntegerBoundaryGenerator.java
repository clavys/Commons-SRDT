/*
 * Copyright (c) 2022 Naomod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */
package org.atlanmod.testing.generator;

import org.atlanmod.commons.Guards;
import org.atlanmod.testing.Generator;

import java.util.Arrays;

public class IntegerBoundaryGenerator implements Generator<Integer> {

    private int[] values;
    private int index = 0;

    public IntegerBoundaryGenerator(){
        this(new int[]{Integer.MIN_VALUE, 0, Integer.MAX_VALUE});
    }

    public IntegerBoundaryGenerator(int[] values) {
        Guards.checkGreaterThan(values.length, 0);
        this.values = Arrays.copyOfRange(values, 0, values.length) ;
    }

    @Override
    public Integer generate() {
        return this.values[index % values.length];
    }

    @Override
    public Class<Integer>[] types() {
        return new Class[0];
    }
}
