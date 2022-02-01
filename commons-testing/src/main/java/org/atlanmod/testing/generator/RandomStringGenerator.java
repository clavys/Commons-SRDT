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

public class RandomStringGenerator extends AbstractGenerator<String> {
    private final static int MAX_STRING_SIZE = 256;

    public RandomStringGenerator() {
        values = new String[SIZE];
        Random random = new Random();
        for (int i = 0; i < values.length; i++) {
            int stringLength = random.nextInt(MAX_STRING_SIZE);
            StringBuilder builder = new StringBuilder(stringLength);
            for (int j = 0; j < stringLength; j++) {
                builder.append((char) ('a' + random.nextInt(26)));
            }
            values[i] = builder.toString();
        }
    }

    @Override
    public Class<String>[] types() {
        return new Class[]{String.class};
    }
}
