package org.atlanmod.commons.tuple;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class PairTest {

    private Pair<Integer, String> pair_int_string;
    private Pair<String, Integer> pair_string_int;


    @BeforeEach
    void init() {
        pair_int_string = Pair.of(Integer.valueOf(1), "one");
        pair_string_int = Pair.of("one", Integer.valueOf(1));
    }


    @Test
    void testConstructor() {
        Pair<Integer, String> pair_13 = Pair.of(Integer.valueOf(13), "Thirteen");
        assertThat(pair_13.left).isEqualTo(13);
        assertThat(pair_13.right).isEqualTo("Thirteen");
    }

    @Test
    void testEquals() {
        assertThat(pair_int_string).isEqualTo(pair_int_string);
        assertThat(pair_int_string).isEqualTo(Pair.of(Integer.valueOf(1), "one"));
        assertThat(pair_int_string).isNotEqualTo(null);
        assertThat(pair_int_string.equals(pair_string_int)).isFalse();
    }

    @Test
    void swap() {
        Pair<String, Integer> swapped = pair_int_string.swap();

        assertThat(pair_int_string).isNotEqualTo(swapped);
        assertThat(pair_int_string).isEqualTo(swapped.swap());
        assertThat(swapped).isEqualTo(Pair.of("one", Integer.valueOf(1)));
    }

    @Test
    void testHashCode() {
        assertThat(pair_int_string.hashCode()).isNotEqualTo(pair_string_int.hashCode());
        assertThat(pair_int_string.hashCode()).isEqualTo(Pair.of(Integer.valueOf(1), "one").hashCode());
    }

}