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

import fr.inria.atlanmod.commons.AbstractTest;
import fr.inria.atlanmod.commons.primitive.Strings;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.security.NoSuchAlgorithmException;

import javax.annotation.Nonnull;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

/**
 * A test-case that checks the behavior of {@link Hasher}s.
 */
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
        Constructor<?> constructor = Hashers.class.getDeclaredConstructor();
        assertThat(Modifier.isPrivate(constructor.getModifiers())).isTrue();

        constructor.setAccessible(true);

        assertThat(catchThrowable(constructor::newInstance))
                .isInstanceOf(InvocationTargetException.class)
                .hasCauseExactlyInstanceOf(IllegalStateException.class);
    }

    @Test
    public void testInvalidAlgorithm() {
        assertThat(catchThrowable(() -> Hashers.nativeHash("unknown", new byte[0])))
                .isInstanceOf(RuntimeException.class)
                .hasCauseExactlyInstanceOf(NoSuchAlgorithmException.class);
    }

    @Test
    public void testMD5() {
        assertThat(hashWith(Hashers.md5()).toHexString()).isEqualToIgnoringCase("3ffd50062f0a110bdcfbc7b8d611aa80");
    }

    @Test
    public void testSHA1() {
        assertThat(hashWith(Hashers.sha1()).toHexString()).isEqualToIgnoringCase("469b47430dd9968e127af3034e9b5bd68a700c30");
    }

    @Test
    public void testSHA256() {
        assertThat(hashWith(Hashers.sha256()).toHexString()).isEqualToIgnoringCase("f94d9542e5fe295b1f3209fc2b1e23ff43ddd673350d91612e4ea69233da7a8b");
    }

    @Test
    public void testMurmur3() {
        assertThat(hashWith(Hashers.murmur3()).toHexString()).isEqualToIgnoringCase("14afacf5fbbb494f");
    }

    @Test
    public void testXxHash() {
        assertThat(hashWith(Hashers.xx()).toHexString()).isEqualToIgnoringCase("5ec0f750bc2b69ad");
    }

    @Test
    public void testCityHash() {
        assertThat(hashWith(Hashers.city()).toHexString()).isEqualToIgnoringCase("15dab8ee0877b9a6");
    }

    @Test
    public void testFarmHashNA() {
        assertThat(hashWith(Hashers.farmNa()).toHexString()).isEqualToIgnoringCase("a953e0aba305c7d5");
    }

    @Test
    public void testFarmHashUO() {
        assertThat(hashWith(Hashers.farmUo()).toHexString()).isEqualToIgnoringCase("ea848ff9a62510e3");
    }
}