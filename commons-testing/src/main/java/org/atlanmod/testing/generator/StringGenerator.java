package org.atlanmod.testing.generator;

public abstract class StringGenerator extends AbstractGenerator<String> {

    @Override
    public Class<String>[] types() {
        return new Class[]{String.class};
    }
}
