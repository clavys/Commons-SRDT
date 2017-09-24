package fr.inria.atlanmod.commons.primitive;

import org.junit.Test;

import java.nio.ByteBuffer;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * A test-case that checks the behavior of {@link Ints}.
 */
public class IntsTest {

    @Test
    public void testToBytes() {
        final Integer int0 = 1654125381;
        byte[] actual0 = Ints.toBytes(int0);
        byte[] expected0 = ByteBuffer.allocate(Integer.BYTES).putInt(int0).array();
        assertThat(actual0).containsExactly(expected0);
    }
}