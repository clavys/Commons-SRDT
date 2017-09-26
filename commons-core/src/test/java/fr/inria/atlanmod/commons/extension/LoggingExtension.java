/*
 * Copyright (c) 2013-2017 Atlanmod INRIA LINA Mines Nantes.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Atlanmod INRIA LINA Mines Nantes - initial API and implementation
 */

package fr.inria.atlanmod.commons.extension;

import fr.inria.atlanmod.commons.log.Log;
import fr.inria.atlanmod.commons.log.Logger;
import fr.inria.atlanmod.commons.primitive.Strings;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import org.opentest4j.TestAbortedException;
import org.opentest4j.TestSkippedException;

import java.util.Arrays;
import java.util.Optional;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import static fr.inria.atlanmod.commons.Preconditions.checkState;
import static java.util.Objects.nonNull;

/**
 * A JUnit {@link org.junit.jupiter.api.extension.Extension} that logs each test-case call.
 */
@ParametersAreNonnullByDefault
public final class LoggingExtension implements BeforeEachCallback, AfterEachCallback, TestExecutionExceptionHandler {

    /**
     * {@code true} if the memory usage must be logged during testing.
     */
    private static final boolean MEMORY_USAGE = true;

    /**
     * The special logger without timestamp.
     */
    @Nonnull
    private static final Logger LOG = Log.forName("test");

    /**
     * {@code true} if the current test case had an error (failed, skipped, aborted,...)
     */
    private boolean hasErrors;

    /**
     * The used memory before starting the test memthod, in bytes.
     */
    private MemoryUsage memoryAtStart;

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        hasErrors = false;

        LOG.info(Strings.EMPTY);
        onRunning(context);

        if (MEMORY_USAGE) {
            memoryAtStart = new MemoryUsage();
        }
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        context.getExecutionException().ifPresent(throwable -> onAnyException(context, throwable));

        if (!hasErrors) {
            onSuccess(context);
        }
        LOG.info(Strings.EMPTY);

