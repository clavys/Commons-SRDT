<<<<<<< HEAD
package org.atlanmod.commons.predicate;

import org.junit.jupiter.api.Test;

import static org.atlanmod.commons.Preconditions.requireThat;
import static org.atlanmod.commons.predicate.TestUtility.throwsPreconditionError;
import static org.junit.jupiter.api.Assertions.*;

class ObjectPredicateTest {

    @Test
    void isNull() {
        throwsPreconditionError(() ->
                requireThat(new Object()).isNull());
    }

    @Test
    void isNotNull() {
        throwsPreconditionError(() ->
                requireThat(null).isNotNull());
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

        requireThat(null).isNull();
        requireThat(neo).isNotNull();
        requireThat(neo).isEqualTo(neo);
        requireThat(neo).isDifferentFrom(new Object());
    }
=======
import static org.junit.jupiter.api.Assertions.*;
class ObjectPredicateTest {
  
>>>>>>> 517dd1dbb36d7f9c02e95b635dab489449d85be3
}