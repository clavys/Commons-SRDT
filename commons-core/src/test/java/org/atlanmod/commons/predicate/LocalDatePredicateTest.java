package org.atlanmod.commons.predicate;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.atlanmod.commons.Preconditions.requireThat;
import static org.atlanmod.commons.predicate.TestUtility.throwsPreconditionError;
import static org.junit.jupiter.api.Assertions.*;

class LocalDatePredicateTest {

    private final LocalDate january_1st_2022 = LocalDate.of(2022,1,1);
    private final LocalDate october_10th_2022 = LocalDate.of(2022, 10, 10);

    @Test
    void trueIsAfterDoesNothing() {
        requireThat(october_10th_2022).isAfter(january_1st_2022);
    }

    @Test
    void falseIsAfterRaisesError() {
        throwsPreconditionError(() -> {
            requireThat(january_1st_2022).isAfter(october_10th_2022);
        });
    }

    @Test
    void trueIsABeforeDoesNothing() {
        requireThat(january_1st_2022).isBefore(october_10th_2022);
    }

    @Test
    void falseIsBeforeRaisesError() {
        throwsPreconditionError(() -> {
            requireThat(october_10th_2022).isBefore(january_1st_2022);
        });
    }

}