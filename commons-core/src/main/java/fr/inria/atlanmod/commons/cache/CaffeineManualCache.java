/*
 * Copyright (c) 2017 Atlanmod, Inria, LS2N, and IMT Nantes.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package fr.inria.atlanmod.commons.cache;

import fr.inria.atlanmod.commons.Throwables;
import fr.inria.atlanmod.commons.concurrent.MoreExecutors;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.function.Function;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import static fr.inria.atlanmod.commons.Preconditions.checkNotNull;
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
     * The pool used to perform read operations.
     */
    @Nonnull
    private final ExecutorService readPool;

    /**
     * The pool used to perform write operations.
     */
    @Nonnull
    private final ExecutorService writePool;

    /**
     * Constructs a new {@code CaffeineManualCache}.
     *
     * @param cache      the internal cache implementation
     * @param asyncRead  {@code true} if read operations should be performed asynchronously
     * @param asyncWrite {@code true} if write operations should be performed asynchronously
     */
    protected CaffeineManualCache(C cache, boolean asyncRead, boolean asyncWrite) {
        this.cache = cache;

        this.readPool = asyncRead ? MoreExecutors.newFixedThreadPool() : MoreExecutors.newDirectPool();
        this.writePool = asyncWrite ? MoreExecutors.newFixedThreadPool() : MoreExecutors.newDirectPool();
    }

    @Nullable
    @Override
    public V get(K key) {
        checkNotNull(key, "key");

        return performRead(() -> cache.getIfPresent(key));
    }

    @Override
    public V get(K key, Function<? super K, ? extends V> mappingFunction) {
        checkNotNull(key, "key");
        checkNotNull(mappingFunction, "mappingFunction");

        return performRead(() -> cache.get(key, mappingFunction));
    }

    @Nonnull
    @Override
    public Map<K, V> getAll(Iterable<? extends K> keys) {
        checkNotNull(keys, "keys");

        return performRead(() -> cache.getAllPresent(keys));
    }

    @Override
    public void put(K key, V value) {
        checkNotNull(key, "key");
        checkNotNull(value, "value");

        performWrite(() -> cache.put(key, value));
    }

    @Override
    public void putIfAbsent(K key, V value) {
        checkNotNull(key, "key");
        checkNotNull(value, "value");

        performWrite(() -> cache.get(key, k -> value));
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        checkNotNull(map, "map");

        performWrite(() -> cache.putAll(map));
    }

    @Override
    public void invalidate(K key) {
        checkNotNull(key, "key");

        performWrite(() -> cache.invalidate(key));
    }

    @Override
    public void invalidateAll(Iterable<? extends K> keys) {
        checkNotNull(keys, "keys");

        performWrite(() -> cache.invalidateAll(keys));
    }

    @Override
    public void invalidateAll() {
        performWrite(cache::invalidateAll);
    }

    @Override
    public boolean contains(K key) {
        return nonNull(get(key));
    }

    @Override
    public long size() {
        return performRead(cache::estimatedSize);
    }

    @Override
    public void refresh(K key) {
        // Do nothing
    }

    @Override
    public void cleanUp() {
        performWrite(cache::cleanUp);
    }

    @Nonnull
    @Override
    public ConcurrentMap<K, V> asMap() {
        return performRead(cache::asMap);
    }

    @Nonnull
    @Override
    public CacheStats stats() {
        com.github.benmanes.caffeine.cache.stats.CacheStats stats = performRead(cache::stats);

        return new CacheStats(
                stats.hitCount(),
                stats.missCount(),
                stats.loadSuccessCount(),
                stats.loadFailureCount(),
                stats.totalLoadTime(),
                stats.evictionCount());
    }

    /**
     * Performs a read operation.
     *
     * @param task the read operation
     * @param <T>  the type of the read value
     *
     * @return the result value
     */
    protected <T> T performRead(Callable<T> task) {
        Future<T> future = readPool.submit(task);
        return getResult(future);
    }

    /**
     * Performs a write operation.
     *
     * @param task the write operation
     */
    protected void performWrite(Runnable task) {
        writePool.submit(task);
    }

    /**
     * Waits and returns the result of the specified {@code future}.
     *
     * @param future the future
     * @param <T>    the type of the read value
     *
     * @return the result value
     */
    private <T> T getResult(Future<T> future) {
        try {
            return future.get();
        }
        catch (InterruptedException e) {
            throw Throwables.wrap(e, IllegalStateException.class);
        }
        catch (Exception e) {
            throw Throwables.wrap(e, RuntimeException.class);
        }
    }
}
