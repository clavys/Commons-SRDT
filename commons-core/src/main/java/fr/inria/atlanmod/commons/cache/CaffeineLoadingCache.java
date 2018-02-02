/*
 * Copyright (c) 2017 Atlanmod, Inria, LS2N, and IMT Nantes.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package fr.inria.atlanmod.commons.cache;

import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import static fr.inria.atlanmod.commons.Preconditions.checkNotNull;

/**
 * A Caffeine {@link Cache} implementation which either returns an already-loaded value for a given key or atomically
 * computes or retrieves it.
 *
 * @param <K> the type of keys maintained by this cache
 * @param <V> the type of mapped values
 */
@ParametersAreNonnullByDefault
class CaffeineLoadingCache<C extends com.github.benmanes.caffeine.cache.LoadingCache<K, V>, K, V> extends CaffeineManualCache<C, K, V> {

    /**
     * Constructs a new {@code CaffeineLoadingCache}.
     *
     * @param cache      the internal cache implementation
     * @param asyncRead  {@code true} if read operations should be performed asynchronously
     * @param asyncWrite {@code true} if write operations should be performed asynchronously
     */
    protected CaffeineLoadingCache(C cache, boolean asyncRead, boolean asyncWrite) {
        super(cache, asyncRead, asyncWrite);
    }

    @Nullable
    @Override
    public V get(K key) {
        checkNotNull(key, "key");

        return performRead(() -> cache.get(key));
    }

    @Nonnull
    @Override
    public Map<K, V> getAll(Iterable<? extends K> keys) {
        checkNotNull(keys, "keys");

        return performRead(() -> cache.getAll(keys));
    }

    @Override
    public void refresh(K key) {
        checkNotNull(key, "key");

        performWrite(() -> cache.refresh(key));
    }
}
