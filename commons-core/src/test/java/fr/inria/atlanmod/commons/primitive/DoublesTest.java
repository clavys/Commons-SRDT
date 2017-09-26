package fr.inria.atlanmod.commons.primitive;

import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;

import javax.annotation.ParametersAreNonnullByDefault;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * A test-case that checks the behavior of {@link Doubles}.
 */
@ParametersAreNonnullByDefault
public class DoublesTest {

    @Test
    public void testToBytes() {
        final Double double0 = 19876412.08910810486479E196;
        byte[] actual0 = Doubles.toBytes(double0);
        byte[] expected0 = ByteBuffer.allocate(Double.BYTES).putDouble(double0).array();
        assertThat(actual0).containsExactly(expected0);
    }
}