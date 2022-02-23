package org.atlanmod.commons.predicate;

import org.junit.jupiter.api.Test;

import static org.atlanmod.commons.Preconditions.requireThat;
import static org.atlanmod.commons.predicate.TestUtility.throwsPreconditionError;

class ObjectPredicateTest {

    @Test
    void isNull() {
        throwsPreconditionError(() ->
                requireThat(new Object()).isNull());
    }

    @Test
    void isNotNull() {
        Object nullObject = null;
        throwsPreconditionError(() ->
                requireThat(nullObject).isNotNull());
    }

    @Test
    void isEqualTo() {
        throwsPreconditionError(() ->
                requireThat(new Object()).isEqualTo(new Object()));
    }

    @Test
    void isDifferentFrom() {
        Object neo = new Object();
        throwsPreconditionError(() ->
                requireThat(neo).isDifferentFrom(neo));
    }

    @Test
    void correctBehavior() {
        Object neo = new Object();
        Object nullObject = null;

        requireThat(nullObject).isNull();
        requireThat(neo).isNotNull();
        requireThat(neo).isEqualTo(neo);
        requireThat(neo).isDifferentFrom(new Object());
    }
}
