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

public class RandomCharGenerator implements Generator<Character> {
    private static final int SIZE = 5;
    private final char[] values = new char[SIZE];
    private int index = 0;

    public RandomCharGenerator() {
        Random random = new Random();
        int offset = 'a';
        for (int i = 0; i < values.length; i++) {
            values[i] = (char) (offset + random.nextInt(26));
        }
    }

    @Override
    public Character generate() {
        return values[index++ % values.length];
    }

    @Override
    public Class[] types() {
        return new Class[]{Character.class, char.class};
    }
}
