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

    private static final byte[] DATA = Strings.toBytes("Nantes44000");

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
        assertThat(hashWith(Hashers.md5()).toHexString()).isEqualToIgnoringCase("31D0A78B13BFAB69A5ABDDA3D85333A6");
    }

    @Test
    public void testSHA1() {
        assertThat(hashWith(Hashers.sha1()).toHexString()).isEqualToIgnoringCase("88F57BD0C53086269714B07982146A2B95932A2F");
    }

    @Test
    public void testSHA256() {
        assertThat(hashWith(Hashers.sha256()).toHexString()).isEqualToIgnoringCase("6754DB63437F65F5FB29FB76717521B47DE285ADF4630700D44802D9686206D5");
    }

    @Test
    public void testMurmur3() {
        assertThat(hashWith(Hashers.murmur3()).toHexString()).isEqualToIgnoringCase("6584465e14141afd");
    }

    @Test
    public void testXxHash() {
        assertThat(hashWith(Hashers.xx()).toHexString()).isEqualToIgnoringCase("e82ce2b25b745d5d");
    }

    @Test
    public void testCityHash() {
        assertThat(hashWith(Hashers.city()).toHexString()).isEqualToIgnoringCase("04555fb0dfee9f5e");
    }

    @Test
    public void testFarmHashNA() {
        assertThat(hashWith(Hashers.farmNa()).toHexString()).isEqualToIgnoringCase("04555fb0dfee9f5e");
    }

    @Test
    public void testFarmHashUO() {
        assertThat(hashWith(Hashers.farmUo()).toHexString()).isEqualToIgnoringCase("04555fb0dfee9f5e");
    }
}