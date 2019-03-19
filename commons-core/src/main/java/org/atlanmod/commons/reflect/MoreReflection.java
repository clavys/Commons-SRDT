/*
 * Copyright (c) 2017 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package org.atlanmod.commons.reflect;

import org.atlanmod.commons.Throwables;
import org.atlanmod.commons.annotation.Builder;
import org.atlanmod.commons.annotation.Singleton;
import org.atlanmod.commons.annotation.Static;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import static org.atlanmod.commons.Preconditions.checkArgument;
import static org.atlanmod.commons.Preconditions.checkNotNull;

/**
 * Static utility methods related to reflection.
 */
@ParametersAreNonnullByDefault
public final class MoreReflection {

    private MoreReflection() {
        throw Throwables.notInstantiableClass(getClass());
    }

    /**
     * Gets or creates a instance of the given {@code type} by using the static method named {@code name}.
     * <p>
     * If the {@code type} is annotated by {@link Singleton} or by {@link Builder}, then the static method identified by
     * the value of the annotation is used to get the instance. Otherwise, the default constructor is used.
     *
     * @param type the class to look for
     * @param <T>  the type of the instance
     *
     * @return the single instance if the {@code type} is a singleton, or a new instance
     *
     * @throws ReflectionException if an error occurs during the instantiation
     */
    @Nonnull
    @SuppressWarnings("unchecked")
    public static <T> T newInstance(Class<T> type) {
        checkNotNull(type, "type");

        T instance;
        Optional<String> methodName = findConstructionMethod(type);

        try {
            if (methodName.isPresent()) {
                Method method = type.getMethod(methodName.get());
                method.setAccessible(true);
                instance = (T) method.invoke(null);
            }
            else {
                instance = type.getConstructor().newInstance();
            }
        }
        catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new ReflectionException(e);
        }

        return instance;
    }

    /**
     * Returns the name of the method to use for creating a new instance of {@code type}, according to its annotations.
     *
     * @param type the class to look for
     *
     * @return an {@link Optional} containing the name of the construction method, or {@link Optional#empty()} if the
     * {@code type} is not annotated
     */
    @Nonnull
    private static Optional<String> findConstructionMethod(Class<?> type) {
        checkArgument(!type.isAnnotationPresent(Static.class), "%s is annotated with @%s: cannot be instantiated", type.getName(), Static.class.getSimpleName());

        Optional<String> methodName;

        if (type.isAnnotationPresent(Singleton.class)) {
            methodName = Optional.of(type.getAnnotation(Singleton.class).value());
        }
        else if (type.isAnnotationPresent(Builder.class)) {
            methodName = Optional.of(type.getAnnotation(Builder.class).value());
        }
        else {
            // Use the default constructor
            methodName = Optional.empty();
        }

        return methodName;
    }

    /**
     * Retrieves all interfaces implemented by the given class and its superclasses.
     * <p>
     * The order is determined by looking through each interface in turn as declared in the source file and following
     * its hierarchy up. Then each superclass is considered in the same way. Later duplicates are ignored, so the order
     * is maintained.
     *
     * @param type the class to look up
     *
     * @return a {@link Set} of interfaces in order
     */
    @Nonnull
    public static Set<Class<?>> getAllInterfaces(Class<?> type) {
        Set<Class<?>> interfaces = new LinkedHashSet<>();
        appendAllInterfaces(type, interfaces);
        return interfaces;
    }

    /**
     * Retrieves recursively the interfaces for the specified class.
     *
     * @param type       the class to look up
     * @param interfaces the set where to append interfaces of the class
     */
    private static void appendAllInterfaces(Class<?> type, Set<Class<?>> interfaces) {
        while (type != null) {
            Arrays.stream(type.getInterfaces()).filter(interfaces::add).forEachOrdered(i -> appendAllInterfaces(i, interfaces));
            type = type.getSuperclass();
        }
    }
}
