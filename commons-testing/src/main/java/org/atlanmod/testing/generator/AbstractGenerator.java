package org.atlanmod.testing.generator;

import org.atlanmod.testing.Generator;

public abstract class AbstractGenerator<T> implements Generator<T> {
    public static final int SIZE = 5;
    private T[] values;
    private int index;

    protected AbstractGenerator() {
        this.initializeValues();
    }

    public T generate() {
        return values[index++ % values.length];
    }

    protected abstract void initializeValues();

    protected void setValues(T[] newValues) {
        this.values = newValues;
    }
}
