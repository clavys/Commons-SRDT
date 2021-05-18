package org.atlanmod.testing;

import org.atlanmod.commons.Throwables;
import org.atlanmod.commons.annotation.Static;
import org.atlanmod.commons.predicate.BooleanPredicate;
import org.atlanmod.commons.predicate.IntPredicate;
import org.atlanmod.commons.predicate.PredicateContext;

import javax.annotation.ParametersAreNonnullByDefault;

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

    static class AssertionContext implements PredicateContext {
        @Override
        public void send(String pattern, Object... args) {
            throw new AssertionError(String.format(pattern, args));
        }
    }
}
