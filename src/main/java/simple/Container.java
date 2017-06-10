package simple;

import common.DependencyException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rarques on 6/8/2017.
 */
public class Container implements Injector {

    private Map<String, Object> registeredConstants = new HashMap<>();
    private Map<String, Factory> registeredFactories = new HashMap<>();

    public void registerConstant(String name,
                                 Object value)
            throws DependencyException {

        if (!registeredConstants.containsKey(name)) {
            registeredConstants.put(name, value);
        } else {
            throw new DependencyException("A constant is already registered with the same name");
        }
    }

    public void registerFactory(String name,
                                Factory factory,
                                String... parameters)
            throws DependencyException {

        if (!registeredFactories.containsKey(name)) {
            registeredFactories.put(name, factory);
        } else {
            throw new DependencyException("A factory is already registered with the same name");
        }
    }

    public Object getObject(String name)
            throws DependencyException {

        if (isConstant(name)) {
            return registeredConstants.get(name);
        } else if (isFactory(name)) {
            Factory factory = registeredFactories.get(name);
            return factory.create();
        } else {
            throw new DependencyException("Not registered constant/factory");
        }

    }

    private boolean isConstant(String name) {
        return registeredConstants.containsKey(name);
    }

    private boolean isFactory(String name) {
        return registeredFactories.containsKey(name);
    }

}
