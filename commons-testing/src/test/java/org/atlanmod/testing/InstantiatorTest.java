package org.atlanmod.testing;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class InstantiatorTest {

    @Test
    void testInstanciate() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Instantiator instantiator = Instantiator.getInstance();

        String str = instantiator.instanciate(String.class);
        System.out.println(str.length());
    }
}
