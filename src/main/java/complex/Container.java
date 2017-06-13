package complex;

import common.DependencyException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rarques on 6/11/2017.
 */
public class Container implements Injector {

    private Map<Class<?>, Object> registeredConstants = new HashMap<>();
    private Map<Class<?>, Factory<?>> registeredFactories = new HashMap<>();

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
            registeredFactories.put(name, creator);
        } else {
            throw new DependencyException("A factory is already registered with the same name");
        }
    }

    @Override
    public <E> E getObject(Class<E> name)
            throws DependencyException {
        if (isConstant(name)) {
            Object obj = registeredConstants.get(name);
            return name.cast(obj);
        } else if (isFactory(name)) {
            Factory factory = registeredFactories.get(name);
            return name.cast(factory.create());
        } else {
            throw new DependencyException("Not registered constant/factory");
        }
    }

    private <E> boolean isFactory(Class<E> name) {
        return registeredFactories.containsKey(name);
    }

    private <E> boolean isConstant(Class<E> name) {
        return registeredConstants.containsKey(name);
    }

}
