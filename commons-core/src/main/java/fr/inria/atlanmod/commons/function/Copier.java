package fr.inria.atlanmod.commons.function;

import java.util.Objects;
import java.util.function.BiConsumer;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * A specialized {@link BiConsumer} able to copy an object to another of the same type.
 *
 * @param <T> the type of the copied object
 *
 * @see BiConsumer
 */
@ParametersAreNonnullByDefault
@FunctionalInterface
public interface Copier<T> extends BiConsumer<T, T> {

    @Override
    default void accept(T source, T target) {
        if (Objects.equals(source, target)) {
            return;
        }

        copy(source, target);
    }

    /**
     * Copies all the contents from the {@code source} to the {@code target}.
     *
     * @param source the object to copy
     * @param target the object where to store the copied content
     */
    void copy(T source, T target);
}
