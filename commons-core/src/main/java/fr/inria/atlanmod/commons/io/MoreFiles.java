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
     * This class should not be instantiated.
     *
     * @throws IllegalStateException every time
     */
    private MoreFiles() {
        throw new IllegalStateException("This class should not be instantiated");
    }

    /**
     * Returns the file extension for the given {@code file} name, or the empty string if the file has no extension.
     * The result does not include the '{@code .}'.
     *
     * @param file the file name
     *
     * @return the filename extension
     */
    @Nonnull
    public static String fileExtension(String file) {
        checkNotNull(file);

        String fileName = new File(file).getName();
        int dotIndex = fileName.lastIndexOf('.');
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
        checkNotNull(file);

        String fileName = new File(file).getName();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? fileName : fileName.substring(0, dotIndex);
    }
}
