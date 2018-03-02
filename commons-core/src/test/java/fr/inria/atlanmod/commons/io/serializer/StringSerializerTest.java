/*
 * Copyright (c) 2017-2018 Atlanmod, Inria, LS2N, and IMT Nantes.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package fr.inria.atlanmod.commons.io.serializer;

import fr.inria.atlanmod.commons.AbstractTest;
import fr.inria.atlanmod.commons.function.Converter;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * A test-case that checks the behavior of {@link StringSerializer} instances.
 */
@ParametersAreNonnullByDefault
public class StringSerializerTest extends AbstractSerializerTest {

    @Test
    void testSerializeDeserializeBase16() {
        Serializer<List<Integer>> binarySerializer = SerializerFactory.getInstance().forAny();
        StringSerializer<List<Integer>> serializer = StringSerializerFactory.base16(binarySerializer);

        List<Integer> object = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<Integer> result = process(object, serializer);

        assertThat(result).containsExactly(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    }

    @Test
    void testSerializeDeserializeBase64() {
        Serializer<List<Integer>> binarySerializer = SerializerFactory.getInstance().forAny();
        StringSerializer<List<Integer>> serializer = StringSerializerFactory.base64(binarySerializer);

        List<Integer> object = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<Integer> result = process(object, serializer);

        assertThat(result).containsExactly(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    }
}
