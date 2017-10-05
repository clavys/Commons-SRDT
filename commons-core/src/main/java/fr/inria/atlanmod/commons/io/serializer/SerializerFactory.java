/*
 * Copyright (c) 2017 Atlanmod, Inria, LS2N, and IMT Nantes.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package fr.inria.atlanmod.commons.io.serializer;

import fr.inria.atlanmod.commons.annotation.Singleton;
import fr.inria.atlanmod.commons.annotation.Static;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * A factory that creates {@link Serializer} instances.
 */
@Singleton
@ParametersAreNonnullByDefault
public class SerializerFactory {

    /**
     * The default {@link Serializer}.
     */
    @Nonnull
    private final Serializer<?> anySerializer = new ObjectSerializer<>();

    /**
     * Constructs a new {@code SerializerFactory}.
     */
    protected SerializerFactory() {
    }

    /**
     * Returns the instance of this class.
     *
     * @return the instance of this class
     */
    @Nonnull
    public static SerializerFactory getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * Gets the {@link Serializer} for any {@link Object}.
     *
     * @param <T> the type of (de)serialized objects
     *
     * @return a serializer
     */
    @Nonnull
    @SuppressWarnings("unchecked")
    public <T> Serializer<T> forAny() {
        return (Serializer<T>) anySerializer;
    }

    /**
     * The initialization-on-demand holder of the singleton of this class.
     */
    @Static
    private static final class Holder {

        /**
         * The instance of the outer class.
         */
        private static final SerializerFactory INSTANCE = new SerializerFactory();
    }
}
