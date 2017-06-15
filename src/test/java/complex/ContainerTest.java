package complex;

import common.DependencyException;
import factories.complex.*;
import implementations.ImplementationA1;
import implementations.ImplementationB1;
import implementations.ImplementationC1;
import implementations.ImplementationD1;
import interfaces.InterfaceA;
import interfaces.InterfaceB;
import interfaces.InterfaceC;
import interfaces.InterfaceD;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;

/**
 * Created by rarques on 6/11/2017.
 */
public class ContainerTest {

    private Injector injector;

    @Before
    public void setUp() throws Exception {
        injector = new Container();
        registerConstantsAndFactories();
    }

    private void registerConstantsAndFactories() throws DependencyException {
        injector.registerConstant(String.class, "Hello");
        injector.registerConstant(Integer.class, 42);
        injector.registerFactory(InterfaceA.class, new FactoryA1(),
                InterfaceB.class, InterfaceC.class);
        injector.registerFactory(InterfaceB.class, new FactoryB1(), InterfaceD.class);
        injector.registerFactory(InterfaceC.class, new FactoryC1(), String.class);
        injector.registerFactory(InterfaceD.class, new FactoryD1(), Integer.class);
    }

    @Test
    public void registerConstant() throws DependencyException {
        injector.registerConstant(Long.class, 123456789L);
        Long l = injector.getObject(Long.class);
        assertThat(l, is(123456789L));
    }

    @Test(expected = DependencyException.class)
    public void cannotRegisterConstantsWithSameName() throws DependencyException {
        injector.registerConstant(Long.class, 12L);
        injector.registerConstant(Long.class, 12L);
    }

    @Test
    public void registerFactoryWithoutParameters() throws DependencyException {
        injector.registerFactory(Long.class, new SimpleFactory());
        Long actual = injector.getObject(Long.class);
        assertThat(actual, is(123456789L));
    }

    @Test(expected = DependencyException.class)
    public void registerFactoriesWithSameName() throws DependencyException {
        injector.registerFactory(Long.class, new SimpleFactory());
        injector.registerFactory(Long.class, new SimpleFactory());
    }

    @Test(expected = DependencyException.class)
    public void createObjectWithUnregisteredFactory() throws DependencyException {
        injector.getObject(Double.class);
    }

    @Test(expected = DependencyException.class)
    public void createObjectWithoutRegisteredDependencies() throws DependencyException {
        injector.registerFactory(InterfaceD.class, new FactoryD1(), Long.class);
        injector.getObject(InterfaceD.class);
    }

    @Test
    public void createImplementationD1() throws DependencyException {
        InterfaceD d = injector.getObject(InterfaceD.class);
        assertThat(d, is(instanceOf(ImplementationD1.class)));
        ImplementationD1 d1 = (ImplementationD1) d;
        assertThat(d1.getI(), is(42));
    }

    @Test
    public void createImplementationC1() throws DependencyException {
        InterfaceC c = injector.getObject(InterfaceC.class);
        assertThat(c, is(instanceOf(ImplementationC1.class)));
        ImplementationC1 c1 = (ImplementationC1) c;
        assertThat(c1.getString(), is("Hello"));
    }

    @Test
    public void createImplementationB1() throws DependencyException {
        InterfaceB b = injector.getObject(InterfaceB.class);
        assertThat(b, is(instanceOf(ImplementationB1.class)));

        ImplementationB1 b1 = (ImplementationB1) b;
        InterfaceD d = b1.getD();
        assertThat(d, is(instanceOf(ImplementationD1.class)));

        ImplementationD1 d1 = (ImplementationD1) d;
        assertThat(d1.getI(), is(42));
    }

    @Test
    public void createImplementationA1() throws DependencyException {
        InterfaceA a = injector.getObject(InterfaceA.class);
        assertThat(a, is(instanceOf(ImplementationA1.class)));

        ImplementationA1 a1 = (ImplementationA1) a;
        InterfaceB b = a1.getB();
        assertThat(b, is(instanceOf(ImplementationB1.class)));

        InterfaceC c = a1.getC();
        assertThat(c, is(instanceOf(ImplementationC1.class)));

        InterfaceD d = ((ImplementationB1) b).getD();
        assertThat(d, is(instanceOf(ImplementationD1.class)));

        int i = ((ImplementationD1) d).getI();
        assertThat(i, is(42));

        String s = ((ImplementationC1) c).getString();
        assertThat(s, is("Hello"));
    }

}
