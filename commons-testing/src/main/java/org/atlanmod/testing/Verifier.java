/*
 * Copyright (c) 2022 Naomod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */
package org.atlanmod.testing;

import org.atlanmod.commons.Throwables;
import org.atlanmod.testing.generator.*;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Array;
import java.util.*;

/**
 * Entry point for verification methods that improve unit tests.
 * Each method in this class is a static factory for a specific verification object.
 *
 * For instance:
 *
 * <pre><code class='java'>
 * {@link Verifier#verifyEqualsOf(Class) verifyEqualsOf(String.class)}
 *      .{@link EqualsVerifier#withArguments(Object...) withArguments("a String"}
 *      .{@link EqualsVerifier#andVariants(Object...) andVariants("another String"}
 *      .{@link EqualsVerifier#check() check()}
 *
 * </code></pre>
 *
 * <pre><code class='java'>
 * {@link Verifier#verifySerialization(Class) verifySerialization(String.class)}
 *      .{@link SerializationVerifier#withArguments(Object...) withArguments("a String"}
 *      .{@link SerializationVerifier#check() check()}
 *
 * </code></pre>
 */
public class Verifier {
    private static final Instantiator instatiator = new Instantiator();

    private Verifier() {
        throw Throwables.notInstantiableClass(getClass());
    }

    /**
     * Creates a {@link EqualsVerifier} for class {@code type}.
     *
     * @param type the class whose {@code equals()} method will be verified.
     * @param <T> the actual class of the class {@type}.
     * @return an instance of {@link EqualsVerifier}.
     */
    public static <T> EqualsVerifier<T> verifyEqualsOf(Class<T> type) {
        return new EqualsVerifier<>(type);
    }

    /**
     * Creates a {@link SerializationVerifier} for class {@code type}.
     *
     * @param type the class whose {@code serialize()} method will be verified.
     * @param <T> the actual class of the class {@type}.
     * @return an instance of {@link SerializationVerifier}.
     */
    public static <T extends Serializable> SerializationVerifier<T> verifySerialization(Class<T> type) {
        return new SerializationVerifier<>(type);
    }
}
