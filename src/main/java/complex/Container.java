package complex;

import common.DependencyException;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by rarques on 6/11/2017.
 */
public class Container implements Injector {

    private Map<Class<?>, Object> registeredConstants = new HashMap<>();
    private Map<Class<?>, FactoryWrapper> registeredFactories = new HashMap<>();

    @Override

    public <E> void registerConstant(Class<E> name, E value)
            throws DependencyException {
        if (!registeredConstants.containsKey(name)) {
            registeredConstants.put(name, value);
        } else {
            throw new DependencyException("A constant is already registered with the same name");
        }

    }

    @Override
    public <E> void registerFactory(Class<E> name,
                                    Factory<? extends E> creator,
                                    Class<?>[] parameters)
            throws DependencyException {
        if (!registeredFactories.containsKey(name)) {
            FactoryWrapper wrapper = new FactoryWrapper(creator, parameters);
            registeredFactories.put(name, wrapper);
        } else {
            throw new DependencyException("A factory is already registered with the same name");
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E> E getObject(Class<E> name)
            throws DependencyException {
        if (isConstant(name)) {
            return (E) registeredConstants.get(name);
        } else if (isFactory(name)) {
            return createObjectFromFactory(name);
        } else {
            throw new DependencyException("Not registered constant/factory");
        }
    }

    @SuppressWarnings("unchecked")
    private <E> E createObjectFromFactory(Class<E> name) throws DependencyException {
        FactoryWrapper wrapper = registeredFactories.get(name);
        Factory<? extends E> factory = (Factory<? extends E>) wrapper.factory;
        Class<?>[] parameters = wrapper.parameters;
        Object[] factoryParameters = getParametersFromInjector(parameters);
        return (E) factory.create(factoryParameters);
    }

    private Object[] getParametersFromInjector(Class<?>[] parameters) throws DependencyException {
        List<Object> objects = new LinkedList<>();
        for (Class<?> c :
                parameters) {
            Object obj = getObject(c);
            objects.add(obj);
        }
        return objects.toArray();
    }

    private <E> boolean isFactory(Class<E> name) {
        return registeredFactories.containsKey(name);
    }

    private <E> boolean isConstant(Class<E> name) {
        return registeredConstants.containsKey(name);
    }

    private class FactoryWrapper {

        Factory<?> factory;
        Class<?>[] parameters;

        FactoryWrapper(Factory<?> factory, Class<?>[] parameters) {
            this.factory = factory;
            this.parameters = parameters;
        }
    }
}
