/*
 * Copyright (c) 2017-2018 Atlanmod, Inria, LS2N, and IMT Nantes.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package fr.inria.atlanmod.commons.io.serializer;

import java.util.Base64;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * A {@link StringSerializer} that encodes and decodes a {@code byte} array to a Base64 representation.
 *
 * @param <T> the type of (de)serialized objects
 *
 * @see Base64.Encoder#encodeToString(byte[])
 * @see Base64.Decoder#decode(String)
 */
@ParametersAreNonnullByDefault
final class Base64Serializer<T> extends AbstractStringSerializer<T> {

    /**
     * Constructs a new {@code Base64Serializer}.
     *
     * @param serializer the serializer to use before applying a {@link String} encoding or decoding
     */
    protected Base64Serializer(@Nonnull BinarySerializer<T> serializer) {
        super(serializer);
    }

    @Nonnull
    @Override
    public String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    @Nonnull
    @Override
    public byte[] decode(String data) {
        return Base64.getDecoder().decode(data);
    }
}
