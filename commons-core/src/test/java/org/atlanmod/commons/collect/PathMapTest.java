package org.atlanmod.commons.collect;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class PathMapTest {
    private PathMap<String, Integer> tree;

    @BeforeEach
    void init() {
        tree = new PathMap<>();
        tree.put(Path.of("a", "b", "c"), 1);
        tree.put(Path.of("a", "b"), 2);
        tree.put(Path.of("a"), 3);
        tree.put(Path.of("b", "c", "d"), 4);
    }


    @Test
    void testPut() {
        assertThat(tree.get(Path.of("a", "b", "c"))).isEqualTo(Optional.of(1));
    }

    @Test
    void testToString() {
        String actual = tree.toString();

        assertThat(actual)
                .contains("a")
                .contains("b")
                .contains("c");
    }

    @Test
    void testComputeIfAbsent() {
        PathMap<String, Integer> map = new PathMap<>();
        Integer expected = Integer.valueOf(3);
        Integer actual = map.computeIfAbsent(Path.of("a", "aa", "aaa"), (x) -> x.length()).get();

        assertThat(actual).isEqualTo(expected);
        assertThat(map.get(Path.of("a", "aa"))).get().isEqualTo(Integer.valueOf(2));
        assertThat(map.get(Path.of("a"))).get().isEqualTo(Integer.valueOf(1));
    }

    @Test
    void testApply() {
        tree.apply((parent, child) -> {
            System.out.println(parent + " : " + child);
        });
    }

    @Test
    void testFromDottedString() {
        Path expected = Path.of("org", "atlanmod", "neodisco");
        Path actual = Path.fromDottedString("org.atlanmod.neodisco");
        assertThat(actual).isEqualTo(expected);
    }
}
