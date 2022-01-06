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

    @Override
    /**
     *Generate a byte.
     */
    public Byte generate() {
        Random rd = new Random();
        byte[] arr = new byte[7];
        rd.nextBytes(arr);
        return arr[2];
    }

    @Override
    /**
     * return an array of class which contains the byte class.
     */
    public Class<Byte>[] types() {
        Class[] types={Byte.class,byte.class};
        return types;
    }
}
