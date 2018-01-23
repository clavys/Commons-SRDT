/*
 * Copyright (c) 2017 Atlanmod, Inria, LS2N, and IMT Nantes.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package fr.inria.atlanmod.commons.hash;

import fr.inria.atlanmod.commons.AbstractTest;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.security.NoSuchAlgorithmException;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

/**
 * A test-case that checks the behavior of {@link Hasher}s.
 */
@ParametersAreNonnullByDefault
public class HasherTest extends AbstractTest {

    /**
     * A 64 bytes string.
     */
    private static final String DATA = "Lorem ipsum dolor sit amet, consectetur adipiscing elit volutpat";

    /**
     * Calculates the {@link HashCode} of {@link #DATA} with the specified {@code hasher}.
     *
     * @param hasher the {@link Hasher} to use
     *
     * @return the hashcode
     */
    @Nonnull
    private static HashCode hashWith(Hasher hasher) {
        return hasher.hash(DATA);
    }

    @Test
    public void testConstructor() throws Exception {
        Constructor<?> constructor = StandardHashers.class.getDeclaredConstructor();
        assertThat(Modifier.isPrivate(constructor.getModifiers())).isTrue();

        constructor.setAccessible(true);

        assertThat(catchThrowable(constructor::newInstance))
                .isInstanceOf(InvocationTargetException.class)
                .hasCauseExactlyInstanceOf(IllegalStateException.class);
    }

    @Test
    public void testInvalidAlgorithm() {
        assertThat(catchThrowable(() -> new NativeHasher("unknown")))
                .isInstanceOf(RuntimeException.class)
                .hasCauseExactlyInstanceOf(NoSuchAlgorithmException.class);
    }

    @Test
    public void testMD5() {
        assertThat(hashWith(StandardHashers.MD5).toHexString()).isEqualToIgnoringCase("3ffd50062f0a110bdcfbc7b8d611aa80");
    }

    @Test
    public void testSHA1() {
        assertThat(hashWith(StandardHashers.SHA1).toHexString()).isEqualToIgnoringCase("469b47430dd9968e127af3034e9b5bd68a700c30");
    }

    @Test
    public void testSHA256() {
        assertThat(hashWith(StandardHashers.SHA256).toHexString()).isEqualToIgnoringCase("f94d9542e5fe295b1f3209fc2b1e23ff43ddd673350d91612e4ea69233da7a8b");
    }

    @Test
    public void testMurmur3() {
        assertThat(hashWith(StandardHashers.MURMUR3).toHexString()).isEqualToIgnoringCase("14afacf5fbbb494f");
    }

    @Test
    public void testXxHash() {
        assertThat(hashWith(StandardHashers.XX).toHexString()).isEqualToIgnoringCase("5ec0f750bc2b69ad");
    }

    @Test
    public void testCityHash() {
        assertThat(hashWith(StandardHashers.CITY).toHexString()).isEqualToIgnoringCase("15dab8ee0877b9a6");
    }

    @Test
    public void testFarmHashNA() {
        assertThat(hashWith(StandardHashers.FARM_NA).toHexString()).isEqualToIgnoringCase("a953e0aba305c7d5");
    }

    @Test
    public void testFarmHashUO() {
        assertThat(hashWith(StandardHashers.FARM_UO).toHexString()).isEqualToIgnoringCase("ea848ff9a62510e3");
    }
}