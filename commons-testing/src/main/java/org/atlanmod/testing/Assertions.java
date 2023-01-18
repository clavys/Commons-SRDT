/*
 * Copyright (c) 2022 Naomod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */
package org.atlanmod.testing;

import org.atlanmod.commons.Throwables;
import org.atlanmod.commons.annotation.Static;
import org.atlanmod.commons.predicate.*;

import javax.annotation.ParametersAreNonnullByDefault;
import java.time.LocalDate;
import java.util.Collection;

@Static
@ParametersAreNonnullByDefault
public class Assertions {
    private final static PredicateContext CONTEXT = new AssertionContext();

    private Assertions() {
        throw Throwables.notInstantiableClass(getClass());
    }

    public static BooleanPredicate assertThat(boolean actual) {
        return new BooleanPredicate(CONTEXT, actual);
    }

    public static IntPredicate assertThat(int actual) {
        return new IntPredicate(CONTEXT, actual);
    }

    public static LongPredicate assertThat(long actual) {
        return new LongPredicate(CONTEXT, actual);
    }

    public static StringPredicate assertThat(String actual) {
        return new StringPredicate(CONTEXT, actual);
    }

    public static ObjectPredicate<ObjectPredicate, Object> assertThat(Object actual) {
        return new ObjectPredicate(CONTEXT, actual);
    }

    public static ComparablePredicate<ComparablePredicate, Comparable> assertThat(Comparable actual) {
        return new ComparablePredicate(CONTEXT, actual);
    }

    public static CollectionPredicate assertThat(Collection<?> actual) {
        return new CollectionPredicate(CONTEXT, actual);
    }

    public static LocalDatePredicate assertThat(LocalDate expression) {
        return new LocalDatePredicate(CONTEXT, expression);
    }

    public static ArrayPredicate assertThat(Object[] actual) {
        return new ArrayPredicate(CONTEXT, actual);
    }

    static class AssertionContext implements PredicateContext {
        @Override
        public void send(String pattern, Object... args) {
            throw new AssertionError(String.format(pattern, args));
        }
    }

    public static void fail(String message) {
        throw new AssertionError(message);
    }
}
