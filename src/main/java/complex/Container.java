package complex;

import common.DependencyException;

/**
 * Created by rarques on 6/11/2017.
 */
public class Container implements Injector {

    @Override
    public <E> void registerConstant(Class<E> name, E value)
            throws DependencyException {
        throw new UnsupportedOperationException("Not implemented yet");
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
        throw new UnsupportedOperationException("Not implemented yet");
    }

}
