package factories.simple;

import common.DependencyException;
import implementations.ImplementationA1;
import interfaces.InterfaceB;
import interfaces.InterfaceC;
import simple.Factory;

/**
 * Created by rarques on 6/10/2017.
 */
public class FactoryA1 implements Factory {

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
