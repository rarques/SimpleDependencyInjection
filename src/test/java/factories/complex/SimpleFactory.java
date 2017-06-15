package factories.complex;

import common.DependencyException;
import complex.Factory;

/**
 * Created by rarques on 6/10/2017.
 */
public class SimpleFactory implements Factory<Long> {

    @Override
    public Long create(Object... parameters) throws DependencyException {
        return (long) 123456789;
    }

}
