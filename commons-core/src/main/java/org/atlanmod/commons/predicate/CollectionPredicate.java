package org.atlanmod.commons.predicate;

import java.util.Collection;

public class CollectionPredicate<Myself extends CollectionPredicate, T extends Collection>
        extends ObjectPredicate<Myself, T> {

    private static final String PATTERN = "\nExpecting collection %s (%s)";

    public CollectionPredicate(PredicateContext context, T value) {
        super(context, value);
    }

    public Myself contains(Object elm) {
        if (!value.contains(elm)) {
            context.send(PATTERN, "to contain", elm);
        }
        return me();
    }

    public Myself containsAll(Collection<?> subCollection) {
        if (!value.containsAll(subCollection)) {
            context.send(PATTERN, "to contain all from", subCollection);
        }
        return me();
    }

    public Myself isEmpty() {
        if (!value.isEmpty()) {
            context.send(PATTERN, "to be empty", "");
        }
        return me();
    }

    public Myself notEmpty() {
        if (value.isEmpty()) {
            context.send(PATTERN, "not to be empty", "");
        }
        return me();
    }

    public Myself sizeIs(int size) {
        if (value.size() != size) {
            context.send(PATTERN, "size to be", size);
        }
        return me();
    }

}
