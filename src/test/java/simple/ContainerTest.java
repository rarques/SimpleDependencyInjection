package simple;

import common.DependencyException;
import implementations.ImplementationD1;
import interfaces.InterfaceD;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;

public class ContainerTest {

    private Injector injector;

    @Before
    public void setUp() throws Exception {
        injector = new Container();
    }

    @Test
    public void registerConstant() throws DependencyException {
        int value = 25;
        injector.registerConstant("CONSTANT_A", value);
        assertThat(injector.getObject("CONSTANT_A"), is(value));
    }

    @Test(expected = DependencyException.class)
    public void cannotRegisterConstantsWithSameName() throws DependencyException {
        injector.registerConstant("CONSTANT_NAME", "some value");
        injector.registerConstant("CONSTANT_NAME", "another value");
    }

    @Test
    public void registerFactory() throws DependencyException {
        injector.registerFactory("SimpleObject", new SimpleFactory());
        String createdObject = (String) injector.getObject("SimpleObject");
        assertThat(createdObject, is("Simple object created"));
    }

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

class SimpleFactory implements Factory {

    @Override
    public Object create(Object... parameters) throws DependencyException {
        return "Simple object created";
    }
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