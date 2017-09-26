package fr.inria.atlanmod.commons.primitive;

import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;

import javax.annotation.ParametersAreNonnullByDefault;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * A test-case that checks the behavior of {@link Longs}.
 */
@ParametersAreNonnullByDefault
public class LongsTest {

    @Test
    public void testToBytes() {
        final Long long0 = 1354566516474223156L;
        byte[] actual0 = Longs.toBytes(long0);
        byte[] expected0 = ByteBuffer.allocate(Long.BYTES).putLong(long0).array();
        assertThat(actual0).containsExactly(expected0);
    }
}