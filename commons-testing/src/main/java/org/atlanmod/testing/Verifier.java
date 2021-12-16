/*
 * Copyright (c) 2020 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */
package org.atlanmod.testing;

import org.atlanmod.commons.Throwables;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.lang.reflect.Array;

/**
 * Entry point for verification methods that improve unit tests.
 * Each method in this class is a static factory for a specific verification object.
 *
 * For instance:
 *
 * <pre><code class='java'>
 * {@link Verifier#verifyEqualsOf(Class) verifyEqualsOf(String.class)}
 *      .{@link EqualsVerifier#withArguments(Object...) withArguments("a String"}
 *      .{@link EqualsVerifier#andVariants(Object...) andVariants("another String"}
 *      .{@link EqualsVerifier#check() check()}
 *
 * </code></pre>
 *
 * <pre><code class='java'>
 * {@link Verifier#verifySerialization(Class) verifySerialization(String.class)}
 *      .{@link SerializationVerifier#withArguments(Object...) withArguments("a String"}
 *      .{@link SerializationVerifier#check() check()}
 *
 * </code></pre>
 */
public class Verifier {
    private static final Map <Class<?>, Generator<?> > generators= new HashMap<>();
    private static Generator stringGenerator= new RandomStringGenerator();
    private static Generator integerGenerator= new RandomIntegerGenerator();
    private static Generator charGenerator= new RandomCharGenerator();
    //private static Generator byteGenerator= new RandomByteGenerator();
    private static Generator booleanGenerator= new RandomBooleanGenerator();

    static {
        registerGenerator(integerGenerator);
        registerGenerator(stringGenerator);
        registerGenerator(charGenerator);
        //registerGenerator(byteGenerator);
        registerGenerator(booleanGenerator);
    }

    private Verifier() {
        throw Throwables.notInstantiableClass(getClass());
    }

    /**
     * Creates a {@link EqualsVerifier} for class {@code type}.
     *
     * @param type the class whose {@code equals()} method will be verified.
     * @param <T> the actual class of the class {@type}.
     * @return an instance of {@link EqualsVerifier}.
     */
    public static <T> EqualsVerifier<T> verifyEqualsOf(Class<T> type) {
        return new EqualsVerifier<>(type);
    }

    /**
     * Creates a {@link SerializationVerifier} for class {@code type}.
     *
     * @param type the class whose {@code serialize()} method will be verified.
     * @param <T> the actual class of the class {@type}.
     * @return an instance of {@link SerializationVerifier}.
     */
    public static <T extends Serializable> SerializationVerifier<T> verifySerialization(Class<T> type) {
        return new SerializationVerifier<>(type);
    }

    /**
     * Register a new generator for a specific type.
     * @param generator the generator of the target class.
     * @param <T> the target class to generate.
     */
    public static <T> void registerGenerator(Generator <T> generator) {
        for (Class<?> type :generator.types() ) {
            generators.put(type, generator);
        }
    }

    /**
     *creation of an array generator from his simple generator.
     * @param gen
     * @param arrayType
     * @return
     */
    public static Generator createArrayGenerator(Generator gen,Class arrayType)  {
        Random random =new Random();
        int length= random.nextInt(10)+1;
        Generator newGenerator= new Generator() {
            @Override
            public Object generate() {
                Object list = Array.newInstance(arrayType,length);
                for (int i=0; i<length; i++) {
                    Array.set(list,i,gen.generate());
                }
                return list;
            }
            @Override
            public Class[] types() {
                List<Class> listTypes = new ArrayList<>();
                for (Class<?> type : gen.types()) {
                    listTypes.add(Array.newInstance(type, 0).getClass());
                }
                return listTypes.toArray(new Class[0]);
            }
        };
        return newGenerator;
    }

    /**
     * Provide an optional list which contains the specific generator of each parameter of the constructor
     * @param constr the constructor to be verified
     * @return An optional list of generator
     */
    private static Optional<List<Generator>> getGeneratorsForConstructor (Constructor<?> constr) {
        if(constr.getParameters().length==0) {
            return Optional.of(Collections.emptyList());
        }
        List<Generator> generate = new ArrayList<>();
        for (Class<?> type : constr.getParameterTypes()) {
            Generator newgen = generators.get(type);
            if (newgen==null  && type.isArray()) {
                Class<?> arrayType = type.getComponentType();
                newgen = generators.get(arrayType);
                if (newgen!=null) {
                    //Creer le nouveau generateur d'array à partir de newgen et l'ajouter à generators
                    Generator newGenerator = createArrayGenerator(newgen,arrayType);
                    registerGenerator(newGenerator);
                    newgen=newGenerator;
                }
            }
            if(newgen==null) {
                return Optional.empty();
            }
            generate.add(newgen);
        }
        return Optional.of(generate);
    }

    /**
     *
     * @param construc
     * @return
     */
    public static Object generateConstructor(Constructor<?> construc) {
        List<Generator> listGenerateurs = getGeneratorsForConstructor(construc).get();
        List<Object> generatedArguments = new ArrayList<>();
        for (Generator gen : listGenerateurs) {
            generatedArguments.add(gen.generate());
        }
        try {
            Object o =  construc.newInstance(generatedArguments.toArray());
            return o;
        } catch (InstantiationException | IllegalAccessException |InvocationTargetException e ) {
            e.printStackTrace();
            return e.getCause().getClass();

            //System.out.println("La classe de l'erreur :"+e.getCause().getClass());
            //System.out.println("Les exceptions du constructeur "+construc.getExceptionTypes());
            // le parcourir et faire un test de sous typage avec isAssignableFrom
        }
       /* catch (Exception f) {
            System.err.println("Exception pas de notre faute ");
            f.printStackTrace();
        }*/
    }

    public static void generateConstructorsOfClass(Class klass)  {
        /* Au cas où on genere les constructeurs de la classe Integer,
         on doit generer un String d'entiers pour le constructeur*/
        if (klass.getName().equals(Integer.class.getName())) {
            registerGenerator(new RandomStringOfIntGenerator());
        }
        for (Constructor<?> each : klass.getConstructors()) {
            Optional<List<Generator>> optionalGeneratorList =getGeneratorsForConstructor(each);
            if(optionalGeneratorList.isPresent()) {
                generateConstructor(each);
            }
        }
        //On remet le generateur de String par défaut
        if (klass.getName().equals(Integer.class.getName())) {
            registerGenerator(stringGenerator);
        }
    }

    public static void main(String[] args) {
        generateConstructorsOfClass(String.class);
    }
}
