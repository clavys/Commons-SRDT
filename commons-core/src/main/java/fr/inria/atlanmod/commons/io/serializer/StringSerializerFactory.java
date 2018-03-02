/*
 * Copyright (c) 2017-2018 Atlanmod, Inria, LS2N, and IMT Nantes.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package fr.inria.atlanmod.commons.io.serializer;

import fr.inria.atlanmod.commons.Throwables;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * A factory that creates {@link StringSerializer} instances.
 */
@ParametersAreNonnullByDefault
public final class StringSerializerFactory {

    private StringSerializerFactory() {
        throw Throwables.notInstantiableClass(getClass());
    }

    /**
     * Creates a new {@link StringSerializer} using a Base16 encoding scheme.
     *
     * @param serializer the serializer to use before applying a {@link String} encoding or decoding
     * @param <T>        the type of (de)serialized objects
     *
     * @return a new string serializer
     */
    @Nonnull
    public static <T> StringSerializer<T> base16(Serializer<T> serializer) {
        return new Base16Serializer<>(serializer);
    }

    /**
     * Creates a new {@link StringSerializer} using a Base64 encoding scheme.
     *
     * @param serializer the serializer to use before applying a {@link String} encoding or decoding
     * @param <T>        the type of (de)serialized objects
     *
     * @return a new string serializer
     */
    @Nonnull
    public static <T> StringSerializer<T> base64(Serializer<T> serializer) {
        return new Base64Serializer<>(serializer);
    }
}
