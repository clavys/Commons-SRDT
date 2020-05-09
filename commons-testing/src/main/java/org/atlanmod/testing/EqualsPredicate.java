package org.atlanmod.testing;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Stream;
import org.atlanmod.commons.reflect.MoreReflection;

public class EqualsPredicate {
    private Class klass;
    private Object[] arguments;
    private Object[] variants;

    public EqualsPredicate(Class klass) {
        this.klass = klass;
    }

    public EqualsPredicate withArguments(Object[] objects) {
        arguments = objects;
        return this;
    }

    public EqualsPredicate andVariants(Object[] objects) {
        variants = objects;
        return this;
    }

    public void check() {
        checkArguments(arguments, variants);
        Class[] arg1Types = mapToClasses(arguments);

        Function<Object[], Object> instantiator = MoreReflection.getInstantiator(klass, arg1Types);

        Object[] freaks = new Object[arguments.length];
        for (int i = 0; i < arguments.length; i++) {
            Object[] variation = Arrays.copyOf(arguments, arguments.length);
            variation[i] = variants[i];
            freaks[i] = instantiator.apply(variation);
        }

        Object one = instantiator.apply(arguments);
        Object clone = instantiator.apply(arguments);

        assertEqualsToSelf(one);
        assertEquals(one,clone);
        assertDifferentFromNull(one);
        for (Object each : freaks) {
            assertDifferent(one,each);
        }
    }

    private  void checkArguments(Object[] args1, Object[] args2) {
        int length = args1.length;
        if (args2.length != length) {
            throw new IllegalArgumentException("Argument arrays must have the same length");
        }
        for (int i = 0; i < length; i++) {
            if (args1[i].equals(args2[i])) {
                throw new IllegalArgumentException("Argument arrays must have different elements");
            }
        }

                /*
        Optional<Constructor> constructor = getConstructor(klass, arg1Types);
        Optional<Constructor> constructorForArgs2 = getConstructor(klass, arg2Types);
        if (!constructor.equals(constructorForArgs2)) {
            throw new IllegalArgumentException(
                "Argument arrays must have compatible types (use the same constructor");
        }
         */
    }

    private static Class[] mapToClasses(Object[] objects) {
        return Stream.of(objects)
            .map(Object::getClass)
            .toArray(Class[]::new);
    }

    public static void assertEquals(Object one, Object other) {
        if (!one.equals(other)) {
            throw new AssertionError("Expecting objects to be equal");
        } else if (!other.equals(one)) {
            throw new AssertionError("Equals is supposed to be symmetric");
        } else if (one.hashCode() != other.hashCode()) {
            throw new AssertionError("Equal objects must have the same hash code");
        }
    }

    public static void assertDifferent(Object one, Object other) {
        if (one.equals(other)) {
            throw new AssertionError("Expecting objects NOT to be equal");
        }
    }

    public static void assertDifferentFromNull(Object one) {
        if (one.equals(null)) {
            throw new AssertionError("Non-null objets should not be equal to null");
        }
    }

    public static void assertEqualsToSelf(Object one) {
        if (!one.equals(one)) {
            throw new AssertionError("Object should be equal to itself");
        }
    }
}
