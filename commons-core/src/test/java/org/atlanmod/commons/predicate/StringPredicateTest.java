package org.atlanmod.commons.predicate;

import org.junit.jupiter.api.Test;

import static org.atlanmod.commons.Preconditions.requireThat;
import static org.atlanmod.commons.predicate.TestUtility.throwsPreconditionError;

class StringPredicateTest {

    @Test
    void falseContainsRaisesError() {
        throwsPreconditionError(() ->
                requireThat("atlanmod-commons").contains("other"));
    }

    @Test
    void trueContainsDoesNothing() {
        requireThat("atlanmod-commons")
                .contains("atlanmod");
    }

    @Test
    void trueIsNotEmptyDoesNothing() {
        requireThat("a").isNotEmpty();
    }

    @Test
    void falseIsNotEmptyRaisesError() {
        throwsPreconditionError(() -> requireThat("").isNotEmpty());
    }
}
