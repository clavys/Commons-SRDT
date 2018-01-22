/*
 * Copyright (c) 2017-2018 Atlanmod, Inria, LS2N, and IMT Nantes.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package fr.inria.atlanmod.commons;

import fr.inria.atlanmod.commons.annotation.Static;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import static fr.inria.atlanmod.commons.Preconditions.checkNotNull;

/**
 * Static utility methods related to {@link Throwable} and {@link Exception} instances.
 */
@Static
@ParametersAreNonnullByDefault
public final class Throwables {

    private Throwables() {
        throw notInstantiableClass(getClass());
    }

    /**
     * Wraps the given {@code throwable} in the given {@code type}.
     * <p>
     * If {@code throwable} is already an instance of {@code type}, then the original {@code throwable} is returned,
     * otherwise, the {@code throwable} is declared as the cause of a new instance of {@code type}.
     *
     * @param throwable the throwable to wrap
     * @param type      the expected type of the {@code throwable}
     *
     * @return a throwable of the given {@code type}
     */
    @Nonnull
    public static <E extends Throwable> E wrap(Throwable throwable, Class<E> type) {
        checkNotNull(throwable, "throwable");
        checkNotNull(type, "type");

        try {
            Throwable e = type.isInstance(throwable)
                    ? throwable
                    : type.newInstance().initCause(throwable);

            return type.cast(e);
        }
        catch (IllegalAccessException | InstantiationException re) {
            throw new IllegalStateException(re); // Should never happen
        }
    }

    /**
     * Returns a new {@link RuntimeException} thrown when calling a method that is not yet implemented.
     *
     * @param methodName the name of the method not implemented
     *
     * @return a new runtime exception
     */
    @Nonnull
    public static RuntimeException notImplementedYet(String methodName) {
        return new UnsupportedOperationException(String.format("Not implemented yet: %s", methodName));
    }

    /**
     * Returns a new {@link RuntimeException} thrown when calling a constructor of a non-instantiable class.
     *
     * @param type the non-instantiable class
     *
     * @return a new runtime exception
     *
     * @see Static
     */
    @Nonnull
    public static RuntimeException notInstantiableClass(Class<?> type) {
        return new IllegalStateException(String.format("%s is not instantiable; this constructor should not be called", type.getSimpleName()));
    }
}
