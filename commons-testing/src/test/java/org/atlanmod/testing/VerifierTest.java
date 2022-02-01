/*
 * Copyright (c) 2022 Naomod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */
package org.atlanmod.testing;

import org.atlanmod.testing.generator.RoundRobinBooleanGenerator;
import org.atlanmod.testing.generator.RandomIntegerGenerator;
import org.atlanmod.testing.generator.RandomStringGenerator;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VerifierTest {

    @Test
    void registerGenerator() {
    }

    /**
     * Test creation of array generator from his simple generator(int in this case).
     */
    @Test
    void createArrayGeneratorForInt() {
        RandomIntegerGenerator Gen = new RandomIntegerGenerator();
        Class GenType = Gen.types()[0];
        Object arrayGenType = Array.newInstance(GenType, 0).getClass();
        Generator arrayGenerator = Verifier.createArrayGenerator(Gen, GenType);
        assertEquals(arrayGenerator.generate().getClass(), arrayGenType);
        assertEquals(arrayGenerator.generate().getClass(), arrayGenerator.types()[0]);
        assertEquals(arrayGenType, arrayGenerator.types()[0]);
    }

    /**
     * Test creation of array generator from his simple generator(String in this case).
     */
    @Test
    void createArrayGeneratorForString() {
        RandomStringGenerator Gen = new RandomStringGenerator();
        Class GenType= Gen.types()[0];
        Object arrayGenType = Array.newInstance(GenType, 0).getClass();
        Generator arrayGenerator = Verifier.createArrayGenerator(Gen,GenType);
        assertEquals(arrayGenerator.generate().getClass(), arrayGenType);
        assertEquals(arrayGenerator.generate().getClass(),arrayGenerator.types()[0]);
        assertEquals(arrayGenType,arrayGenerator.types()[0]);
    }

    /**
     * Test creation of array generator from his simple generator(boolean in this case).
     */
    @Test
    void createArrayGeneratorForBoolean() {
        RoundRobinBooleanGenerator Gen = new RoundRobinBooleanGenerator();
        Class GenType= Gen.types()[0];
        Object arrayGenType = Array.newInstance(GenType, 0).getClass();
        Generator arrayGenerator = Verifier.createArrayGenerator(Gen,GenType);
        assertEquals(arrayGenerator.generate().getClass(), arrayGenType);
        assertEquals(arrayGenerator.generate().getClass(),arrayGenerator.types()[0]);
        assertEquals(arrayGenType,arrayGenerator.types()[0]);
    }


    @Test
    /**
     * Test the generation of a constructor.
     */
    void generateConstructor() {
        Constructor constr = Person.class.getConstructors()[0];
        Object generatedConstructor = Verifier.generateConstructor(constr);
        Person pers = (Person) generatedConstructor;
        assertNotEquals(null,pers);
        assertEquals(Person.class,pers.getClass());
        assertNotEquals(null,pers.getName());
        assertNotEquals(null, pers.getAge());

    }

    @Test
    /**
     * Test the generation of a class.
     */
    void generateConstructorsOfClass() {
        List<Object> list = Verifier.generateConstructorsOfClass(Person.class);
        for(Object objet:list) {
            Person person = (Person) objet;
            assertNotEquals(null,person);
            assertEquals(Person.class,person.getClass());
            assertNotEquals(null,person.getName());
            assertNotEquals(null, person.getAge());
        }
    }
}
