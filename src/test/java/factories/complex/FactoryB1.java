package factories.complex;

import common.DependencyException;
import complex.Factory;
import implementations.ImplementationB1;
import interfaces.InterfaceB;
import interfaces.InterfaceD;

/**
 * Created by rarques on 6/15/2017.
 */
public class FactoryB1 implements Factory<InterfaceB> {


    @Override
    public InterfaceB create(Object... parameters) throws DependencyException {
        InterfaceD d;
        try {
            d = (InterfaceD) parameters[0];
        } catch (ClassCastException | ArrayIndexOutOfBoundsException ex) {
            throw new DependencyException(ex);
        }
        return new ImplementationB1(d);
    }
}
