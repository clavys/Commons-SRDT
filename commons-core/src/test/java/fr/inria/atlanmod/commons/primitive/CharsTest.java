package fr.inria.atlanmod.commons.primitive;

import org.junit.Test;

import java.nio.ByteBuffer;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * A test-case that checks the behavior of {@link Chars}.
 */
public class CharsTest {

    @Test
    public void testToBytes() {
        final Character chart0 = 'N';
        byte[] actual0 = Chars.toBytes(chart0);
        byte[] expected0 = ByteBuffer.allocate(Character.BYTES).putChar(chart0).array();
        assertThat(actual0).containsExactly(expected0);
    }
}