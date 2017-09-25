package fr.inria.atlanmod.commons.primitive;

import org.junit.Test;

import java.nio.ByteBuffer;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * A test-case that checks the behavior of {@link Shorts}.
 */
public class ShortsTest {

    @Test
    public void testToBytes() {
        final Short short0 = 28433;
        byte[] actual0 = Shorts.toBytes(short0);
        byte[] expected0 = ByteBuffer.allocate(Short.BYTES).putShort(short0).array();
        assertThat(actual0).containsExactly(expected0);
    }
}