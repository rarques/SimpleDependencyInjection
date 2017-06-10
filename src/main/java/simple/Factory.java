package simple;

import common.DependencyException;

/**
 * Created by rarques on 6/8/2017.
 */
public interface Factory {

    Object create(Object... parameters)
            throws DependencyException;

}
