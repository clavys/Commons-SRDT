/*
 * Copyright (c) 2022 Naomod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */
package org.atlanmod.testing.generator;

public abstract class IntegerGenerator {

    /**
     *return an array of class which contains the integer and int class.
     */
    public Class<Integer>[] types() {
        return new Class[]{Integer.class, int.class};
    }
}
