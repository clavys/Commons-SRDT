package org.atlanmod.testing.generator;

import org.atlanmod.testing.Generator;

public abstract class AbstractGenerator<T> implements Generator<T> {
    public static final int SIZE = 5;
    private int index;
    protected T[] values;

    public T generate() {
        return values[index++ % values.length];
    }
}
