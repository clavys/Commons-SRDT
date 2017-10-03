package fr.inria.atlanmod.commons;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * An object that can be copied to another.
 *
 * @param <T> the type of the copied object
 */
@ParametersAreNonnullByDefault
@FunctionalInterface
public interface Copiable<T> {

    /**
     * Copies all the contents from this object to the {@code target}.
     *
     * @param target the object where to store the copied content
     */
    void copyTo(T target);
}
