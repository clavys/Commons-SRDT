package org.atlanmod.commons.predicate;

import java.util.Collection;

public class CollectionPredicate<Yourself extends CollectionPredicate, T extends Collection>
        extends ObjectPredicate<Yourself, T> {

    private static final String PATTERN = "\nExpecting collection %s (%s)";

    public CollectionPredicate(PredicateContext context, T value) {
        super(context, value);
    }

    public Yourself contains(Object elm) {
        if (!value.contains(elm)) {
            context.send(PATTERN, "to contain", elm);
        }
        return me();
    }

    public Yourself containsAll(Collection<?> subCollection) {
        if (!value.containsAll(subCollection)) {
            context.send(PATTERN, "to contain all from", subCollection);
        }
        return me();
    }

    public Yourself isEmpty() {
        if (!value.isEmpty()) {
            context.send(PATTERN, "to be empty", "");
        }
        return me();
    }

    public Yourself notEmpty() {
        if (value.isEmpty()) {
            context.send(PATTERN, "not to be empty", "");
        }
        return me();
    }

    public Yourself sizeIs(int size) {
        if (value.size() != size) {
            context.send(PATTERN, "size to be", size);
        }
        return me();
    }

}
