package org.atlanmod.testing;

import java.util.Random;

public class RandomBooleanGenerator implements Generator<Boolean> {

    @Override
    /**
     * Generate a boolean.
     */
    public Boolean generate() {
        Random r = new Random();
        boolean bool = r.nextBoolean();
        return bool;
    }

    @Override
    /**
     * return an array of class which contains the boolean class.
     */
    public Class<Boolean>[] types() {
        Class[] types={Boolean.class};
        return types;
    }
}
