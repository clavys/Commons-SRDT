/*
 * Copyright (c) 2017-2018 Atlanmod, Inria, LS2N, and IMT Nantes.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package fr.inria.atlanmod.commons;

import java.util.function.LongSupplier;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * An object that automatically loads a {@code long} by using a {@link LongSupplier} during the first call to
 * {@link #getAsLong()}.
 */
@ParametersAreNonnullByDefault
public final class LazyLong {

    /**
     * The function used to load the value.
     */
    @Nonnull
    private final LongSupplier loadFunction;

    /**
     * The embed value.
     */
    private long value;

    /**
     * Whether the value has been loaded.
     */
    private boolean isLoaded;

    /**
     * Constructs a new {@code LazyLong}.
     *
     * @param loadFunction the function used to load the value
     */
    private LazyLong(LongSupplier loadFunction) {
        this.loadFunction = loadFunction;
    }

    /**
     * Creates a new lazy long with the embed value.
     *
     * @param value the embed value
     *
     * @return a new lazy object
     */
    @Nonnull
    public static LazyLong of(long value) {
        return new LazyLong(() -> value);
    }

    /**
     * Creates a new lazy long with the {@link LongSupplier} to load the embed value.
     *
     * @param loadFunction the function used to load the value
     *
     * @return a new lazy object
     */
    @Nonnull
    public static LazyLong with(LongSupplier loadFunction) {
        return new LazyLong(loadFunction);
    }

    /**
     * Gets the wrapped value. On the first call, the object is created.
     *
     * @return the value
     */
    public long getAsLong() {
        if (!isLoaded) {
            update(loadFunction.getAsLong());
        }
        return value;
    }

    /**
     * Updates the wrapped value.
     *
     * @param newValue the new value
     */
    public void update(long newValue) {
        value = newValue;
        isLoaded = true;
    }

    /**
     * Returns {@code true} if the value has been loaded.
     *
     * @return {@code true} if the value has been loaded
     */
    public boolean isLoaded() {
        return isLoaded;
    }
}
