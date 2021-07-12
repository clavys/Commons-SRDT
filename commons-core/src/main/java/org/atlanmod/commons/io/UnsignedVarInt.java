package org.atlanmod.commons.io;

import javax.annotation.Nonnull;
import java.nio.ByteBuffer;
import java.util.Objects;

import static org.atlanmod.commons.Guards.*;

/**
 * The `CompressedInt` class allows the representation
 *
 * @author sunye
 * @since 1.1.0
 */
public class CompressedInt extends UnsignedNumber implements Comparable<CompressedInt> {

    // @formatter:off
    public static final long    MIN_VALUE = 0;
    public static final long    MAX_VALUE = 0xffffffffL;
    public static final int     SIZE = 32;
    public static final int     BYTES = SIZE / Byte.SIZE;
    private static final long   UNSIGNED_INT_MASK = 0xFFFFFFFFL;
    // @formatter: on

    private final long value;

    private CompressedInt(long value) {
        this.value = value;
    }

    /**
     * Wraps an `int` value into a `CompressedInt`.
     *
     * @param value A unsigned 32-bits unsigned int value.
     * @return un object wrapping the unsigned int value.
     */
    public static CompressedInt fromInt(int value) {
        return new CompressedInt(value & UNSIGNED_INT_MASK);
    }


    public static CompressedInt fromLong(long value) {
        checkArgument(value >= MIN_VALUE && value <= MAX_VALUE);

        long unsigned = value & UNSIGNED_INT_MASK;
        return new CompressedInt(unsigned);
    }

    /**
     * Compares this object to the specified object.
     * The result is `true` if and only if the argument is not null and is an `UnsignedInt` object that contains
     * the same `long` value as this object.
     *
     * @param obj the object to compare with.
     *
     * @return `true` if the objects are the same; `false` otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        //@formatter:off
        if (this == obj) {return true;}
        if (obj == null || getClass() != obj.getClass()) {return false;}
        //@formatter:on

        CompressedInt that = (CompressedInt) obj;
        return value == that.value;
    }

    /**
     * Returns a hash code for this `UnsignedInt`.
     * @return a hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public int intValue() {
        return (int) value;
    }

    @Override
    public long longValue() {
        return value;
    }

    @Override
    public float floatValue() {
        return (float) value;
    }

    @Override
    public double doubleValue() {
        return (double) value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public int compareTo(CompressedInt other) {
        return Long.compare(this.value, other.value);
    }

    /**
     * Encodes a {@code UnsignedInt} to a {@code byte} array, following the big endian order.
     *
     * @return a {@code byte} array
     */
    @Nonnull
    public byte[] toBytes() {
        final int length = UnsignedInt.BYTES;
        final int value = this.intValue();

        byte[] bytes = new byte[length];

        for (int i = 0; i < length; i++) {
            int shift = Byte.SIZE * (length - i - 1);
            bytes[i] = (byte) (value >> shift);
        }

        return bytes;
    }

    public static byte[] toBytes(int value) {
        if (value <= 0x3F) {
            return new byte[]{(byte) value};
        } else if (value <= 0x3FFF) {
            return new byte[]{(byte) (value >> 8 | 0x40), (byte) (value & 0xFF)};
        } else if (value <= 0x3FFFFF) {
            return new byte[]{(byte) (value >> 16 | 0x80), (byte) (value >> 8 & 0xFF), (byte) (value & 0xFF)};
        } else if (value <= 0x3FFFFFFF) {
            return new byte[]{(byte) (value >> 24 | 0xC0),
                    (byte) (value >> 16 & 0xFF),
                    (byte) (value >> 8 & 0xFF),
                    (byte) (value & 0xFF)};
        }
        else return new byte[0];
    }

    /**
     * Encode the specified long as a variable length integer into the supplied {@link ByteBuffer}
     *
     * @param buf the buffer to write to
     * @param value the long value
     */
    public static void writeVLong(ByteBuffer buf, long value) {
        if(value < 0)                                buf.put((byte)0x81);
        if(value > 0xFFFFFFFFFFFFFFL || value < 0)   buf.put((byte)(0x80 | ((value >>> 56) & 0x7FL)));
        if(value > 0x1FFFFFFFFFFFFL || value < 0)    buf.put((byte)(0x80 | ((value >>> 49) & 0x7FL)));
        if(value > 0x3FFFFFFFFFFL || value < 0)      buf.put((byte)(0x80 | ((value >>> 42) & 0x7FL)));
        if(value > 0x7FFFFFFFFL || value < 0)        buf.put((byte)(0x80 | ((value >>> 35) & 0x7FL)));
        if(value > 0xFFFFFFFL || value < 0)          buf.put((byte)(0x80 | ((value >>> 28) & 0x7FL)));
        if(value > 0x1FFFFFL || value < 0)           buf.put((byte)(0x80 | ((value >>> 21) & 0x7FL)));
        if(value > 0x3FFFL || value < 0)             buf.put((byte)(0x80 | ((value >>> 14) & 0x7FL)));
        if(value > 0x7FL || value < 0)               buf.put((byte)(0x80 | ((value >>>  7) & 0x7FL)));

        buf.put((byte)(value & 0x7FL));
    }

    /**
     * Read a variable length long from the supplied {@link ByteBuffer} starting at the specified position.
     * @param arr the byte data to read from
     * @return the long value
     */
    public static long readVLong(ByteBuffer arr) {
        byte b = arr.get();

        if(b == (byte) 0x80)
            throw new RuntimeException("Attempting to read null value as long");

        long value = b & 0x7F;
        while ((b & 0x80) != 0) {
            b = arr.get();
            value <<= 7;
            value |= (b & 0x7F);
        }

        return value;
    }

    public static UnsignedInt fromBytes(byte[] bytes) {
        checkNotNull(bytes, "bytes");
        checkEqualTo(bytes.length, UnsignedInt.BYTES, "bytes has wrong size: %d", bytes.length);

        int value = 0;
        final int length = UnsignedInt.BYTES - 1;
        for (int i = length; i >= 0; i--) {
            value |= (bytes[i]) << Byte.SIZE * (length - i);
        }

        return UnsignedInt.fromInt(value);
    }
}
