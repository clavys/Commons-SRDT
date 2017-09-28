package fr.inria.atlanmod.commons.extension;

import org.junit.jupiter.api.extension.ExtensionContext;

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
    private static final ExtensionContext.Namespace NS = ExtensionContext.Namespace.create("atlanmod.logging");

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        super.beforeEach(context);

        context.getStore(NS).put("memory.usage", MemoryUsage.now());
    }

    @Nonnull
    @Override
    protected String additionalDetails(ExtensionContext context) {
        final MemoryUsage endUsage = MemoryUsage.now();
        final MemoryUsage startUsage = context.getStore(NS).remove("memory.usage", MemoryUsage.class);

        String baseDetails = super.additionalDetails(context);

        return nonNull(startUsage)
                ? baseDetails + String.format(" [%s]", MemoryUsage.Diff.between(startUsage, endUsage))
                : baseDetails;
    }
}
