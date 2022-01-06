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

public class RandomBooleanGenerator implements Generator<Boolean> {

    @Override
    /**
     * Generate a boolean.
     */
    public Boolean generate() {
        Random r = new Random();
        boolean bool = r.nextBoolean();
        return bool;
    }

    @Override
    /**
     * return an array of class which contains the boolean class.
     */
    public Class<Boolean>[] types() {
        Class[] types={Boolean.class};
        return types;
    }
}
