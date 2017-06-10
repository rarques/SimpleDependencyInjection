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
        registeredFactories.put(name, factory);
    }

    public Object getObject(String name)
            throws DependencyException {
        if (registeredConstants.containsKey(name)) {
            return registeredConstants.get(name);
        } else if (registeredFactories.containsKey(name)) {
            Factory factory = registeredFactories.get(name);
            return factory.create();
        } else {
            throw new DependencyException("Not registered constant/factory");
        }
    }

}
