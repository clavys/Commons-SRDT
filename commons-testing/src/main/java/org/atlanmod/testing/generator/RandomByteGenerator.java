/*
 * Copyright (c) 2022 Naomod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */
package org.atlanmod.testing.generator;

import org.atlanmod.commons.collect.MoreArrays;

import java.util.Random;

public class RandomByteGenerator extends AbstractGenerator<Byte> {

    public RandomByteGenerator() {
        byte[] bytes = new byte[SIZE];
        Random rd = new Random();
        rd.nextBytes(bytes);
        values = MoreArrays.toObject(bytes);
    }


    @Override
    public Class<Byte>[] types() {
        return new Class[]{Byte.class, byte.class};
    }
}
