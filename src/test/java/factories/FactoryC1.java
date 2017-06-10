package factories;

import common.DependencyException;
import implementations.ImplementationC1;
import simple.Factory;

/**
 * Created by rarques on 6/10/2017.
 */
public class FactoryC1 implements Factory{

    @Override
    public ImplementationC1 create(Object... parameters)
            throws DependencyException {
        String s;
        try {
            s = (String) parameters[0];
        } catch (ClassCastException | ArrayIndexOutOfBoundsException ex) {
            throw new DependencyException(ex);
        }
        return new ImplementationC1(s);
    }

}
