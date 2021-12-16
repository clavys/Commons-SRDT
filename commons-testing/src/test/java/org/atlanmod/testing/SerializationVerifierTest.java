package org.atlanmod.testing;

import org.junit.jupiter.api.Test;

import java.io.IOException;

class SerializationVerifierTest {

    @Test
    void check() throws IOException, ClassNotFoundException {
        Verifier.verifySerialization(Person.class)
                .withArguments("Anna",25)
                .check();
    }
}