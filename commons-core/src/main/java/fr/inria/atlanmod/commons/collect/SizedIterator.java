/*
 * Copyright (c) 2017 Atlanmod, Inria, LS2N, and IMT Nantes.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package fr.inria.atlanmod.commons.collect;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.IntFunction;

import javax.annotation.Nonnegative;
import javax.annotation.ParametersAreNonnullByDefault;

import static fr.inria.atlanmod.commons.Preconditions.checkArgument;
import static fr.inria.atlanmod.commons.Preconditions.checkNotNull;

/**
 * A size-based {@link Iterator} that loads each value with a {@link IntFunction}.
 *
 * @param <E> the type of elements returned by this iterator
 */
@ParametersAreNonnullByDefault
public class SizedIterator<E> implements Iterator<E> {

    /**
     * The function used to retrieve the value at a specified index.
     */
    @Nonnegative
    private final IntFunction<E> mappingFunction;

    /**
     * The size of the iterator.
     */
    @Nonnegative
    private final int size;

    /**
     * The current position of this iterator.
     */
    @Nonnegative
    private int index;

    /**
     * Constructs a new {@code SizedIterator}.
     *
     * @param size            the size of the iterator
     * @param mappingFunction the function used to retrieve the value at a specified index
     */
    public SizedIterator(@Nonnegative int size, IntFunction<E> mappingFunction) {
        checkNotNull(mappingFunction);
        checkArgument(size >= 0, "size (%d) must not be negative");

        this.mappingFunction = mappingFunction;
        this.size = size;
    }

    @Override
    public boolean hasNext() {
        return index < size;
    }

    @Override
    public E next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        return mappingFunction.apply(index++);
    }
}
