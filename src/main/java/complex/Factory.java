package complex;

import common.DependencyException;

/**
 * Created by rarques on 6/8/2017.
 */
public interface Factory<E> {

    E create(Object... parameters)
            throws DependencyException;

}
