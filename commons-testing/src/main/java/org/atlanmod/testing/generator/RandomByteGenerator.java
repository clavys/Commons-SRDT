/*
 * Copyright (c) 2022 Naomod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */
package org.atlanmod.testing.generator;
import org.atlanmod.testing.Generator;

import java.io.UnsupportedEncodingException;
import java.util.Random;

public class RandomByteGenerator implements Generator<Byte> {

    private static final int SIZE = 5;
    private final byte[] values = new byte[SIZE];
    private int index = 0;

    public RandomByteGenerator() {
        Random rd = new Random();
        rd.nextBytes(values);
    }

    @Override
    public Byte generate() {
        return values[index++ % values.length];
    }

    @Override
    public Class<Byte>[] types() {
        return new Class[]{Byte.class,byte.class};
    }
}
