/*
 * Copyright (c) 2017-2018 Atlanmod, Inria, LS2N, and IMT Nantes.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package fr.inria.atlanmod.commons.hash;

import fr.inria.atlanmod.commons.Throwables;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import static fr.inria.atlanmod.commons.Preconditions.checkNotNull;

/**
 * A {@link Hasher} that delegate its calls to a {@link MessageDigest} from the Java API.
 */
@ParametersAreNonnullByDefault
final class NativeHasher implements Hasher {

    /**
     * The delegated hash function.
     */
    @Nonnull
    private final MessageDigest digest;

    /**
     * Constructs a new {@code NativeHasher}.
     *
     * @param algorithm the name of the algorithm of this hasher
     */
    public NativeHasher(String algorithm) {
        checkNotNull(algorithm, "algorithm");

        this.digest = create(algorithm);
    }

    /**
     * Creates a new instance of {@link MessageDigest} for the specified {@code algorithm}.
     *
     * @param algorithm the name of the algorithm
     *
     * @return a new instance
     */
    @Nonnull
    private static MessageDigest create(String algorithm) {
        try {
            return MessageDigest.getInstance(algorithm);
        }
        catch (NoSuchAlgorithmException e) {
            throw Throwables.wrap(e, IllegalStateException.class);
        }
    }

    @Nonnull
    @Override
    public HashCode hash(byte[] data) {
        HashCode h = new BinaryHashCode(digest.digest(data));
        digest.reset();
        return h;
    }
}
