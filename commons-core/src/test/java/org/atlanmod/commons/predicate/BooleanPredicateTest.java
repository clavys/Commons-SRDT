package org.atlanmod.commons.predicate;

import org.junit.jupiter.api.Test;

import static org.atlanmod.commons.Guards.requireThat;
import static org.atlanmod.commons.predicate.Utility.throwsPreconditionError;

class BooleanPredicateTest {

    // region Valid Predicate
    @Test
    void isTrue() {
        requireThat(true).isTrue();
    }

    @Test
    void isFalse() {
        requireThat(false).isFalse();
    }
    // endregion

    // region Invalid Predicate
    @Test
    void invalid_isTrue() {
        throwsPreconditionError(() -> {
            requireThat(false).isTrue();
        });
    }

    @Test
    void invalid_isFalse() {
        throwsPreconditionError(() -> {
            requireThat(true).isFalse();
        });
    }
    // end region
}