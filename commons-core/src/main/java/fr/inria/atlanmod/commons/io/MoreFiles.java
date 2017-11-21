/*
 * Copyright (c) 2017 Atlanmod, Inria, LS2N, and IMT Nantes.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package fr.inria.atlanmod.commons.io;

import fr.inria.atlanmod.commons.annotation.Static;
import fr.inria.atlanmod.commons.primitive.Strings;

import java.io.File;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import static fr.inria.atlanmod.commons.Preconditions.checkNotNull;

/**
 * Static utility methods related to {@link java.io.File}s.
 */
@Static
@ParametersAreNonnullByDefault
public final class MoreFiles {

    /**
     * The dot character.
     */
    private static final char DOT = '.';

    /**
     * This class should not be instantiated.
     *
     * @throws IllegalStateException every time
     */
    private MoreFiles() {
        throw new IllegalStateException("This class should not be instantiated");
    }

    /**
     * Returns the file extension for the given {@code file} name, or the empty string if the file has no extension. The
     * result does not include the '{@code .}'.
     *
     * @param file the file name
     *
     * @return the filename extension
     */
    @Nonnull
    public static String fileExtension(String file) {
        checkNotNull(file, "file");

        String fileName = new File(file).getName();
        int dotIndex = fileName.lastIndexOf(DOT);
        return (dotIndex == -1) ? Strings.EMPTY : fileName.substring(dotIndex + 1);
    }

    /**
     * Returns the {@code file} name without its file extension or path. This is similar to the {@code basename} unix
     * command. The result does not include the '{@code .}'.
     *
     * @param file the file name
     *
     * @return the filename without its extension
     */
    @Nonnull
    public static String nameWithoutExtension(String file) {
        checkNotNull(file, "file");

        String fileName = new File(file).getName();
        int dotIndex = fileName.lastIndexOf(DOT);
        return (dotIndex == -1) ? fileName : fileName.substring(0, dotIndex);
    }
}
