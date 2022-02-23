package org.atlanmod.testing;

import org.atlanmod.commons.annotation.Static;
import org.atlanmod.commons.io.serializer.BinarySerializerFactory;
import org.atlanmod.commons.reflect.MoreReflection;
import org.atlanmod.testing.generator.*;

import javax.annotation.Nonnull;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Instantiator {
    private final Map<Class<?>,Generator<?>> generators = new HashMap<>();
    private final Map<Class, Constructor[]> knownConstructors = new HashMap<>();


    public Instantiator() {
        this.initializeDefautGenerators();
    }

    private void initializeDefautGenerators() {
        this.registerGenerator(new BoundaryIntegerGenerator());
        this.registerGenerator(new RoundRobinBooleanGenerator());
        this.registerGenerator(new RandomCharGenerator());
        this.registerGenerator(new RandomByteGenerator());
        this.registerGenerator(new SimpleStringGenerator());
    }

    /**
     * Registers a new generator for a specific type.
     * @param generator the generator of the target class.
     */
    protected void registerGenerator(Generator generator) {
        for (Class<?> type :generator.types() ) {
            generators.put(type, generator);
        }
    }

    private boolean isCandidateConstructor(Constructor constructor) {
        for (Class<?> each : constructor.getParameterTypes()) {
            if (!generators.containsKey(each)) {
                return false;
            }
        }
        return true;
    }

    public <T> T instanciate(Class<T> klass) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        if(!knownConstructors.containsKey(klass)) {
            this.registerClass(klass);
        }
        Constructor<T>[] availableConstructors = knownConstructors.get(klass);
        if (availableConstructors.length == 0) {
            throw new RuntimeException("Cannot find a commpatible constructor");
        }
        Constructor<T> constructor = availableConstructors[1];
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        Object[] arguments = new Object[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            Generator<?> generator = generators.get(parameterTypes[i]);
            arguments[i] = generator.generate();
        }

        return constructor.newInstance(arguments);
    }

    private <T> void registerClass(Class<T> klass) {
        List<Constructor> candidates = new LinkedList<>();
        for (Constructor each : klass.getConstructors()) {
            if (isCandidateConstructor(each)) candidates.add(each);
        }
        this.knownConstructors.put(klass, candidates.toArray(new Constructor[0]));
    }

    /**
     * Provide an optional list which contains the specific generator of each parameter of the constructor
     * @param constr the constructor to be verified
     * @return An optional list of generator
     */
    private Optional<List<Generator>> getGeneratorsForConstructor (Constructor<?> constr) {
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
                    Generator newGenerator = createArrayGenerator(newgen, arrayType);
                    registerGenerator(newGenerator);
                    newgen = newGenerator;
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
     *creation of an array generator from his simple generator.
     * @param gen the  simple generator
     * @param arrayType the class of the array generator we want to create
     * @return An array generator
     */
    public Generator createArrayGenerator(Generator gen,Class arrayType)  {
        Random random =new Random();
        int length= random.nextInt(10) + 1;
        return new Generator() {
            @Override
            public Object generate() {
                Object list = Array.newInstance(arrayType, length);
                for (int i = 0; i < length; i++) {
                    Array.set(list, i, gen.generate());
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
    }


    /**
     *Generate a new instance of a constructor and return it.
     * @param construc the constructor we want to generate
     * @return A new instance of a constructor
     */
    public  Object generateConstructor(Constructor<?> construc) {
        List<Generator> listGenerateurs = getGeneratorsForConstructor(construc).get();
        List<Object> generatedArguments = new ArrayList<>();
        for (Generator gen : listGenerateurs) {
            generatedArguments.add(gen.generate());
        }
        try {
            return   construc.newInstance(generatedArguments.toArray());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e ) {
            e.printStackTrace();
            return e.getCause().getClass();
        }
    }

    /**
     * Generate a new instance of the constructors of a class and put them in a list.
     * @param klass the class we want to generate all constructors
     * @return A list of objects of type klass
     */
    public List<Object> generateConstructorsOfClass(Class klass)  {

        if (klass.getName().equals(Integer.class.getName())) {
            registerGenerator(new SimpleStringGenerator());
        }
        List<Object> listConstructors = new ArrayList<>();
        for (Constructor<?> each : klass.getConstructors()) {
            Optional<List<Generator>> optionalGeneratorList = getGeneratorsForConstructor(each);
            if(optionalGeneratorList.isPresent()) {
                listConstructors.add(generateConstructor(each));
            }
        }
        if (klass.getName().equals(Integer.class.getName())) {
            //registerGenerator(stringGenerator);
        }
        return listConstructors;
    }

    /**
     * Returns the instance of this class.
     *
     * @return the instance of this class
     */
    @Nonnull
    public static Instantiator getInstance() {
        return Instantiator.Holder.INSTANCE;
    }

    /**
     * The initialization-on-demand holder of the singleton of this class.
     */
    @Static
    private static final class Holder {

        /**
         * The instance of the outer class.
         */
        private static final Instantiator INSTANCE = new Instantiator();
    }
}
