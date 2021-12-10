package org.atlanmod.commons.predicate;

import java.util.Collection;

public class CollectionPredicate extends ObjectPredicate<CollectionPredicate, Collection> {
    private static final String PATTERN = "\nExpecting collection %s (%s)";

    public CollectionPredicate(PredicateContext context, Collection value) {
        super(context, value);
    }

    public CollectionPredicate contains(Object elm) {
        if (!value.contains(elm)) {
            context.send(PATTERN, "to contain", elm);
        }
        return this;
    }

    public CollectionPredicate containsAll(Collection<?> subCollection) {
        if (!value.containsAll(subCollection)) {
            context.send(PATTERN, "to contain all from", subCollection);
        }
        return this;
    }

    public CollectionPredicate isEmpty() {
        if (!value.isEmpty()) {
            context.send(PATTERN, "to be empty", "");
        }
        return this;
    }

    public CollectionPredicate notEmpty() {
        if (value.isEmpty()) {
            context.send(PATTERN, "not to be empty", "");
        }
        return this;
    }

    public CollectionPredicate sizeIs(int size) {
        if (value.size() != size) {
            context.send(PATTERN, "size to be", size);
        }
        return this;
    }

}
