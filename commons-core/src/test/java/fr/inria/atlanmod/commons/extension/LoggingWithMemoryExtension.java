package fr.inria.atlanmod.commons.extension;

import fr.inria.atlanmod.commons.primitive.Strings;

import org.junit.jupiter.api.extension.ExtensionContext;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import static java.util.Objects.nonNull;

/**
 * A JUnit {@link org.junit.jupiter.api.extension.Extension} that logs each test-case calls, with memory usage
 * analysis.
 */
@ParametersAreNonnullByDefault
public class LoggingWithMemoryExtension extends LoggingExtension {

    /**
     * The namespace of this extension.
     */
    @Nonnull
    private static final ExtensionContext.Namespace NS = ExtensionContext.Namespace.create("atlanmod.logging.memory");

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        super.beforeEach(context);

        context.getStore(NS).put("usage", new MemoryUsage());
    }

    @Nonnull
    @Override
    protected String additionalDetails(ExtensionContext context) {
        final MemoryUsage usage = context.getStore(NS).remove("usage", MemoryUsage.class);

        String baseDetails = super.additionalDetails(context);

        return nonNull(usage)
                ? baseDetails + String.format(" [%s]", MemoryUsage.Diff.between(usage, new MemoryUsage()))
                : baseDetails;
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

                return String.format("%s/%s%s MB",
                        usedDiff < 0 ? "<0" : usedDiff,
                        convertUnit(end.total()),
                        totalDiff == 0 ? Strings.EMPTY : ("(" + (totalDiff > 0 ? "+" + totalDiff : totalDiff) + ")"));
            }
        }
    }
}
