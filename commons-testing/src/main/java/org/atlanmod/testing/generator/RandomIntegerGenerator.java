/*
 * Copyright (c) 2022 Naomod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */
package org.atlanmod.testing.generator;
import org.atlanmod.commons.Preconditions;
import org.atlanmod.testing.Generator;

import java.util.Random;

public class RandomIntegerGenerator extends IntegerGenerator {

    public RandomIntegerGenerator() {
        values = new Integer[SIZE];
        Random r = new Random();
        for (int i = 0; i < SIZE; i++) {
            values[i] = Integer.valueOf(r.nextInt());
        }
    }

}
