/*
 * Copyright (c) 2022 Naomod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */
package org.atlanmod.testing.generator;

import org.atlanmod.testing.Generator;

public class RoundRobinBooleanGenerator implements Generator<Boolean> {
    private static final boolean[] VALUES = {true, false};
    private int index = 0;

    @Override
    public Boolean generate() {
        return VALUES[index++ % VALUES.length];
    }

    @Override
    public Class<Boolean>[] types() {
        return new Class[]{Boolean.class, boolean.class};
    }
}
