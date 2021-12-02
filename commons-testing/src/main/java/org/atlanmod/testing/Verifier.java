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
 */
public class Verifier {
    private static final Map <Class<?>, Generator<?> > generators= new HashMap<>();
    private static Generator stringGenerator= new RandomStringGenerator();
    private static Generator integerGenerator= new RandomIntegerGenerator();

    static {
        registerGenerator(integerGenerator);
        registerGenerator(stringGenerator);
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


    public static <T extends Serializable> SerializationVerifier<T> verifySerializer(Class<T> type) {
        return new SerializationVerifier<>(type);
    }

    public static <T> void registerGenerator(Generator <T> generator) {
        for (Class<?> type :generator.types() ) {
            generators.put(type, generator);
        }
    }

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

    public static Optional<List<Generator>> getGeneratorsForConstructor (Constructor<?> constr) {
        if(constr.getParameters().length==0) {
            System.out.println("Retour lorsque le constructeur n'a pas de paramètres : ");
            return Optional.of(Collections.emptyList());
        }
        List<Generator> generate = new ArrayList<>();
        for (Class<?> type : constr.getParameterTypes()) {
            Generator newgen = generators.get(type);
            System.out.println("Le generateur associé est : "+newgen);
            if (newgen==null  && type.isArray()) {
                Class<?> arrayType = type.getComponentType();
                System.out.println("Le type de l'array est : "+arrayType);
                newgen = generators.get(arrayType);
                System.out.println("Le generateur associé au type de l'array est : "+newgen);
                if (newgen!=null) {
                    //Creer le nouveau generateur d'array à partir de newgen et l'ajouter à generators
                    Generator newGenerator = createArrayGenerator(newgen,arrayType);
                    //generators.put(arrayType,newGenerator)
                    registerGenerator(newGenerator);
                    newgen=newGenerator;
                }
            }
            if(newgen==null) {
                System.out.println("Retour lorsqu'on a pas de generateur pour ce constructeur : " +generate.size());
                return Optional.empty();
            }
            generate.add(newgen);
        }
        System.out.println("Retour lorsqu'on a trouvé un generateur pour ce constructeur de taille : " +generate.size());
        return Optional.of(generate);
    }

    public static void getObjectFrom(Constructor<?> construc, List<Generator> listGenerateurs) {
        List<Object> generatedArguments = new ArrayList<>();
        for (Generator gen : listGenerateurs) {
            generatedArguments.add(gen.generate());
        }
        System.out.println("la taille de la liste est : "+generatedArguments.size());

        for(Object j : generatedArguments) {
            System.out.println("elt de la liste "+j);
        }
        try {
            Object o = construc.newInstance(generatedArguments.toArray());
            System.out.println("La nouvelle instance de l'objet: {" + o +  "}\n");
        } catch (InstantiationException | IllegalAccessException |InvocationTargetException e ) {
            e.printStackTrace();
            System.err.println(e.getCause().getClass());
            //constructor.getExceptionTypes(); le parcourir et faire un test de sous typage avec isAssignableFrom
        }
       /* catch (Exception f) {
            System.err.println("Exception pas de notre faute ");
            f.printStackTrace();
        }*/
    }

    public static void generateConstructorsOfClass(Class klass)  {
        /* Au cas où on genere les constructeurs de la classe Integer,
         on doit generer un String d'entiers pour le constructeur*/
        System.out.println("La classe du constructeur "+klass.getName()+" vs "+Integer.class.getName());
        if (klass.getName().equals(Integer.class.getName())) {
            registerGenerator(new RandomStringOfIntGenerator());
        }
        for (Constructor<?> each : klass.getConstructors()) {
            System.out.println("La signature du constructeur : "+each);
            Optional<List<Generator>> optionalGeneratorList =getGeneratorsForConstructor(each);
            if(optionalGeneratorList.isPresent()) {
                List<Generator> generatorList = optionalGeneratorList.get();
                getObjectFrom(each, generatorList);
            }
        }
        //On remet le generateur de String par défaut
        if (klass.getName().equals(Integer.class.getName())) {
            registerGenerator(stringGenerator);
        }
    }

    public static void main(String[] args) {
        generateConstructorsOfClass(Person.class);
        System.out.println("----------------------------------");
        System.out.println("----------------------------------");
        System.out.println("----------------------------------");
        generateConstructorsOfClass(Person.class);
    }
}
