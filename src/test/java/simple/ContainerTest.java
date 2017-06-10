package simple;

import common.DependencyException;
import implementations.ImplementationD1;
import interfaces.InterfaceD;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;

public class ContainerTest {

//    @Test
//    public void sampleTest() {
//        try {
//            Injector injector = new Container();
//            injector.registerConstant("I", 42);
//            injector.registerFactory("D", new FactoryD1(), "I");
//            InterfaceD d = (InterfaceD) injector.getObject("D");
//            assertThat(d, is(instanceOf(ImplementationD1.class)));
//            ImplementationD1 d1 = (ImplementationD1) d;
//            assertThat(d1.getI(), is(42));
//        } catch (DependencyException ex) {
//            ex.printStackTrace();
//        }
//    }

}

class FactoryD1 implements Factory {

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