package org.atlanmod.commons.predicate;

import org.atlanmod.commons.collect.MoreArrays;

public class ArrayPredicate extends ObjectPredicate<ArrayPredicate, Object[]> {
  private static final String PATTERN = "\nExpecting array %s (%s)";

  public ArrayPredicate(PredicateContext context, Object[] value) {
    super(context, value);
  }

  public ArrayPredicate contains(Object elm) {
    if (!MoreArrays.contains(value, elm)) {
      context.send(PATTERN, "to contain", elm);
    }
    return this;
  }

  public ArrayPredicate containsAll(Object[] subArray) {
    if (!MoreArrays.containsAll(value, subArray)) {
      context.send(PATTERN, "to contain all from", subArray);
    }
    return this;
  }

  public ArrayPredicate lengthIs(int size) {
    if (value.length != size) {
      context.send(PATTERN, "length to be", size);
    }
    return this;
  }
}
