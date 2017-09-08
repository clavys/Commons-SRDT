/*
 * Copyright (c) 2013-2017 Atlanmod INRIA LINA Mines Nantes.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Atlanmod INRIA LINA Mines Nantes - initial API and implementation
 */

package fr.inria.atlanmod.commons;

import java.util.function.Function;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * An object that converts an object of type {@link A} to another of type {@link B}.
 * <p>
 * The reverse operation <b>may</b> be strict <i>inverse</i>, meaning that {@code converter.revert(converter.convert(a)).equals(a)}
 * always {@code true}.
 *
 * @param <A> the type of the input instance
 * @param <B> the type of the output instance
 */
@ParametersAreNonnullByDefault
public interface Converter<A, B> {

    /**
     * Creates a converter that always converts or reverses an object to itself.
     *
     * @param <T> the type of the converted instance
     *
     * @return a new converter
     */
    static <T> Converter<T, T> identity() {
        return new Converter<T, T>() {
            @Nonnull
            @Override
            public T convert(T t) {
                return t;
            }

            @Nonnull
            @Override
            public T revert(T t) {
                return t;
            }
        };
    }

    /**
     * Creates a converter based on separate forward and backward functions.
     *
     * @param convertFunc the function used for {@link #convert(Object)}
     * @param revertFunc  the function used for {@link #revert(Object)}
     * @param <A>         the type of the input instance
     * @param <B>         the type of the output instance
     *
     * @return a new converter
     */
    static <A, B> Converter<A, B> from(Function<? super A, ? extends B> convertFunc, Function<? super B, ? extends A> revertFunc) {
        return new Converter<A, B>() {
            @Nonnull
            @Override
            public B convert(A a) {
                return convertFunc.apply(a);
            }

            @Nonnull
            @Override
            public A revert(B b) {
                return revertFunc.apply(b);
            }
        };
    }

    /**
     * Returns a representation of {@code a} as an instance of type {@link B}.
     *
     * @param a the instance to convert
     *
     * @return the converted instance
     */
    @Nonnull
    B convert(A a);

    /**
     * Returns a representation of {@code b} as an instance of type {@link A}.
     *
     * @param b the instance to convert
     *
     * @return the converted instance
     */
    @Nonnull
    A revert(B b);
}
