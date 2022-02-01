/*
 * Copyright (c) 2022 Naomod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */
package org.atlanmod.testing.generator;
import org.atlanmod.testing.Generator;

import java.util.Random;

public class RandomIntegerGenerator extends IntegerGenerator implements Generator<Integer> {

    private static final int SIZE = 5;
    private final int[] values = new int[SIZE];
    private int index = 0;

    public RandomIntegerGenerator() {
        Random r = new Random();
        for (int i = 0; i < SIZE; i++) {
            values[i] = r.nextInt();
        }
    }

    @Override
    public Integer generate() {
        return values[index++ % SIZE];
    }

}
