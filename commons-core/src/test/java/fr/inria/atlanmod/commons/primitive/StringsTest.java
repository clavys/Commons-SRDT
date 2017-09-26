package fr.inria.atlanmod.commons.primitive;

import org.junit.jupiter.api.Test;

import javax.annotation.ParametersAreNonnullByDefault;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * A test-case that checks the behavior of {@link Strings}.
 */
@ParametersAreNonnullByDefault
public class StringsTest {

    @Test
    public void testIsNullOrEmpty() {
        String string0 = "";
        String string1 = null;
        String string2 = "MyString";

        assertThat(Strings.isNullOrEmpty(string0)).isTrue();
        assertThat(Strings.isNullOrEmpty(string1)).isTrue();
        assertThat(Strings.isNullOrEmpty(string2)).isFalse();
    }

    @Test
    public void testNullToEmpty() {
        String string0 = "";
        String string1 = null;
        String string2 = "MyString";

        assertThat(Strings.nullToEmpty(string0)).isNotNull().isEmpty();
        assertThat(Strings.nullToEmpty(string1)).isNotNull().isEmpty();
        assertThat(Strings.nullToEmpty(string2)).isEqualTo(string2);
    }

    @Test
    public void testEmptyToNull() {
        String string0 = "";
        String string1 = null;
        String string2 = "MyString";

        assertThat(Strings.emptyToNull(string0)).isNull();
        assertThat(Strings.emptyToNull(string1)).isNull();
        assertThat(Strings.emptyToNull(string2)).isEqualTo(string2);
    }

    @Test
    public void testIsBinary() {
        String string0 = "";
        String string1 = null;
        String string2 = "MyString";
        String string3 = "0123456789ABCDEF";
        String string4 = "0123456789abcdef";
        String string5 = "0123456789aBcdEf";
        String string6 = "G";

        assertThat(Strings.isBinary(string0)).isFalse();
        assertThat(Strings.isBinary(string1)).isFalse();
        assertThat(Strings.isBinary(string2)).isFalse();
        assertThat(Strings.isBinary(string3)).isTrue();
        assertThat(Strings.isBinary(string4)).isTrue();
        assertThat(Strings.isBinary(string5)).isTrue();
        assertThat(Strings.isBinary(string6)).isFalse();
    }

    @Test
    public void testToBytes() {
        final String string0 = "AtlanmodIsAwesome!";
        byte[] actual0 = Strings.toBytes(string0);
        byte[] expected0 = string0.getBytes();
        assertThat(actual0).containsExactly(expected0);
    }
}