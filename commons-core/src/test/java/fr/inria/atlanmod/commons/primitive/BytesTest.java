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

package fr.inria.atlanmod.commons.primitive;

import fr.inria.atlanmod.commons.AbstractTest;

import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;

import javax.annotation.ParametersAreNonnullByDefault;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * A test-case that checks the behavior of {@link Bytes}.
 */
@ParametersAreNonnullByDefault
public class BytesTest extends AbstractTest {

    @Test
    public void testToBoolean() {
        boolean actual0 = Bytes.toBoolean(Booleans.toBytes(Boolean.TRUE));
        assertThat(actual0).isTrue();

        boolean actual1 = Bytes.toBoolean(Booleans.toBytes(Boolean.FALSE));
        assertThat(actual1).isFalse();
    }

    @Test
    public void testToShort() {
        final short expected0 = 28433;
        byte[] bytes = ByteBuffer.allocate(Short.BYTES).putShort(expected0).array();
        short actual0 = Bytes.toShort(bytes);
        assertThat(actual0).isEqualTo(expected0);
    }

    @Test
    public void testToChar() {
        final char expected0 = 'N';
        byte[] bytes = ByteBuffer.allocate(Character.BYTES).putChar(expected0).array();
        char actual0 = Bytes.toChar(bytes);
        assertThat(actual0).isEqualTo(expected0);
    }

    @Test
    public void testToInt() {
        final int expected0 = 1654125381;
        byte[] bytes = ByteBuffer.allocate(Integer.BYTES).putInt(expected0).array();
        int actual0 = Bytes.toInt(bytes);
        assertThat(actual0).isEqualTo(expected0);
    }

    @Test
    public void testToLong() {
        final long long0 = 1354566516474223156L;
        byte[] bytes = ByteBuffer.allocate(Long.BYTES).putLong(long0).array();
        long actual0 = Bytes.toLong(bytes);
        assertThat(actual0).isEqualTo(long0);
    }

    @Test
    public void testToFloat() {
        final float expected0 = 139895433915.09579569E18f;
        byte[] bytes = ByteBuffer.allocate(Float.BYTES).putFloat(expected0).array();
        float actual0 = Bytes.toFloat(bytes);
        assertThat(actual0).isEqualTo(expected0);
    }

    @Test
    public void testToDouble() {
        final double expected0 = 19876412.08910810486479E196;
        byte[] bytes = ByteBuffer.allocate(Double.BYTES).putDouble(expected0).array();
        double actual0 = Bytes.toDouble(bytes);
        assertThat(actual0).isEqualTo(expected0);
    }

    @Test
    public void testToString() {
        final String expected0 = "AtlanmodIsAwesome!";
        byte[] bytes = expected0.getBytes();
        String actual0 = Bytes.toString(bytes);
        assertThat(actual0).isEqualTo(expected0);
    }

    @Test
    public void testToStringBinaryAndReverse() {
        String expected0 = "AtlanmodIsAwesome!";
        byte[] bytes = Strings.toBytes(expected0);

        String actual0 = Bytes.toStringBinary(bytes);
        assertThat(actual0).isEqualTo("41746c616e6d6f644973417765736f6d6521");

        byte[] actualBytes0 = Strings.toBytesBinary(actual0);
        assertThat(actualBytes0).contains(bytes);

        assertThat(Bytes.toString(actualBytes0)).isEqualTo(expected0);
    }
}