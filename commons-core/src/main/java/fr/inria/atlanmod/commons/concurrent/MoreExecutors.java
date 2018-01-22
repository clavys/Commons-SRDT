/*
 * Copyright (c) 2017 Atlanmod, Inria, LS2N, and IMT Nantes.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package fr.inria.atlanmod.commons.concurrent;

import fr.inria.atlanmod.commons.Throwables;
import fr.inria.atlanmod.commons.annotation.Static;
import fr.inria.atlanmod.commons.log.Log;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import static fr.inria.atlanmod.commons.Preconditions.checkArgument;
import static fr.inria.atlanmod.commons.Preconditions.checkNotNull;

/**
 * Static utility methods related to {@link java.util.concurrent.Executor} instances.
 */
@Static
@ParametersAreNonnullByDefault
public final class MoreExecutors {

    private MoreExecutors() {
        throw Throwables.notInstantiableClass(getClass());
    }

    /**
     * Creates a new {@link ExecutorService} using the maximum {@link Runtime#availableProcessors() available
     * processors}, that will be closed when the application will exit.
     *
     * @return a new service
     *
     * @throws IllegalArgumentException if {@code nThreads <= 0}
     * @see #newFixedThreadPool(int)
     */
    @Nonnull
    public static ExecutorService newFixedThreadPool() {
        return newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    /**
     * Creates a new {@link ExecutorService} using the maximum {@link Runtime#availableProcessors() available
     * processors}, that will be closed when the application will exit.
     *
     * @return a new service
     *
     * @throws IllegalArgumentException if {@code nThreads <= 0}
     * @see #newFixedThreadPool(int)
     */
    @Nonnull
    public static ExecutorService newFixedThreadPool(String name) {
        return newFixedThreadPool(Runtime.getRuntime().availableProcessors(), name);
    }

    /**
     * Creates a new {@link ExecutorService} using {@code nThreads} processors, that will be closed when the application
     * will exit.
     *
     * @param nThreads the number of threads in the pool
     *
     * @return a new service
     *
     * @throws IllegalArgumentException if {@code nThreads <= 0}
     * @see #newFixedThreadPool(int, String)
     */
    @Nonnull
    public static ExecutorService newFixedThreadPool(int nThreads) {
        return newFixedThreadPool(nThreads, null);
    }

    /**
     * Creates a new {@link ExecutorService} using {@code nThreads} processors, that will be closed when the application
     * will exit.
     *
     * @param nThreads the number of threads in the pool
     * @param name     the name of the pool
     *
     * @return a new service
     *
     * @throws IllegalArgumentException if {@code nThreads <= 0}
     * @see Executors#newFixedThreadPool(int)
     * @see MoreThreads#newThreadFactory()
     * @see #shutdownAtExit(ExecutorService, long, TimeUnit, boolean)
     */
    @Nonnull
    public static ExecutorService newFixedThreadPool(int nThreads, @Nullable String name) {
        ExecutorService service = Executors.newFixedThreadPool(nThreads, MoreThreads.newThreadFactory(name));
        return shutdownAtExit(service, 100, TimeUnit.MILLISECONDS, true);
    }

    /**
     * Cleanly closes the {@code service}.
     *
     * @param service          the service to close
     * @param timeout          the maximum time to wait
     * @param unit             the time unit of the timeout argument
     * @param executeUnstarted {@code true} if the remaining tasks must be executed synchronously if they have not
     *                         completed their execution after the shutdown request
     *
     * @throws NullPointerException     if any argument is {@code null}
     * @throws IllegalArgumentException if {@code timeout < 0}
     * @see ExecutorService#shutdown()
     * @see ExecutorService#awaitTermination(long, TimeUnit)
     * @see ExecutorService#shutdownNow()
     */
    public static void shutdown(ExecutorService service, long timeout, TimeUnit unit, boolean executeUnstarted) {
        List<Runnable> unstartedTasks = shutdown(service, timeout, unit);

        if (executeUnstarted) {
            unstartedTasks.forEach(Runnable::run);
        }
        else if (!unstartedTasks.isEmpty()) {
            Log.warn("{0} pending execution(s) will not be executed", unstartedTasks.size());
        }
    }

    /**
     * Cleanly closes the {@code service}.
     *
     * @param service the service to close
     * @param timeout the maximum time to wait
     * @param unit    the time unit of the timeout argument
     *
     * @return a list of remaining tasks that never commenced execution
     *
     * @throws NullPointerException     if any argument is {@code null}
     * @throws IllegalArgumentException if {@code timeout < 0}
     * @see ExecutorService#shutdown()
     * @see ExecutorService#awaitTermination(long, TimeUnit)
     * @see ExecutorService#shutdownNow()
     */
    @Nonnull
    public static List<Runnable> shutdown(ExecutorService service, long timeout, TimeUnit unit) {
        checkNotNull(service, "service");
        checkNotNull(unit, "unit");
        checkArgument(timeout >= 0, "timeout (%d) must not be negative", timeout);

        if (!service.isShutdown() && !service.isTerminated()) {
            try {
                service.shutdown();

                if (service.awaitTermination(timeout, unit)) {
                    return service.shutdownNow();
                }
            }
            catch (InterruptedException ignored) {
            }
        }

        return Collections.emptyList();
    }

    /**
     * Adds a shutdown hook to cleanly closes the {@code service} when the application will exit.
     *
     * @param service          the service to close
     * @param timeout          the maximum time to wait
     * @param unit             the time unit of the timeout argument
     * @param executeUnstarted {@code true} if the remaining tasks must be executed synchronously if they have not
     *                         completed their execution after the shutdown request
     *
     * @return the {@code service}
     *
     * @throws NullPointerException     if any argument is {@code null}
     * @throws IllegalArgumentException if {@code timeout < 0}
     * @see ExecutorService#shutdown()
     * @see ExecutorService#awaitTermination(long, TimeUnit)
     * @see ExecutorService#shutdownNow()
     */
    @Nonnull
    public static ExecutorService shutdownAtExit(ExecutorService service, long timeout, TimeUnit unit, boolean executeUnstarted) {
        checkNotNull(service, "service");
        checkNotNull(unit, "unit");
        checkArgument(timeout >= 0, "timeout (%d) must not be negative", timeout);

        MoreThreads.executeAtExit(() -> shutdown(service, timeout, unit, executeUnstarted));

        return service;
    }
}
