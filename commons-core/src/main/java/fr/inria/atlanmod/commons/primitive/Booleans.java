/*
 * Copyright (c) 2017 Atlanmod, Inria, LS2N, and IMT Nantes.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package fr.inria.atlanmod.commons.primitive;

import fr.inria.atlanmod.commons.Throwables;
import fr.inria.atlanmod.commons.annotation.Static;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import static fr.inria.atlanmod.commons.Preconditions.checkNotNull;

/**
 * Static utility methods related to {@code boolean} and {@link Boolean}.
 */
@Static
@ParametersAreNonnullByDefault
public final class Booleans {

    private Booleans() {
        throw Throwables.notInstantiableClass(getClass());
    }

    /**
     * Encodes a {@code boolean} to a {@code byte} array.
     *
     * @param value the value to encode
     *
     * @return a {@code byte} array
     */
    @Nonnull
    public static byte[] toBytes(final boolean value) {
        return new byte[]{value ? (byte) 1 : (byte) 0};
    }

    /**
     * Encodes a {@link Boolean} to a {@code byte} array.
     *
     * @param value the value to encode
     *
     * @return a {@code byte} array
     *
     * @throws NullPointerException if the {@code value} is {@code null}
     */
    @Nonnull
    public static byte[] toBytes(final Boolean value) {
        checkNotNull(value, "value");

        return toBytes(value.booleanValue());
    }
}
