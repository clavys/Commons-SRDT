/*
 * Copyright (c) 2017-2018 Atlanmod, Inria, LS2N, and IMT Nantes.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package fr.inria.atlanmod.commons.io.serializer;

import fr.inria.atlanmod.commons.function.Converter;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * An object that is responsible of {@link Object} to {@link String} encoding and decoding.
 *
 * @param <T> the type of (de)serialized objects
 */
@ParametersAreNonnullByDefault
public interface StringSerializer<T> extends Converter<T, String> {

    /**
     * {@inheritDoc}
     *
     * @see #serialize(Object)
     */
    @Nonnull
    @Override
    default String convert(T t) {
        return serialize(t);
    }

    /**
     * {@inheritDoc}
     *
     * @see #deserialize(String)
     */
    @Nonnull
    @Override
    default T revert(String data) {
        return deserialize(data);
    }

    /**
     * Write an object of type {@code T} as a {@code String}.
     *
     * @param t the object to serialize
     *
     * @return the serialized object as a string
     */
    @Nonnull
    String serialize(T t);

    /**
     * Reads and assembles an object of type {@code T} from the given {@code string}.
     *
     * @param data a string
     *
     * @return the deserialized object
     */
    @Nonnull
    T deserialize(String data);
}
