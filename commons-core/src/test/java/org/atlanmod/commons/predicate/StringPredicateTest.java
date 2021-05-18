<<<<<<< HEAD
package org.atlanmod.commons.predicate;

import org.junit.jupiter.api.Test;

import static org.atlanmod.commons.Preconditions.requireThat;
import static org.atlanmod.commons.predicate.TestUtility.throwsPreconditionError;

class StringPredicateTest {

    @Test
    void contains() {
        throwsPreconditionError(() ->
                requireThat("atlanmod-commons").contains("other"));
    }


    @Test
    void correctPreconditions() {
        requireThat("atlanmod-commons")
                .contains("atlanmod")
                .isNotNull();
    }
=======
import static org.junit.jupiter.api.Assertions.*;
class StringPredicateTest {
  
>>>>>>> 517dd1dbb36d7f9c02e95b635dab489449d85be3
}