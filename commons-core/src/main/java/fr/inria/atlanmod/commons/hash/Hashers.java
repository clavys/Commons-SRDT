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

import fr.inria.atlanmod.commons.annotation.Static;
import fr.inria.atlanmod.commons.annotation.VisibleForTesting;

import net.openhft.hashing.LongHashFunction;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * The factory that creates {@link Hasher} instances.
 */
@Static
@ParametersAreNonnullByDefault
public final class Hashers {

    /**
     * The name to identify the {@code MD5} algorithm in {@link MessageDigest}.
     */
    private static final String MD5 = "MD5";

    /**
     * The name to identify the {@code SHA-1} algorithm in {@link MessageDigest}.
     */
    private static final String SHA1 = "SHA-1";

    /**
     * The name to identify the {@code SHA-256} algorithm in {@link MessageDigest}.
     */
    private static final String SHA256 = "SHA-256";

    /**
     * This class should not be instantiated.
     *
     * @throws IllegalStateException every time
     */
    private Hashers() {
        throw new IllegalStateException("This class should not be instantiated");
    }

    /**
     * Returns a {@link Hasher} that uses the {@code MD5} algorithm (128 bits).
     *
     * @return a new {@link Hasher}
     */
    @Nonnull
    public static Hasher md5() {
        return data -> nativeHash(MD5, data);
    }

    /**
     * Returns a {@link Hasher} that uses the {@code SHA-1} algorithm (160 bits).
     *
     * @return a new {@link Hasher}
     */
    @Nonnull
    public static Hasher sha1() {
        return data -> nativeHash(SHA1, data);
    }

    /**
     * Returns a {@link Hasher} that uses the {@code SHA-256} algorithm (256 bits).
     *
     * @return a new {@link Hasher}
     */
    @Nonnull
    public static Hasher sha256() {
        return data -> nativeHash(SHA256, data);
    }

    /**
     * Returns a {@link Hasher} that uses the {@code MurmurHash3 64-bit} algorithm (64 bits).
     * <p>
     * See <a href="https://github.com/aappleby/smhasher/blob/master/src/MurmurHash3.cpp">Github</a> for more
     * information.
     *
     * @return a new {@link Hasher}
     */
    @Nonnull
    public static Hasher murmur3() {
        return new DelegatedHasher(LongHashFunction.murmur_3());
    }

    /**
     * Returns a {@link Hasher} that uses the {@code xxHash} algorithm (64 bits).
     * <p>
     * See <a href="https://github.com/Cyan4973/xxHash">Github</a> for more information.
     *
     * @return a new {@link Hasher}
     */
    @Nonnull
    public static Hasher xx() {
        return new DelegatedHasher(LongHashFunction.xx());
    }

    /**
     * Returns a {@link Hasher} that uses the {@code CityHash} algorithm (64 bits).
     * <p>
     * See <a href="https://github.com/google/cityhash">Github</a> for more information.
     *
     * @return a new {@link Hasher}
     */
    @Nonnull
    public static Hasher city() {
        return new DelegatedHasher(LongHashFunction.city_1_1());
    }

    /**
     * Returns a {@link Hasher} that uses the {@code FarmHash NA} algorithm (64 bits).
     * <p>
     * See <a href="https://github.com/google/farmhash">Github</a> for more information.
     *
     * @return a new {@link Hasher}
     */
    @Nonnull
    public static Hasher farmNa() {
        return new DelegatedHasher(LongHashFunction.farmNa());
    }

    /**
     * Returns a {@link Hasher} that uses the {@code FarmHash UO} algorithm (64 bits).
     * <p>
     * See <a href="https://github.com/google/farmhash">Github</a> for more information.
     *
     * @return a new {@link Hasher}
     */
    @Nonnull
    public static Hasher farmUo() {
        return new DelegatedHasher(LongHashFunction.farmUo());
    }

    /**
     * Creates a new {@link HashCode} from the given {@code bytes} by using the given {@code algorithm}.
     * <p>
     * See <a href="https://github.com/google/farmhash">Github repository</a> for more information.
     *
     * @param algorithm the name of the algorithm requested
     * @param bytes     the value to hash
     *
     * @return a new {@link HashCode}
     */
    @Nonnull
    @VisibleForTesting
    protected static HashCode nativeHash(String algorithm, byte[] bytes) {
        try {
            return new HashCode(MessageDigest.getInstance(algorithm).digest(bytes));
        }
        catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * A {@link Hasher} that delegate its calls to a {@link LongHashFunction}.
     */
    @ParametersAreNonnullByDefault
    private static final class DelegatedHasher implements Hasher {

        /**
         * The delegated hash function.
         */
        private final LongHashFunction delegate;

        /**
         * Constructs a new {@code DelegatedHasher}.
         *
         * @param delegate he delegated hash function
         */
        private DelegatedHasher(LongHashFunction delegate) {
            this.delegate = delegate;
        }

        @Nonnull
        @Override
        public HashCode hash(byte[] data) {
            return new HashCode(delegate.hashBytes(data));
        }

        @Nonnull
        @Override
        public HashCode hash(String data) {
            return new HashCode(delegate.hashChars(data));
        }
    }
}
