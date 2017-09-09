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

import org.junit.AssumptionViolatedException;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runners.model.MultipleFailureException;

import java.util.Arrays;
import java.util.Collections;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import static fr.inria.atlanmod.commons.Preconditions.checkState;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * A {@link org.junit.Rule} that logs each test-case call.
 */
public class Watcher extends TestWatcher {

    /**
     * The special logger without timestamp.
     */
    private static final Logger LOG_TEST = Log.forName("test");

    /**
     * The special logger without timestamp and level.
     */
    private static final Logger LOG_VOID = Log.forName("void");

    /**
     * The message to display a running test.
     */
    private static final String MESSAGE_RUNNING = "--- Running";

    /**
     * The message to display a successful test.
     */
    private static final String MESSAGE_SUCCESS = "--- Succeeded";

    /**
     * The message to display a failed test.
     */
    private static final String MESSAGE_FAILURE = "--- Failed";

    /**
     * The message to display a failed test with errors.
     */
    private static final String MESSAGE_FAILURE_ERRORS = MESSAGE_FAILURE + " with error(s)";

    /**
     * The message to display a skipped test.
     */
    private static final String MESSAGE_SKIP = "--- Skipped";

    /**
     * The used memory before starting the test memthod, in bytes.
     */
    private MemoryUsage memoryAtStart;

    @Override
    protected void succeeded(Description description) {
        LOG_TEST.info(MESSAGE_SUCCESS + usedMemory());
    }

    @Override
    protected void failed(Throwable e, Description description) {
        if (isNull(e)) {
            LOG_TEST.warn(MESSAGE_FAILURE + usedMemory());
        }
        else if (AssertionError.class.isInstance(e) && nonNull(e.getMessage())) {
            Arrays.stream(e.getMessage().split("\n"))
                    .filter(s -> nonNull(s) && !s.isEmpty())
                    .forEach(LOG_TEST::warn);

            LOG_TEST.warn(Strings.EMPTY);
            LOG_TEST.warn(MESSAGE_FAILURE + usedMemory());
        }
        else {
            LOG_TEST.error(MESSAGE_FAILURE_ERRORS + usedMemory() + ':');

            (MultipleFailureException.class.isInstance(e)
                    ? MultipleFailureException.class.cast(e).getFailures()
                    : Collections.singletonList(e))
                    .forEach(LOG_VOID::error);

            LOG_VOID.error(Strings.EMPTY);
        }
    }

    @Override
    protected void skipped(AssumptionViolatedException e, Description description) {
        if (nonNull(e) && nonNull(e.getMessage())) {
            LOG_TEST.warn(MESSAGE_SKIP + ": {0}", e.getMessage());
        }
        else {
            LOG_TEST.warn(MESSAGE_SKIP);
        }
    }

    @Override
    protected void starting(Description description) {
        LOG_VOID.info(Strings.EMPTY);
        LOG_TEST.info(MESSAGE_RUNNING + ": {0}", description.getMethodName());

        memoryAtStart = new MemoryUsage();
    }

    @Override
    protected void finished(Description description) {
        LOG_VOID.info(Strings.EMPTY);

        // Runs the GC after each test
        Runtime.getRuntime().gc();
    }

    /**
     * Formats the memory usage of this test.
     *
     * @return a formatted string of the memory used during this test
     */
    @Nonnull
    private String usedMemory() {
        checkState(nonNull(memoryAtStart), "memoryAtStart has not bben initialized");

        return memoryAtStart.formatDiff(new MemoryUsage());
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

        @Nonnull
        public String formatDiff(MemoryUsage after) {
            return String.format(" [%d/%d MB] ",
                    convertUnit(after.used() - used()),
                    convertUnit(after.total()));
        }
    }
}
