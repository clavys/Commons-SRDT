package org.atlanmod.commons.predicate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.atlanmod.commons.Guards.requireThat;
import static org.atlanmod.commons.predicate.Utility.throwsPreconditionError;

class IntPredicateTest {

    // region False Predicates

    @Test
    void is_Not_Zero() {
        throwsPreconditionError(
                () -> {
                    requireThat(12).isZero();
                }
        );
    }

    @Test
    void is_Not_GreaterThan() {
        throwsPreconditionError(
                () -> {
                    requireThat(12).isGreaterThan(12);
                }
        );
    }

    @Test
    void is_Not_GreaterThanOrEqualTo() {
        throwsPreconditionError(
                () -> {
                    requireThat(12).isGreaterThanOrEqualTo(13);
                }
        );
    }

    @Test
    void is_Not_LessThan() {
        throwsPreconditionError(
                () -> {
                    requireThat(12).isLessThan(12);
                }
        );
    }

    @Test
    void is_Not_LessThanOrEqualTo() {
        throwsPreconditionError(
                () -> {
                    requireThat(12).isLessThanOrEqualTo(11);
                }
        );
    }

    @Test
    void is_Not_Between() {
        throwsPreconditionError(() -> {
            requireThat(0).isBetween(1, 10);
        });
    }

    // endregion

    // region True Predicated

    @Test
    void isZero() {
        requireThat(0).isZero();
    }

    @Test
    void isGreaterThan() {

        requireThat(12).isGreaterThan(11);

    }

    @Test
    void isGreaterThanOrEqualTo() {
        requireThat(12)
                .isGreaterThanOrEqualTo(12)
                .isGreaterThanOrEqualTo(11);

    }

    @Test
    void isLessThan() {
        requireThat(12).isLessThan(13);
    }

    @Test
    void isLessThanOrEqualTo() {
        requireThat(12)
                .isLessThanOrEqualTo(12)
                .isLessThanOrEqualTo(13);
    }

    @ValueSource(ints = {1, 10, 2, 9})
    @ParameterizedTest
    void isBetween(int value) {
        requireThat(value).isBetween(1, 10);
    }

    // endregion

}