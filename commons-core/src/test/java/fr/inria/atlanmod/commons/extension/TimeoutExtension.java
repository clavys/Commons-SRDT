/*
 * Copyright (c) 2017 Atlanmod, Inria, LS2N, and IMT Nantes.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package fr.inria.atlanmod.commons.extension;

import fr.inria.atlanmod.commons.Timeout;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.OptionalInt;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import static fr.inria.atlanmod.commons.Preconditions.checkArgument;
import static java.util.Objects.nonNull;

/**
 * A JUnit {@link org.junit.jupiter.api.extension.Extension} that stops a test-case when its execution time exceeds a
 * defined timeout.
 *
 * @see Timeout
 */
@ParametersAreNonnullByDefault
public final class TimeoutExtension implements BeforeEachCallback, AfterEachCallback {

    /**
     * The current timer.
     */
    @Nullable
    private Timer timer;

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        getTimeout(context).ifPresent(timeout -> {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    // TODO JUnit5 does not support yet the call interception
                    throw new AssertionError(String.format("Timeout exceeded: %d", timeout));
                }
            }, timeout);
        });
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        if (nonNull(timer)) {
            timer.cancel();
            timer = null;
        }
    }

    @Nonnull
    @Nonnegative
    private OptionalInt getTimeout(ExtensionContext context) {
        Timeout annotation = null;

        if (context.getRequiredTestMethod().isAnnotationPresent(Timeout.class)) {
            annotation = context.getRequiredTestMethod().getAnnotation(Timeout.class);
        }
        else if (context.getRequiredTestClass().isAnnotationPresent(Timeout.class)) {
            annotation = context.getRequiredTestClass().getAnnotation(Timeout.class);
        }

        if (nonNull(annotation)) {
            int timeout = annotation.timeout();
            checkArgument(timeout >= 0, "timeout (%d) must not be negative", timeout);
            return OptionalInt.of(timeout);
        }

        return OptionalInt.empty();
    }
}
