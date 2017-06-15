package simple;

import common.DependencyException;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by rarques on 6/8/2017.
 */
public class Container implements Injector {

    private Map<String, Object> registeredConstants = new HashMap<>();
    private Map<String, FactoryWrapper> registeredFactories = new HashMap<>();

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
            FactoryWrapper wrapper = new FactoryWrapper(factory, parameters);
            registeredFactories.put(name, wrapper);
        } else {
            throw new DependencyException("A factory is already registered with the same name");
        }
    }

    public Object getObject(String name)
            throws DependencyException {
        if (isConstant(name)) {
            return registeredConstants.get(name);
        } else if (isFactory(name)) {
            return createObjectFromFactory(name);
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

    private Object createObjectFromFactory(String name)
            throws DependencyException {
        FactoryWrapper wrapper = registeredFactories.get(name);
        Factory factory = wrapper.factory;
        String[] parameters = wrapper.parameters;
        List<Object> objects = getObjectsFromParameters(parameters);
        return factory.create(objects.toArray());
    }

    private List<Object> getObjectsFromParameters(String[] parameters)
            throws DependencyException {
        List<Object> objects = new LinkedList<>();
        for (String parameter : parameters) {
            Object object = getObject(parameter);
            objects.add(object);
        }
        return objects;
    }

    private class FactoryWrapper {

        private Factory factory;
        private String[] parameters;

        FactoryWrapper(Factory factory, String[] parameters) {
            this.factory = factory;
            this.parameters = parameters;
        }
    }
}
