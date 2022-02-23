package org.atlanmod.commons.predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.atlanmod.commons.Guards.checkThat;
import static org.atlanmod.commons.predicate.TestUtility.throwsGuardException;

class CollectionPredicateTest {

    private Collection<Integer> ints;

    @BeforeEach
    void setUp() {
        ints = new ArrayList<>(5);
    }

    @Test
    void trueContainsDoesNothing() {
        ints.add(42);
        checkThat(ints).contains(42);
    }

    @Test
    void falseContainsRaisesException() {
        throwsGuardException(() -> {
            checkThat(ints).contains(99);
        });

    }

    @Test
    void trueContainsAllDoesNothing() {
        ints.addAll(Arrays.asList(1, 2, 3, 4, 5));
        checkThat(ints).containsAll(Arrays.asList(1, 2, 3, 4, 5));
    }

    @Test
    void falseConstainsAllRaisesException() {
        ints.addAll(Arrays.asList(1, 2, 3, 4, 5));
        throwsGuardException(() -> {
            checkThat(ints).containsAll(Arrays.asList(1, 2, 3, 4, 5, 6));
        });

    }

    @Test
    void trueIsEmptyDoesNothing() {
        checkThat(ints).isEmpty();
    }

    @Test
    void falseIsEmptyRaisesException() {
        ints.addAll(Arrays.asList(22, 2, 2022));
        throwsGuardException(() -> {
            checkThat(ints).isEmpty();
        });
    }

    @Test
    void trueSizeIsDoesNothing() {
        ints.addAll(Arrays.asList(22, 2, 2022));
        checkThat(ints).sizeIs(ints.size());
    }

    @Test
    void falseSizeIsRaisesException() {
        throwsGuardException(() -> {
            checkThat(ints).sizeIs(10);
        });
    }
}
