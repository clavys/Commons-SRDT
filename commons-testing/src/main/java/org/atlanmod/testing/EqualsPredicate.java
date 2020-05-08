package org.atlanmod.testing;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

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

        Function<Object[], Object> instantiator = getInstantiator(klass, arg1Types);

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

    private static Optional<Constructor> getConstructor(Class type, Class[] argumentTypes) {
        return Stream.of(type.getConstructors()).filter(each -> matches(each, argumentTypes))
            .findFirst();
    }

    private static Optional<Method> getFactory(Class type, Class[] argumentTypes) {
        return Stream.of(type.getMethods())
            .filter(each -> Modifier.isStatic(each.getModifiers()))
            .filter(each -> each.getReturnType().isAssignableFrom(type))
            .filter(each -> matches(each, argumentTypes))
            .findFirst();
    }

    private static Function<Object[], Object> getInstantiator(Class type, Class[] argumentTypes) {
        Optional<Constructor> constructor = getConstructor(type, argumentTypes);
        if (constructor.isPresent()) {
            return arguments -> {
                try {
                    return constructor.get().newInstance(arguments);
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException("Could not instantiate class with constructor");
                }
            };
        } else {
            Optional<Method> factory = getFactory(type, argumentTypes);
            if (factory.isPresent()) {
                return arguments -> {
                    try {
                        return factory.get().invoke(null, arguments);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                        throw new RuntimeException(
                            "Could not instantiate class with factory method");
                    }
                };
            } else {
                throw new IllegalArgumentException(
                    "Could not find compatible constructor or factory method");
            }
        }
    }

    private static boolean matches(Executable executable, Class[] argumentTypes) {
        Class[] types = executable.getParameterTypes();
        if (types.length != argumentTypes.length) {
            return false;
        }
        for (int i = 0; i < argumentTypes.length; i++) {
            if (!types[i].isAssignableFrom(argumentTypes[i])) {
                return false;
            }
        }
        return true;
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