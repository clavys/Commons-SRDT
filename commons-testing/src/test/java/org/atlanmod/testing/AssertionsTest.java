package org.atlanmod.testing;

import org.junit.jupiter.api.Test;

import static org.atlanmod.testing.TestUtility.throwsAssertionError;

class AssertionsTest {

    // region Boolean Predicates
    @Test
    void assertingATrueExpressionIsFalseRaises_AssertionError() {
        throwsAssertionError(() -> Assertions.assertThat(true).isFalse());
    }

    @Test
    void assertingAFalseExpressionIsTrueRaises_AssertionError() {
        throwsAssertionError(() -> Assertions.assertThat(false).isTrue());
    }

    @Test
    void correctBooleanAssertions_Raise_Nothing() {
        Assertions.assertThat(true).isTrue();
        Assertions.assertThat(false).isFalse();
    }
    // endregion

    // region Int Predicates
    @Test
    void assertingOneIsZeroRaises_AssertionError() {
        throwsAssertionError(() -> Assertions.assertThat(1).isZero());
    }

    @Test
    void asserting_11_IsBetween_0_and_10_Raises_AssertionError() {
        throwsAssertionError(() -> Assertions.assertThat(11).isBetween(0,10));
    }

    @Test
    void asserting_10_IsLessThan_10_Raises_AssertionError() {
        throwsAssertionError(() -> Assertions.assertThat(10).isLessThan(10));
    }

    @Test
    void asserting_10_IsLessThanOrEqualTo_9_Raises_AssertionError() {
        throwsAssertionError(() -> Assertions.assertThat(10).isLessThanOrEqualTo(9));
    }

    @Test
    void asserting_10_IsGreaterThan_10_Raises_AssertionError() {
        throwsAssertionError(() -> Assertions.assertThat(10).isGreaterThan(10));
    }

    @Test
    void asserting_10_IsGreaterThanOrEqualTo_11_Raises_AssertionError() {
        throwsAssertionError(() -> Assertions.assertThat(10).isGreaterThanOrEqualTo(11));
    }

    @Test
    void asserting_10_IsEqualTo_11_Raises_AssertionError() {
        throwsAssertionError(() -> Assertions.assertThat(10).isEqualTo(11));
    }

    @Test
    void asserting_10_IsDifferentFrom_10_Raises_AssertionError() {
        throwsAssertionError(() -> Assertions.assertThat(10).isDifferentFrom(10));
    }

    @Test
    void correctIntAssertions_Raise_Nothing() {
        Assertions.assertThat(0).isZero();
        Assertions.assertThat(0).isBetween(0,10);
        Assertions.assertThat(10).isBetween(0,10);
        Assertions.assertThat(10).isLessThan(11);
        Assertions.assertThat(10).isLessThanOrEqualTo(10);
        Assertions.assertThat(10).isLessThanOrEqualTo(11);
        Assertions.assertThat(10).isGreaterThan(9);
        Assertions.assertThat(10).isGreaterThanOrEqualTo(10);
        Assertions.assertThat(10).isEqualTo(10);
        Assertions.assertThat(10).isDifferentFrom(11);
    }
    // endregion
}