package org.atlanmod.testing;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.atlanmod.testing.Assertions.assertThat;

class InstantiatorTest {

    @Test
    void testInstanciate() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Instantiator instantiator = Instantiator.getInstance();

        Person someone = instantiator.instanciate(Person.class);

        assertThat(someone).isNotNull();
        assertThat(someone.getName()).isNotNull().isNotEmpty();
        assertThat(someone.getAge()).isNotNull();
    }
}
