package complex;

import common.DependencyException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rarques on 6/11/2017.
 */
public class Container implements Injector {

    private Map<Class<?>, Object> registeredConstants = new HashMap<>();

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
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public <E> E getObject(Class<E> name)
            throws DependencyException {
        if (registeredConstants.containsKey(name)) {
            Object obj = registeredConstants.get(name);
            return name.cast(obj);
        } else {
            throw new DependencyException("Not registered constant/factory");
        }
    }

}
