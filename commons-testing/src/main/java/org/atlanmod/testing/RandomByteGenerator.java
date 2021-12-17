package org.atlanmod.testing;
import java.io.UnsupportedEncodingException;
import java.util.Random;

public class RandomByteGenerator implements Generator<Byte> {

    @Override
    /**
     *Generate a byte.
     */
    public Byte generate() {
        Random rd = new Random();
        byte[] arr = new byte[7];
        rd.nextBytes(arr);
        return arr[2];
    }

    @Override
    /**
     * return an array of class which contains the byte class.
     */
    public Class<Byte>[] types() {
        Class[] types={Byte.class,byte.class};
        return types;
    }
}
