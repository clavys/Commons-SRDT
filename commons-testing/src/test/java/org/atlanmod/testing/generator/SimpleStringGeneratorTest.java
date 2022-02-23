package org.atlanmod.testing.generator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.atlanmod.testing.Assertions.assertThat;

class SimpleStringGeneratorTest {

    private SimpleStringGenerator generator;

    @BeforeEach
    void setup() {
        this.generator = new SimpleStringGenerator();
    }

    @Test
    void testGenerate() {
        String first = generator.generate();
        String second = generator.generate();

        assertThat(first).isDifferentFrom(second);
    }
}
