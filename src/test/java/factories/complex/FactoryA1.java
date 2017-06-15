package factories.complex;

import common.DependencyException;
import complex.Factory;
import implementations.ImplementationA1;
import interfaces.InterfaceB;
import interfaces.InterfaceC;

/**
 * Created by rarques on 6/15/2017.
 */
public class FactoryA1 implements Factory<ImplementationA1> {

    @Override
    public ImplementationA1 create(Object... parameters) throws DependencyException {
        InterfaceB b;
        InterfaceC c;

        try {
            b = (InterfaceB) parameters[0];
            c = (InterfaceC) parameters[1];
        } catch (ClassCastException | ArrayIndexOutOfBoundsException ex) {
            throw new DependencyException(ex);
        }
        return new ImplementationA1(b, c);
    }

}
