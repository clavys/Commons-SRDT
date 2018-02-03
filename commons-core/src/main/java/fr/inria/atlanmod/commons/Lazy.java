/*
 * Copyright (c) 2017 Atlanmod, Inria, LS2N, and IMT Nantes.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package fr.inria.atlanmod.commons;

import java.util.function.Supplier;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * An object that automatically loads an object of type {@code T} by using a {@link Supplier} during the first call to
 * {@link #get()}.
 *
 * @param <T> the type of the embed value
 */
@ParametersAreNonnullByDefault
public final class Lazy<T> {

    /**
     * The function used to load the value.
     */
    @Nonnull
    private final Supplier<T> loadFunction;

    /**
     * The embed value.
     */
    @Nullable
    private T value;

    /**
     * Whether the value has been loaded.
     */
    private boolean isLoaded;

    /**
     * Constructs a new {@code Lazy}.
     *
     * @param loadFunction the function used to load the value
     */
    private Lazy(Supplier<T> loadFunction) {
        this.loadFunction = loadFunction;
    }

    /**
     * Creates a new lazy object with the embed value.
     *
     * @param value the embed value
     * @param <T>   the type of the embed value
     *
     * @return a new lazy object
     */
    @Nonnull
    public static <T> Lazy<T> of(T value) {
        return new Lazy<>(() -> value);
    }

    /**
     * Creates a new lazy object with the {@link Supplier} to load the embed value.
     *
     * @param loadFunction the function used to load the value
     * @param <T>          the type of the embed value
     *
     * @return a new lazy object
     */
    @Nonnull
    public static <T> Lazy<T> with(Supplier<T> loadFunction) {
        return new Lazy<>(loadFunction);
    }

    /**
     * Gets the wrapped value. On the first call, the object is created.
     *
     * @return the value
     */
    public T get() {
        if (!isLoaded) {
            update(loadFunction.get());
        }
        return value;
    }

    /**
     * Updates the wrapped value.
     *
     * @param newValue the new value
     */
    public void update(@Nullable T newValue) {
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
