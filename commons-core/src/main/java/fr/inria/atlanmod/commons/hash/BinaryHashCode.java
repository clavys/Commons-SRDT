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

package fr.inria.atlanmod.commons.hash;

import fr.inria.atlanmod.commons.primitive.Bytes;
import fr.inria.atlanmod.commons.primitive.Longs;

import java.io.Serializable;
import java.security.MessageDigest;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.Immutable;

import static fr.inria.atlanmod.commons.Preconditions.checkNotNull;

/**
 * An immutable hash code of arbitrary bit length.
 */
@Immutable
@ParametersAreNonnullByDefault
final class BinaryHashCode implements HashCode {

    @SuppressWarnings("JavaDoc")
    private static final long serialVersionUID = -576482022539994714L;

    /**
     * The bytes representation of this hash code.
     */
    private final byte[] bytes;

    /**
     * Constructs a new {@code HashCode} with the given {@code hashCode}.
     *
     * @param hashCode the bytes representation of this hash code
     */
    public BinaryHashCode(byte[] hashCode) {
        this.bytes = checkNotNull(hashCode);
    }

    @Nonnegative
    @Override
    public int bits() {
        return bytes.length * 8;
    }

    @Nonnull
    @Override
    public byte[] toBytes() {
        return bytes.clone();
    }

    @Override
    public long toLong() {
        return Bytes.toLong(bytes);
    }

    @Nonnull
    @Override
    public String toHexString() {
        return Bytes.toStringBinary(bytes);
    }

    @Override
    public int hashCode() {
        if (bits() >= 32) {
            return (bytes[0] & 0xff)
                    | ((bytes[1] & 0xff) << 8)
                    | ((bytes[2] & 0xff) << 16)
                    | ((bytes[3] & 0xff) << 24);
        }

        byte[] bytes = toBytes();

        int value = bytes[0] & 0xFF;
        for (int i = 1; i < bytes.length; i++) {
            value |= (bytes[i] & 0xFF) << i * 8;
        }
        return value;
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) {
            return true;
        }
        if (!BinaryHashCode.class.isInstance(o)) {
            return false;
        }

        BinaryHashCode that = BinaryHashCode.class.cast(o);
        return MessageDigest.isEqual(bytes, that.bytes);
    }

    @Override
    public String toString() {
        return String.format("HashCode {%s}", toHexString());
    }
}
