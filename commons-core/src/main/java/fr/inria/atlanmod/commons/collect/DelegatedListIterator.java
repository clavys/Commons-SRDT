/*
 * Copyright (c) 2017-2018 Atlanmod, Inria, LS2N, and IMT Nantes.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package fr.inria.atlanmod.commons.collect;

import java.util.ListIterator;
import java.util.function.Function;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import static fr.inria.atlanmod.commons.Preconditions.checkNotNull;

/**
 * A {@link ListIterator} that iterates over another.
 *
 * @param <T> the type of elements returned by the delegated iterator
 * @param <R> the type of elements returned by this iterator
 */
@ParametersAreNonnullByDefault
public class DelegatedListIterator<T, R> implements ListIterator<R> {

    /**
     * The delegated iterator.
     */
    @Nonnull
    private final ListIterator<T> delegate;

    /**
     * The function used to map elements from the delegated iterator.
     */
    @Nonnull
    private final Function<T, R> mappingFunction;

    /**
     * Constructs a new {@code DelegatedIterator}.
     *
     * @param delegate        the delegated list iterator
     * @param mappingFunction the function used to map elements from the {@code delegate}
     */
    public DelegatedListIterator(ListIterator<T> delegate, Function<T, R> mappingFunction) {
        checkNotNull(delegate, "delegate");
        checkNotNull(mappingFunction, "mappingFunction");

        this.delegate = delegate;
        this.mappingFunction = mappingFunction;
    }

    @Override
    public boolean hasNext() {
        return delegate.hasNext();
    }

    @Override
    public R next() {
        return mappingFunction.apply(delegate.next());
    }

    @Override
    public boolean hasPrevious() {
        return delegate.hasPrevious();
    }

    @Override
    public R previous() {
        return mappingFunction.apply(delegate.previous());
    }

    @Override
    public int nextIndex() {
        return delegate.nextIndex();
    }

    @Override
    public int previousIndex() {
        return delegate.previousIndex();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("remove");
    }

    @Override
    public void set(R r) {
        throw new UnsupportedOperationException("set");
    }

    @Override
    public void add(R r) {
        throw new UnsupportedOperationException("add");
    }
}
