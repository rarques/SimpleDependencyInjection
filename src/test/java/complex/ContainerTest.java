package complex;

import common.DependencyException;
import factories.complex.SimpleFactory;
import implementations.ImplementationD1;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by rarques on 6/11/2017.
 */
public class ContainerTest {

    private Injector injector;

    @Before
    public void setUp() throws Exception {
        injector = new Container();
    }

    @Test
    public void registerConstant() throws DependencyException {
        injector.registerConstant(String.class, "Hello");
        String s = injector.getObject(String.class);
        assertThat(s, is("Hello"));
    }

    @Test(expected = DependencyException.class)
    public void cannotRegisterConstantsWithSameName() throws DependencyException {
        injector.registerConstant(String.class, "value");
        injector.registerConstant(String.class, "another value");
    }

    @Test(expected = DependencyException.class)
    public void requestNotRegisteredConstant() throws DependencyException {
        injector.getObject(Integer.class);
    }

    @Test
    public void registerFactoryWithoutParameters() throws DependencyException {
        injector.registerFactory(String.class, new SimpleFactory());
        String actual = injector.getObject(String.class);
        assertThat(actual, is("Hello from factory"));
    }

//    @Test
//    public void registerFactory() throws DependencyException {
//        Injector injector = new Container();
//        injector.registerConstant(Integer.class, 42);
//        injector.registerFactory(InterfaceD.class,
//                new FactoryD1(),
//                Integer.class);
//        InterfaceD d = injector.getObject(InterfaceD.class);
//        assertThat(d, is(instanceOf(ImplementationD1.class)));
//        ImplementationD1 d1 = (ImplementationD1) d;
//        assertThat(d1.getI(), is(42));
//    }

}

class FactoryD1 implements Factory<ImplementationD1> {
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