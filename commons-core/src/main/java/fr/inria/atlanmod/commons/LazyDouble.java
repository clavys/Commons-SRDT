/*
 * Copyright (c) 2017-2018 Atlanmod, Inria, LS2N, and IMT Nantes.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package fr.inria.atlanmod.commons;

import java.util.function.DoubleSupplier;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * An object that automatically loads a {@code double} by using a {@link DoubleSupplier} during the first call to
 * {@link #getAsDouble()}.
 */
@ParametersAreNonnullByDefault
public final class LazyDouble {

    /**
     * The function used to load the value.
     */
    @Nonnull
    private final DoubleSupplier loadFunction;

    /**
     * The embed value.
     */
    private double value;

    /**
     * Whether the value has been loaded.
     */
    private boolean isLoaded;

    /**
     * Constructs a new {@code LazyDouble}.
     *
     * @param loadFunction the function used to load the value
     */
    private LazyDouble(DoubleSupplier loadFunction) {
        this.loadFunction = loadFunction;
    }

    /**
     * Creates a new lazy double with the embed value.
     *
     * @param value the embed value
     *
     * @return a new lazy object
     */
    @Nonnull
    public static LazyDouble of(double value) {
        return new LazyDouble(() -> value);
    }

    /**
     * Creates a new lazy double with the {@link DoubleSupplier} to load the embed value.
     *
     * @param loadFunction the function used to load the value
     *
     * @return a new lazy object
     */
    @Nonnull
    public static LazyDouble with(DoubleSupplier loadFunction) {
        return new LazyDouble(loadFunction);
    }

    /**
     * Gets the wrapped value. On the first call, the object is created.
     *
     * @return the value
     */
    public double getAsDouble() {
        if (!isLoaded) {
            update(loadFunction.getAsDouble());
        }
        return value;
    }

    /**
     * Updates the wrapped value.
     *
     * @param newValue the new value
     */
    public void update(double newValue) {
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
