/*
 * Copyright (c) 2017 Atlanmod, Inria, LS2N, and IMT Nantes.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package fr.inria.atlanmod.commons.log;

import fr.inria.atlanmod.commons.concurrent.MoreExecutors;

import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;

/**
 * A {@link Logger} that asynchronously invokes logging operations, respecting the order of invocation.
 */
@ThreadSafe
@Immutable
@ParametersAreNonnullByDefault
class AsyncLogger implements Logger {

    /**
     * The logging concurrent pool.
     * <p>
     * A single thread pool is used for keeping events order.
     */
    @Nonnull
    private static final ExecutorService POOL = MoreExecutors.newFixedThreadPool(1);

    static {
        // Configure the SimpleLogger, if no implementation is defined
        System.setProperty("org.slf4j.simpleLogger.showThreadName", "false");
        System.setProperty("org.slf4j.simpleLogger.showLogName", "false");
        System.setProperty("org.slf4j.simpleLogger.levelInBrackets", "true");
    }

    /**
     * The internal logger.
     */
    @Nonnull
    private final org.slf4j.Logger logger;

    /**
     * Constructs a new {@code AsyncLogger} with the given {@code name}.
     *
     * @param name the name of this logger
     */
    public AsyncLogger(String name) {
        this.logger = LoggerFactory.getLogger(name);
    }

    @Override
    public void log(Level level, @Nullable Throwable e, @Nullable CharSequence message, @Nullable Object... params) {
        if (!level.isEnablePredicate().test(logger)) {
            // Don't send the request if the associated level is not enabled
            return;
        }

        execute(() -> {
            try {
                String formattedMessage = Optional.ofNullable(message)
                        .map(m -> MessageFormat.format(m.toString(), params))
                        .orElse(null);

                level.logFunction().accept(logger, formattedMessage, e);
            }
            catch (Exception ignored) {
            }
        });
    }

    /**
     * Executes a {@link Runnable} in a concurrent pool to run asynchronously the logging methods. If the pool rejects
     * the task, then it is executed synchronously.
     *
     * @param runnable the function to execute
     *
     * @see ExecutorService#submit(Runnable)
     */
    private void execute(Runnable runnable) {
        try {
            // Asynchronous call
            POOL.submit(runnable);
        }
        catch (RejectedExecutionException e) {
            // Synchronous call
            runnable.run();
        }
    }
}
