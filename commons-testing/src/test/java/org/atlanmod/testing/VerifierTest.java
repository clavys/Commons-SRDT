package org.atlanmod.testing;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;

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
        //For RandomIntegerGenerator
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
        //For RandomStringGenerator
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
        RandomBooleanGenerator Gen = new RandomBooleanGenerator();
        Class GenType= Gen.types()[0];
        Object arrayGenType = Array.newInstance(GenType, 0).getClass();
        Generator arrayGenerator = Verifier.createArrayGenerator(Gen,GenType);

        assertEquals(arrayGenerator.generate().getClass(), arrayGenType);
        assertEquals(arrayGenerator.generate().getClass(),arrayGenerator.types()[0]);
        assertEquals(arrayGenType,arrayGenerator.types()[0]);
    }


    @Test
    void generateConstructor() {
        /*for (Constructor<?> each : Person.class.getConstructors()) {
            System.out.println("La signature du constructeur : " + each);
           // System.out.println("Les generateurs associés sont :" +"\n" +Verifier.getGeneratorsForConstructor(each));

          //  List<Generator> generatorList = Verifier.getGeneratorsForConstructor(each).get();
            System.out.println("La nouvelle instance de l'objet :" +"\n"
                    +Verifier.getObjectFrom(each)+"\n");*/
        Constructor constr = Person.class.getConstructors()[0];
        System.out.println("Le constructeur : "+constr);
        System.out.println("La nouvellle instance du constructeur"+"\n"+
            Verifier.generateConstructor(constr));

    }

    @Test
    void generateConstructorsOfClass() {
        /*for (Constructor<?> each : Person.class.getConstructors()) {
            System.out.println("La signature du constructeur : " + each);
            System.out.println("Les generateurs associés sont :" +"\n"
                    +Verifier.getGeneratorsForConstructor(each));

            List<Generator> generatorList = Verifier.getGeneratorsForConstructor(each).get();
            System.out.println("La nouvelle instance de l'objet :" +"\n"
                    +Verifier.getObjectFrom(each,generatorList)+"\n");
        }*/
        Verifier.generateConstructorsOfClass(Person.class);
    }
}