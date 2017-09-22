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

package fr.inria.atlanmod.commons.hash;

import java.io.Serializable;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.Immutable;

/**
 * An immutable hash code of arbitrary bit length.
 */
@Immutable
@ParametersAreNonnullByDefault
public interface HashCode extends Serializable {

    /**
     * Returns the number of bits in this hash code; a positive multiple of 8.
     *
     * @return the number of bits
     */
    @Nonnegative
    int bits();

    /**
     * Returns the value of this hash code as a byte array.
     *
     * @return a byte array
     */
    @Nonnull
    byte[] toBytes();

    /**
     * Returns the long representation of this hash code.
     *
     * @return a long
     */
    long toLong();

    /**
     * Returns the literal representation of this hash code.
     *
     * @return a string
     */
    @Nonnull
    String toHexString();
}
