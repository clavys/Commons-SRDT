package fr.inria.atlanmod.commons.primitive;

import org.junit.Test;

import java.nio.ByteBuffer;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * A test-case that checks the behavior of {@link Floats}.
 */
public class FloatsTest {

    @Test
    public void testToBytes() {
        final Float float0 = 139895433915.09579569E18f;
        byte[] actual0 = Floats.toBytes(float0);
        byte[] expected0 = ByteBuffer.allocate(Float.BYTES).putFloat(float0).array();
        assertThat(actual0).containsExactly(expected0);
    }
}