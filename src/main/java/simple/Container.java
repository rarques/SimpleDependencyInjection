package simple;

import common.DependencyException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rarques on 6/8/2017.
 */
public class Container implements Injector {

    private Map<String, Object> registeredConstants = new HashMap<>();

    public void registerConstant(String name,
                                 Object value)
            throws DependencyException {
        registeredConstants.put(name, value);
    }

    public void registerFactory(String name,
                                Factory factory,
                                String... parameters)
            throws DependencyException {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public Object getObject(String name)
            throws DependencyException {
        return registeredConstants.get(name);
    }

}
