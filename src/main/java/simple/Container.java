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
    private Map<String, Factory> registeredFactories = new HashMap<>();
    private Map<Factory, String[]> parametersForFactories = new HashMap<>();

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
            parametersForFactories.put(factory, parameters);
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

    private Object createObjectFromFactory(String name) throws DependencyException {
        Factory factory = registeredFactories.get(name);
        String[] parameters = parametersForFactories.get(factory);
        List<Object> constants = getConstantsFromParameters(parameters);
        return factory.create(constants.toArray());
    }

    private List<Object> getConstantsFromParameters(String[] parameters) throws DependencyException {
        List<Object> constants = new LinkedList<>();
        for (String parameter : parameters) {
            Object constant = getConstant(parameter);
            constants.add(constant);
        }
        return constants;
    }

    private Object getConstant(String parameter) throws DependencyException {
        Object constant = registeredConstants.get(parameter);
        if (constantIsNotRegistered(constant)) {
            throw new DependencyException("Constant '" + parameter + "' not registered");
        }
        return constant;
    }

    private boolean constantIsNotRegistered(Object constant) {
        return constant == null;
    }

}
