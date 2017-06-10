package factories;

import common.DependencyException;
import implementations.ImplementationD1;
import simple.Factory;

/**
 * Created by rarques on 6/10/2017.
 */
public class FactoryD1 implements Factory {

    @Override
    public ImplementationD1 create(Object... parameters)
            throws DependencyException {
        int i;
        try {
            i = (int) parameters[0];
        } catch (ClassCastException | ArrayIndexOutOfBoundsException ex) {
            throw new DependencyException(ex);
        }
        return new ImplementationD1(i);
    }

}
