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
        registeredConstants.put(name, value);
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
        Object obj = registeredConstants.get(name);
        return name.cast(obj);
    }

}
