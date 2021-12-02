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

    public static void generateConstructorsOfClass(Class klass)  {
        /* Au cas où on genere les constructeurs de la classe Integer,
         on doit generer un String d'entiers pour le constructeur
        System.out.println("La classe du constructeur "+klass.getName()+" vs "+Integer.class.getName());
        if (klass.getName().equals(Integer.class.getName())) {
            registerGenerator(new RandomStringOfIntGenerator());
        }
        */
        for (Constructor<?> each : klass.getConstructors()) {
            System.out.println("La signature du constructeur : "+each);
            generateConstructor(each);
            // else System.out.println("Impossible to generate this constructor" + "\n");
        }

        /**
         //On remet le generateur de String par défaut
         if (klass.getName().equals(Integer.class.getName())) {
         registerGenerator(stringGenerator);
         }
         **/
    }


    public static Optional<List<Generator> > getParamGenerators(Constructor<?> constructeur)  {
        List<Generator> listGen = new ArrayList<>();

        // Vérification arg pas vide
        if(constructeur.getParameterTypes().length==0){
            return Optional.of(listGen);
        }

        for (Class<?> type : constructeur.getParameterTypes())
        {
            if(generators.containsKey(type)){
                listGen.add(generators.get(type));
            }
            else if (!(generators.containsKey(type)) && (type.isArray())){
                Class typePrimitive = type.getComponentType() ;
                Generator primTypeGen = generators.get(typePrimitive);
                System.out.println(" ------type-----------  " + type);
                if ( primTypeGen != null){
                    Generator arrayGenerator = createArrayGenerator(typePrimitive, type);
                    generators.put(type, arrayGenerator);
                    listGen.add(arrayGenerator);
                }
            }

        }
        return Optional.of(listGen);
    }


    public static void generateConstructor(/** Optional<List<Generator>> listGen,**/ Constructor<?> constructeur)  {
        Optional<List<Generator>> optionalListGenerateurs = getParamGenerators(constructeur);
        List<Generator> listGenerateurs = optionalListGenerateurs.get();
        List<Object> listObjets = new ArrayList<>();

        // Faut ajouter une verification si jamais une des valeurs dans la liste est null
        for (Generator gen : listGenerateurs ) {
            Object o = gen.generate();
            listObjets.add(o);
        }
        // System.out.println("la taille de la liste est : "+list.size());
        try {
           /* for(int i=0;i<list.size();i++) {
                System.out.println("the object" + list.get(i));
            }*/
            System.out.println("==============>" + constructeur.getParameterCount() );
            System.out.println("==============>" + Arrays.toString(constructeur.getParameterTypes()) );
            System.out.println("==============>" + constructeur);

            Object o = constructeur.newInstance(Arrays.asList(listObjets) );
            System.out.println("La nouvelle instance de l'objet: {" + o +  "}\n");
        } catch (InstantiationException | IllegalAccessException |InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour la generation d'un generateur d'array d'un type
    // Object tab = java.lang.reflect.Array,new Instance(int.class, list.size)
    // for( int i=0; i<list_ints.size(); i++]
    //{     Array..set(tab_ints, i , list_ints.get(i)
    public static Generator createArrayGenerator(Class typePrimitive, Class type){

         class complexTypeGenarator implements Generator{
             Class typePrimitive ;
             Class type;

             public complexTypeGenarator(Class typePrimitive, Class type) {
                 this.typePrimitive = typePrimitive;
                 this.type = type;
             }

             @Override
            public Object generate() {
                Generator<?> primTypeGenerator=generators.get(typePrimitive);
                List<Object> tmp = new ArrayList<>();
                for (int i=0; i<20; i++){
                    tmp.add(generators.get(typePrimitive).generate());
                }
                // retourner un Object de array int
                return tmp;
            }

            @Override
            public Class[] types() {
                Class[] types={type};
                return types;
            }
        }
        complexTypeGenarator generateurArray = new complexTypeGenarator(typePrimitive, type);

        return generateurArray ;
    }

/**
    public static boolean canGenerateConstructor(Constructor<?> constr) {
        boolean possible = false;
        int i = 0;
        System.out.println("Le nombre de paramètres du constructeur : "+constr.getParameters().length);
        if(constr.getParameters().length==0) {
            System.out.println("Ce constructeur n'a pas de paramètres donc pas besoin de générateur.");
            return true;
        }
        for (Class<?> type : constr.getParameterTypes()) {
            System.out.println("Le Type du paramètre est: " +type);
            if(type.isArray()) System.out.println("==============>" + type.getComponentType());

            if(generators.containsKey(type)) {
                System.out.println("Le generateur associé est: " + generators.get(type));
                i++;
                if(i==constr.getParameterCount()) {
                    possible = true;
                    System.out.println("Nous avons trouvé générateur pour ce constructeur");
                }
            }
            else {
                System.out.println("Il n'y a pas de générateur pour ce constructeur");
                break;
            }
        }
        return possible;
    }
**/

    public static void main(String[] args) {

        //Generator gen = createArrayGenerator(Integer.class, Integer.class);
        //System.out.println(gen.generate());

        generateConstructorsOfClass(Person.class);

    }
}
