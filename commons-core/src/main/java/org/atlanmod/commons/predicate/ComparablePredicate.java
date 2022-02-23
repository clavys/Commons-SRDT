package org.atlanmod.commons.predicate;

public class ComparablePredicate<Yourself extends ComparablePredicate, T extends Comparable>
        extends ObjectPredicate<Yourself, T>{

    public ComparablePredicate(PredicateContext context, T value) {
        super(context, value);
    }

    public Yourself isGreaterThan(T other) {
        boolean expected = this.value.compareTo(other) > 0;
        if(!expected) {
            context.send(PATTERN, value, "greater than", other);
        }
        return me();
    }

    public Yourself isGreaterThanOrEqualTo(T other) {
        boolean expected = this.value.compareTo(other) >= 0;
        if(!expected) {
            context.send(PATTERN, value, "greater than", other);
        }
        return me();
    }

    public Yourself isLessThan(T other) {
        boolean expected = this.value.compareTo(other) < 0;
        if(!expected) {
            context.send(PATTERN, value, "less than", other);
        }
        return me();
    }

    public Yourself isLessThanOrEqualTo(T other) {
        boolean expected = this.value.compareTo(other) <= 0;
        if(!expected) {
            context.send(PATTERN, value, "less or equal to", other);
        }
        return me();
    }

    public Yourself isEqualTo(T other) {
        boolean expected = this.value.compareTo(other) == 0;
        if(!expected) {
            context.send(PATTERN, value, "equal to", other);
        }
        return me();
    }
}
