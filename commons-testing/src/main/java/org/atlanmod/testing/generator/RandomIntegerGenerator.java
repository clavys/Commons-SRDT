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

public class RandomIntegerGenerator implements Generator<Integer> {

    @Override
    /**
     *Generate an integer.
     */
    public Integer generate() {
        int min= 0;
        int max= 20;
        Random r = new Random();
        int value = r.nextInt((max - min) + 1);
        boolean bool = r.nextBoolean();
       // if(!bool) value=-1*value;
        return value;
    }

    @Override
    /**
     *return an array of class which contains the integer and int class.
     */
    public Class<Integer>[] types() {
        Class[] types={Integer.class,int.class};
        return types;
    }
}
