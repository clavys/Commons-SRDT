/*
 * Copyright (c) 2017 Atlanmod, Inria, LS2N, and IMT Nantes.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package fr.inria.atlanmod.commons.concurrent;

import fr.inria.atlanmod.commons.annotation.Static;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import static fr.inria.atlanmod.commons.Preconditions.checkNotNull;

/**
 * Static utility methods related to {@link Thread} instances.
 */
@Static
@ParametersAreNonnullByDefault
public final class MoreThreads {

    /**
     * The default thread factory, used to create {@link Thread} instances that are not attached to an {@link
     * ExecutorService}.
     *
     * @see #executeAtExit(Runnable)
     */
    @Nonnull
    private static final ThreadFactory DEFAULT_THREAD_FACTORY = newThreadFactory();

    /**
     * This class should not be instantiated.
     *
     * @throws IllegalStateException every time
     */
    private MoreThreads() {
        throw new IllegalStateException("This class should not be instantiated");
    }

    /**
     * Creates a new {@link ThreadFactory} that creates daemon threads.
     *
     * @return a new thread factory
     *
     * @see Executors#defaultThreadFactory()
     */
    @Nonnull
    public static ThreadFactory newThreadFactory() {
        return task -> {
            Thread thread = Executors.defaultThreadFactory().newThread(task);
            thread.setDaemon(true);
            return thread;
        };
    }

    /**
     * Adds a shutdown hook that will execute the {@code task} when the application will exit.
     *
     * @param task the task to execute
     *
     * @throws NullPointerException if the {@code task} is {@code null}
     */
    public static void executeAtExit(Runnable task) {
        checkNotNull(task, "task");

        Runtime.getRuntime().addShutdownHook(DEFAULT_THREAD_FACTORY.newThread(task));
    }
}
