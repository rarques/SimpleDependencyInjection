package factories;

import common.DependencyException;
import implementations.ImplementationB1;
import interfaces.InterfaceD;
import simple.Factory;

/**
 * Created by rarques on 6/10/2017.
 */
public class FactoryB1 implements Factory {

    @Override
    public ImplementationB1 create(Object... parameters) throws DependencyException {
        InterfaceD d;
        try {
            d = (InterfaceD) parameters[0];
        } catch (ClassCastException | ArrayIndexOutOfBoundsException ex) {
            throw new DependencyException(ex);
        }
        return new ImplementationB1(d);
    }

}
