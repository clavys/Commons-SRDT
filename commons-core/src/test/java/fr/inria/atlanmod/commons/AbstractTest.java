/*
 * Copyright (c) 2017 Atlanmod, Inria, LS2N, and IMT Nantes.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package fr.inria.atlanmod.commons;

import fr.inria.atlanmod.commons.extension.LoggingWithMemoryExtension;

import org.junit.jupiter.api.extension.ExtendWith;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * An abstract test-case that manages the logger.
 */
@ExtendWith(LoggingWithMemoryExtension.class)
@ParametersAreNonnullByDefault
public abstract class AbstractTest {
}
