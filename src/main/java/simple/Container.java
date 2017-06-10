package simple;

import common.DependencyException;

/**
 * Created by rarques on 6/8/2017.
 */
public class Container implements Injector {

    public void registerConstant(String name,
                                 Object value)
            throws DependencyException {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void registerFactory(String name,
                                Factory factory,
                                String... parameters)
            throws DependencyException {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public Object getObject(String name)
            throws DependencyException {
        throw new UnsupportedOperationException("Not implemented yet");
    }

}