        // Runs the GC after each test
        Runtime.getRuntime().gc();
    }

    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable e) throws Throwable {
        onAnyException(context, e);
    }

    /**
     * Handles the start of a test-case.
     *
     * @param context the current extension context
     */
    private void onRunning(ExtensionContext context) {
        String methodName = context.getRequiredTestMethod().getName();

        String displayName = Optional.ofNullable(context.getDisplayName())
                .filter(n -> !n.startsWith(methodName))
                .map(n -> Strings.SPACE + n)
                .orElse(Strings.EMPTY);

        LOG.info("--- Running: {0}{1}", methodName, displayName);
    }

    /**
     * Handles the success of a test-case.
     *
     * @param context the current extension context
     */
    private void onSuccess(@SuppressWarnings("unused") ExtensionContext context) {
        LOG.info("--- Succeeded{0}", memoryUsage());
    }

    /**
     * Handles any {@link Throwable} that occurs during a test-case.
     *
     * @param context the current extension context
     * @param e       the exception to handle
     *
     * @see #onFailure(ExtensionContext, AssertionError)
     * @see #onSkip(ExtensionContext, TestSkippedException)
     * @see #onAbort(ExtensionContext, TestAbortedException)
     * @see #onError(ExtensionContext, Throwable)
     */
    private void onAnyException(ExtensionContext context, Throwable e) {
        hasErrors = true;

        if (AssertionError.class.isInstance(e)) {
            onFailure(context, AssertionError.class.cast(e));
        }
        else if (TestSkippedException.class.isInstance(e)) {
            onSkip(context, TestSkippedException.class.cast(e));
        }
        else if (TestAbortedException.class.isInstance(e)) {
            onAbort(context, TestAbortedException.class.cast(e));
        }
        else {
            onError(context, e);
        }
    }

    /**
     * Handles a {@link Throwable}, when an unexpected error occured during a test.
     *
     * @param context the current extension context
     * @param e       the exception to handle
     */
    private void onError(@SuppressWarnings("unused") ExtensionContext context, Throwable e) {
        LOG.error(e, "--- Failed with error(s)");
    }

    /**
     * Handles an {@link AssertionError}, when a test-case failed.
     *
     * @param context the current extension context
     * @param e       the exception to handle
     */
    private void onFailure(@SuppressWarnings("unused") ExtensionContext context, AssertionError e) {
        if (nonNull(e.getMessage())) {
            Arrays.stream(e.getMessage().split("\n"))
                    .filter(s -> nonNull(s) && !s.isEmpty())
                    .forEach(LOG::warn);
        }

        LOG.warn("--- Failed{0}", memoryUsage());
    }

    /**
     * Handles a {@link TestSkippedException}, when a test-case is skipped with {@link org.junit.jupiter.api.Disabled}.
     *
     * @param context the current extension context
     * @param e       the exception to handle
     */
    private void onSkip(@SuppressWarnings("unused") ExtensionContext context, TestSkippedException e) {
        if (nonNull(e.getMessage())) {
            LOG.warn("--- Skipped: {0}", e.getMessage());
        }
        else {
            LOG.warn("--- Skipped");
        }
    }

    /**
     * Handles a {@link TestAbortedException}, when a test-case is aborted with {@link
     * org.junit.jupiter.api.Assumptions}.
     *
     * @param context the current extension context
     * @param e       the exception to handle
     */
    private void onAbort(@SuppressWarnings("unused") ExtensionContext context, TestAbortedException e) {
        if (nonNull(e.getMessage())) {
            LOG.warn("--- Aborted: {0}", e.getMessage().replaceFirst("Assumption failed: ", ""));
        }
        else {
            LOG.warn("--- Aborted");
        }
    }

    /**
     * Formats the memory usage of this test.
     *
     * @return a formatted string of the memory used during this test
     */
    @Nonnull
    private String memoryUsage() {
        if (MEMORY_USAGE) {
            checkState(nonNull(memoryAtStart), "memoryAtStart has not been initialized");
            return " [" + MemoryUsage.Diff.between(memoryAtStart, new MemoryUsage()).toString() + ']';
        }

        return Strings.EMPTY;
    }

    @ParametersAreNonnullByDefault
    private static final class MemoryUsage {

        /**
         * The total amount of memory in the Java virtual machine, in bytes.
         */
        @Nonnegative
        private final long total;

        /**
         * The amount of free memory in the Java Virtual Machine, in bytes.
         */
        @Nonnegative
        private final long free;

        /**
         * Constructs a new {@code MemoryUsage}.
         */
        public MemoryUsage() {
            Runtime runtime = Runtime.getRuntime();

            this.total = runtime.totalMemory();
            this.free = runtime.freeMemory();
        }

        /**
         * Converts the {@code bytes} value to another unit (kilobytes, megabytes,...).
         */
        private static long convertUnit(long bytes) {
            return bytes / 1024 / 1024;
        }

        /**
         * Returns the total amount of memory in the Java virtual machine, in bytes.
         */
        @Nonnegative
        public long total() {
            return total;
        }

        /**
         * Returns the amount of free memory in the Java Virtual Machine, in bytes.
         */
        @Nonnegative
        public long free() {
            return free;
        }

        /**
         * Returns the amount of used memory in the Java Virtual Machine, in bytes.
         */
        @Nonnegative
        public long used() {
            return total() - free();
        }

        @ParametersAreNonnullByDefault
        public static final class Diff {

            @Nonnull
            private final MemoryUsage start;

            @Nonnull
            private final MemoryUsage end;

            /**
             * Creates a new {@code Diff}.
             */
            private Diff(MemoryUsage start, MemoryUsage end) {
                this.start = start;
                this.end = end;
            }

            /**
             * Creates a {@code Diff} reprensenting the difference between two memory usages.
             */
            public static Diff between(MemoryUsage start, MemoryUsage end) {
                return new Diff(start, end);
            }

            public long total() {
                return end.total() - start.total();
            }

            public long free() {
                return end.free() - start.free();
            }

            public long used() {
                return end.used() - start.used();
            }

            @Override
            public String toString() {
                final long usedDiff = convertUnit(used());
                final long totalDiff = convertUnit(total());

                return String.format("%s / %s%s MB",
                        usedDiff < 0 ? "<0" : usedDiff,
                        convertUnit(end.total()),
                        totalDiff == 0 ? Strings.EMPTY : ("(" + (totalDiff > 0 ? "+" + totalDiff : totalDiff) + ")"));
            }
        }
    }
}
