package org.atlanmod.commons.predicate;

@SuppressWarnings("PMD.GenericsNaming")
public class ComparablePredicate<YOURSELF extends ComparablePredicate, T extends Comparable>
        extends ObjectPredicate<YOURSELF, T>{

    public ComparablePredicate(PredicateContext context, T value) {
        super(context, value);
    }

    public YOURSELF isGreaterThan(T other) {
        boolean expected = this.value.compareTo(other) > 0;
        if(!expected) {
            context.send(PATTERN, value, "greater than", other);
        }
        return me();
    }

    public YOURSELF isGreaterThanOrEqualTo(T other) {
        boolean expected = this.value.compareTo(other) >= 0;
        if(!expected) {
            context.send(PATTERN, value, "greater than", other);
        }
        return me();
    }

    public YOURSELF isLessThan(T other) {
        boolean expected = this.value.compareTo(other) < 0;
        if(!expected) {
            context.send(PATTERN, value, "less than", other);
        }
        return me();
    }

    public YOURSELF isLessThanOrEqualTo(T other) {
        boolean expected = this.value.compareTo(other) <= 0;
        if(!expected) {
            context.send(PATTERN, value, "less or equal to", other);
        }
        return me();
    }

    public YOURSELF isEqualTo(T other) {
        boolean expected = this.value.compareTo(other) == 0;
        if(!expected) {
            context.send(PATTERN, value, "equal to", other);
        }
        return me();
    }
}
