package fr.inria.atlanmod.commons;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * An object able to copy an object to another of the same type.
 *
 * @param <T> the type of the copied object
 */
@ParametersAreNonnullByDefault
public interface Copier<T> {

    /**
     * Copies all the contents from the {@code source} to the {@code target}.
     *
     * @param source the object to copy
     * @param target the object where to store the copied content
     */
    void copy(T source, T target);
}
