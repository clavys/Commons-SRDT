package org.atlanmod.testing;
import java.util.Random;

public class RandomIntegerGenerator implements Generator<Integer>{

    @Override
    /**
     *Generate an integer.
     */
    public Integer generate() {
        int min= 0;
        int max= 20;
        Random r = new Random();
        int value = r.nextInt((max - min) + 1);
        boolean bool = r.nextBoolean();
       // if(!bool) value=-1*value;
        return value;
    }

    @Override
    /**
     *return an array of class which contains the integer and int class.
     */
    public Class<Integer>[] types() {
        Class[] types={Integer.class,int.class};
        return types;
    }
}
