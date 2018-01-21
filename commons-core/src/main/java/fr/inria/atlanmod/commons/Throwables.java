/*
 * Copyright (c) 2017-2018 Atlanmod, Inria, LS2N, and IMT Nantes.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package fr.inria.atlanmod.commons;

import fr.inria.atlanmod.commons.annotation.Static;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import static fr.inria.atlanmod.commons.Preconditions.checkNotNull;

/**
 * Static utility methods related to {@link Throwable} and {@link Exception} instances.
 */
@Static
@ParametersAreNonnullByDefault
public final class Throwables {

    /**
     * This class should not be instantiated.
     *
     * @throws IllegalStateException every time
     */
    private Throwables() {
        throw new IllegalStateException("This class should not be instantiated");
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

        if (type.isInstance(throwable)) {
            return type.cast(throwable);
        }
        else {
            try {
                Constructor<E> constructor = type.getConstructor(Throwable.class);
                return constructor.newInstance(throwable);
            }
            catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException re) {
                throw new IllegalStateException(re);
            }
        }
    }

    /**
     * Returns a new {@link RuntimeException} thrown when a method is not implemented yet.
     *
     * @param methodName the name of the not implemented method
     *
     * @return a new runtime exception
     */
    @Nonnull
    public static RuntimeException notImplementedYet(String methodName) {
        return new UnsupportedOperationException(String.format("Not implemented yet: %s", methodName));
    }
}
