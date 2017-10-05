/*
 * Copyright (c) 2017 Atlanmod, Inria, LS2N, and IMT Nantes.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package fr.inria.atlanmod.commons.hash;

import fr.inria.atlanmod.commons.primitive.Booleans;
import fr.inria.atlanmod.commons.primitive.Chars;
import fr.inria.atlanmod.commons.primitive.Doubles;
import fr.inria.atlanmod.commons.primitive.Floats;
import fr.inria.atlanmod.commons.primitive.Ints;
import fr.inria.atlanmod.commons.primitive.Longs;
import fr.inria.atlanmod.commons.primitive.Shorts;
import fr.inria.atlanmod.commons.primitive.Strings;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * A hash function producing a {@link HashCode} from byte sequences of arbitrary length.
 */
@FunctionalInterface
@ParametersAreNonnullByDefault
public interface Hasher {

    /**
     * Calculates the {@link HashCode} of the given {@code data}.
     *
     * @param data the {@code byte} to hash
     *
     * @return a new hash code
     */
    @Nonnull
    default HashCode hash(byte data) {
        return hash(new byte[]{data});
    }

    /**
     * Calculates the {@link HashCode} of the given {@code data}.
     *
     * @param data the {@code boolean} to hash
     *
     * @return a new hash code
     */
    @Nonnull
    default HashCode hash(boolean data) {
        return hash(Booleans.toBytes(data));
    }

    /**
     * Calculates the {@link HashCode} of the given {@code data}.
     *
     * @param data the {@code char} to hash
     *
     * @return a new hash code
     */
    @Nonnull
    default HashCode hash(char data) {
        return hash(Chars.toBytes(data));
    }

    /**
     * Calculates the {@link HashCode} of the given {@code data}.
     *
     * @param data the {@code short} to hash
     *
     * @return a new hash code
     */
    @Nonnull
    default HashCode hash(short data) {
        return hash(Shorts.toBytes(data));
    }

    /**
     * Calculates the {@link HashCode} of the given {@code data}.
     *
     * @param data the {@code int} to hash
     *
     * @return a new hash code
     */
    @Nonnull
    default HashCode hash(int data) {
        return hash(Ints.toBytes(data));
    }

    /**
     * Calculates the {@link HashCode} of the given {@code data}.
     *
     * @param data the {@code long} to hash
     *
     * @return a new hash code
     */
    @Nonnull
    default HashCode hash(long data) {
        return hash(Longs.toBytes(data));
    }

    /**
     * Calculates the {@link HashCode} of the given {@code data}.
     *
     * @param data the {@code float} to hash
     *
     * @return a new hash code
     */
    @Nonnull
    default HashCode hash(float data) {
        return hash(Floats.toBytes(data));
    }

    /**
     * Calculates the {@link HashCode} of the given {@code data}.
     *
     * @param data the {@code double} to hash
     *
     * @return a new hash code
     */
    @Nonnull
    default HashCode hash(double data) {
        return hash(Doubles.toBytes(data));
    }

    /**
     * Calculates the {@link HashCode} of the given {@code data}.
     *
     * @param data the string to hash
     *
     * @return a new hash code
     */
    @Nonnull
    default HashCode hash(String data) {
        return hash(Strings.toBytes(data));
    }

    /**
     * Calculates the {@link HashCode} of the given {@code byte} array.
     *
     * @param data the {@code byte} array to hash
     *
     * @return a new hash code
     */
    @Nonnull
    HashCode hash(byte[] data);
}
