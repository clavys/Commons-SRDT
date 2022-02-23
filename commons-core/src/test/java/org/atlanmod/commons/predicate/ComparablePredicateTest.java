package org.atlanmod.commons.predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.atlanmod.commons.Guards.checkThat;
import static org.atlanmod.commons.predicate.TestUtility.throwsGuardException;

class ComparablePredicateTest {

    private ComparablePredicate<ComparablePredicate, Comparable> predicate_with_int_10;

    @BeforeEach
    void setUp() {
        Comparable<Integer> int_10 = Integer.valueOf(10);
        predicate_with_int_10 = checkThat(int_10);
    }

    @Test
    void trueIsGreaterThanDoesNothing() {
        predicate_with_int_10.isGreaterThan(1);
    }

    @Test
    void falseIsGreaterThanRaisesException() {
        throwsGuardException(() -> {
            predicate_with_int_10.isGreaterThan(10);
        });
    }

    @Test
    void trueIsLessThanDoesNothing() {
        predicate_with_int_10.isLessThan(11);
    }

    @Test
    void falseIsLessThanRaisesException() {
        throwsGuardException(() -> {
            predicate_with_int_10.isLessThan(10);
        });
    }

    @Test
    void trueIsLessOrEqualToDoesNothing() {
        predicate_with_int_10.isLessThanOrEqualTo(10);
        predicate_with_int_10.isLessThanOrEqualTo(11);
    }

    @Test
    void falseIsLessThanOrEqualToThrowsException() {
        throwsGuardException(() -> {
            predicate_with_int_10.isLessThanOrEqualTo(9);
        });
    }

    @Test
    void trueIsEqualToDoesNothing() {
        predicate_with_int_10.isEqualTo(10);
    }

    @Test
    void falseIsEqualToRaisesException() {
        throwsGuardException(() -> {
            predicate_with_int_10.isEqualTo(11);
        });
        throwsGuardException(() -> predicate_with_int_10.isEqualTo(9));
    }

    @Test
    void trueIsGreaterThanOrEqualToDoesNothing() {
        predicate_with_int_10.isGreaterThanOrEqualTo(10);
        predicate_with_int_10.isGreaterThanOrEqualTo(9);
    }

    @Test
    void falseIsGreaterThanOrEqualToRaisesException() {
        throwsGuardException(() -> {
            predicate_with_int_10.isGreaterThanOrEqualTo(11);
        });
    }
}
