package fr.inria.atlanmod.commons.collect;

import fr.inria.atlanmod.commons.annotation.Static;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Static utility methods related to {@link Stream} instances.
 */
@Static
@ParametersAreNonnullByDefault
public final class MoreStreams {

    /**
     * This class should not be instantiated.
     *
     * @throws IllegalStateException every time
     */
    private MoreStreams() {
        throw new IllegalStateException("This class should not be instantiated");
    }

    /**
     * Finds the index of the given {@code value} in the {@code stream}.
     *
     * @param stream the stream to search through for the object
     * @param value  the value to find
     *
     * @return an {@link Optional} containing the index of the value within the stream, {@link Optional#empty()} if not found
     */
    @Nonnull
    public static Optional<Integer> indexOf(Stream<?> stream, Object value) {
        AtomicInteger currentIndex = new AtomicInteger(-1);

        boolean found = stream.peek(e -> currentIndex.incrementAndGet())
                .anyMatch(e -> Objects.equals(e, value));

        return found ?
                Optional.of(currentIndex.get()) :
                Optional.empty();
    }

    /**
     * Finds the last index of the given {@code value} in the {@code stream}.
     *
     * @param stream the stream to traverse for looking for the object
     * @param value  the value to find, may be {@code null}
     *
     * @return an {@link Optional} containing the index of the value within the stream, {@link Optional#empty()} if not found
     */
    @Nonnull
    public static Optional<Integer> lastIndexOf(Stream<?> stream, Object value) {
        AtomicInteger currentIndex = new AtomicInteger(-1);
        AtomicInteger lastIndex = new AtomicInteger(-1);

        stream.peek(e -> currentIndex.incrementAndGet())
                .filter(e -> Objects.equals(e, value))
                .forEach(e -> lastIndex.set(currentIndex.intValue()));

        return lastIndex.get() >= 0
                ? Optional.of(lastIndex.get())
                : Optional.empty();
    }

    /**
     * Returns the number of element in the {@code stream} as an integer;
     *
     * @param stream the stream
     *
     * @return an {@link Optional} containing the size, {@link Optional#empty()} if the stream is empty
     */
    @Nonnull
    public static Optional<Integer> size(Stream<?> stream) {
        return Optional.of(stream.count())
                .map(Long::intValue)
                .filter(s -> s > 0);
    }
}
