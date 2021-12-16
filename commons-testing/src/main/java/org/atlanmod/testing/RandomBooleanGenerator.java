package org.atlanmod.testing;

import java.util.Random;

public class RandomBooleanGenerator implements Generator<Boolean> {
    @Override
    public Boolean generate() {
        Random r = new Random();
        boolean bool = r.nextBoolean();
        return bool;
    }

    @Override
    public Class<Boolean>[] types() {
        Class[] types={Boolean.class};
        return types;
    }
}
