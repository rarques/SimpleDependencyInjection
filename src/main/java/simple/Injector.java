package simple;

import common.DependencyException;

/**
 * Created by rarques on 6/8/2017.
 */
public interface Injector {

    void registerConstant(String name,
                          Object value)
            throws DependencyException;

    void registerFactory(String name,
                         Factory factory,
                         String... parameters)
            throws DependencyException;

    Object getObject(String name)
            throws DependencyException;

}
