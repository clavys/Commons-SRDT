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
import java.util.Collection;

@Static
@ParametersAreNonnullByDefault
public class Assertions {
    private final static PredicateContext CONTEXT = new AssertionContext();

    private Assertions() {
        throw Throwables.notInstantiableClass(getClass());
    }

    public static BooleanPredicate assertThat(boolean expression) {
        return new BooleanPredicate(CONTEXT, expression);
    }

    public static IntPredicate assertThat(int expression) {
        return new IntPredicate(CONTEXT, expression);
    }

    public static LongPredicate assertThat(long expression) {
        return new LongPredicate(CONTEXT, expression);
    }

    public static StringPredicate assertThat(String expression) {
        return new StringPredicate(CONTEXT, expression);
    }

    public static ObjectPredicate<ObjectPredicate, Object> assertThat(Object expression) {
        return new ObjectPredicate(CONTEXT, expression);
    }

    public static ComparablePredicate<ComparablePredicate, Comparable> assertThat(Comparable expression) {
        return new ComparablePredicate(CONTEXT, expression);
    }

    public static CollectionPredicate assertThat(Collection<?> expression) {
        return new CollectionPredicate(CONTEXT, expression);
    }

    public static ArrayPredicate assertThat(Object[] expression) {
        return new ArrayPredicate(CONTEXT, expression);
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
