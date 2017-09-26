package fr.inria.atlanmod.commons.primitive;

import org.junit.jupiter.api.Test;

import javax.annotation.ParametersAreNonnullByDefault;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * A test-case that checks the behavior of {@link Booleans}.
 */
@ParametersAreNonnullByDefault
public class BooleansTest {

    @Test
    public void testToBytes() {
        final byte zero = 0;

        byte[] actual0 = Booleans.toBytes(Boolean.TRUE);
        assertThat(actual0[0]).isNotEqualTo(zero);

        byte[] actual1 = Booleans.toBytes(Boolean.FALSE);
        assertThat(actual1[0]).isEqualTo(zero);
    }
}