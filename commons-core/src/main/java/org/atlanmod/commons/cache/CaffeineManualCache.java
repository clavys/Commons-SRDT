/*
 * Copyright (c) 2017 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package org.atlanmod.commons.cache;

import org.atlanmod.commons.Preconditions;

import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import static org.atlanmod.commons.Preconditions.checkNotNull;
import static java.util.Objects.nonNull;

/**
 * A Caffeine {@link Cache} implementation which does not automatically load values when keys are requested.
 *
 * @param <K> the type of keys maintained by this cache
 * @param <V> the type of mapped values
 */
@ParametersAreNonnullByDefault
class CaffeineManualCache<C extends com.github.benmanes.caffeine.cache.Cache<K, V>, K, V> implements Cache<K, V> {

    /**
     * The internal cache implementation.
     */
    @Nonnull
    protected final C cache;

    /**
     * Constructs a new {@code CaffeineManualCache}.
     *
     * @param cache the internal cache implementation
     */
    protected CaffeineManualCache(C cache) {
        this.cache = cache;
    }

    @Nullable
    @Override
    public V get(K key) {
        Preconditions.checkNotNull(key, "key");

        return cache.getIfPresent(key);
    }

    @Override
    public V get(K key, Function<? super K, ? extends V> mappingFunction) {
        Preconditions.checkNotNull(key, "key");
        Preconditions.checkNotNull(mappingFunction, "mappingFunction");

        return cache.get(key, mappingFunction);
    }

    @Nonnull
    @Override
    public Map<K, V> getAll(Iterable<? extends K> keys) {
        Preconditions.checkNotNull(keys, "keys");

        return cache.getAllPresent(keys);
    }

    @Override
    public void put(K key, V value) {
        Preconditions.checkNotNull(key, "key");
        Preconditions.checkNotNull(value, "value");

        cache.put(key, value);
    }

    @Override
    public void putIfAbsent(K key, V value) {
        get(key, k -> value);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        Preconditions.checkNotNull(map, "map");

        cache.putAll(map);
    }

    @Override
    public void invalidate(K key) {
        Preconditions.checkNotNull(key, "key");

        cache.invalidate(key);
    }

    @Override
    public void invalidateAll(Iterable<? extends K> keys) {
        Preconditions.checkNotNull(keys, "keys");

        cache.invalidateAll(keys);
    }

    @Override
    public void invalidateAll() {
        cache.invalidateAll();
    }

    @Override
    public boolean contains(K key) {
        return nonNull(get(key));
    }

    @Override
    public long size() {
        return cache.estimatedSize();
    }

    @Override
    public void refresh(K key) {
        // Do nothing
    }

    @Override
    public void cleanUp() {
        cache.cleanUp();
    }

    @Nonnull
    @Override
    public ConcurrentMap<K, V> asMap() {
        return cache.asMap();
    }

    @Nonnull
    @Override
    public CacheStats stats() {
        com.github.benmanes.caffeine.cache.stats.CacheStats stats = cache.stats();

        return new CacheStats(
                stats.hitCount(),
                stats.missCount(),
                stats.loadSuccessCount(),
                stats.loadFailureCount(),
                stats.totalLoadTime(),
                stats.evictionCount());
    }
}
