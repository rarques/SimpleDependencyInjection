package simple;

import common.DependencyException;
import factories.simple.*;
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

public class ContainerTest {

    private Injector injector;

    @Before
    public void setUp() throws Exception {
        injector = new Container();
        registerConstantsAndFactories();
    }

    private void registerConstantsAndFactories() throws DependencyException {
        injector.registerConstant("S", "Hello");
        injector.registerConstant("I", 25);
        injector.registerFactory("A1", new FactoryA1(), "B1", "C1");
        injector.registerFactory("B1", new FactoryB1(), "D1");
        injector.registerFactory("C1", new FactoryC1(), "S");
        injector.registerFactory("D1", new FactoryD1(), "I");
    }

    @Test
    public void registerConstant() throws DependencyException {
        int value = 35;
        injector.registerConstant("CONSTANT_A", value);
        assertThat(injector.getObject("CONSTANT_A"), is(value));
    }

    @Test(expected = DependencyException.class)
    public void cannotRegisterConstantsWithSameName() throws DependencyException {
        injector.registerConstant("CONSTANT_NAME", "some value");
        injector.registerConstant("CONSTANT_NAME", "another value");
    }

    @Test(expected = DependencyException.class)
    public void requestNotRegisteredConstant() throws DependencyException {
        injector.getObject("NOT_REGISTERED_CONSTANT");
    }

    @Test
    public void registerFactoryWithOutParameters() throws DependencyException {
        injector.registerFactory("SimpleObject", new SimpleFactory());
        String createdObject = (String) injector.getObject("SimpleObject");
        assertThat(createdObject, is("Simple object created"));
    }

    @Test
    public void registerFactoryWithParameters() throws DependencyException {
        injector.registerConstant("A", "Parameter 1, ");
        injector.registerConstant("B", "Parameter 2, ");
        injector.registerConstant("C", "Parameter 3");
        injector.registerFactory("BigObject", new FactoryWithParameters(),
                "A", "B", "C");
        String object = (String) injector.getObject("BigObject");
        assertThat(object, is("Parameter 1, Parameter 2, Parameter 3"));
    }

    @Test(expected = DependencyException.class)
    public void cannotRegisterFactoryWithSameName() throws DependencyException {
        injector.registerFactory("Factory1", new SimpleFactory());
        injector.registerFactory("Factory1", new SimpleFactory());
    }

    @Test(expected = DependencyException.class)
    public void createObjectWithUnregisteredFactory() throws DependencyException {
        injector.registerConstant("CONSTANT_1", "some value");
        injector.getObject("NOT_REGISTERED_FACTORY");
    }

    @Test(expected = DependencyException.class)
    public void createObjectWithWrongParameters() throws DependencyException {
        injector.registerFactory("D", new FactoryD1());
        InterfaceD d = (InterfaceD) injector.getObject("D");
    }

    @Test(expected = DependencyException.class)
    public void createObjectWithUnregisteredConstant() throws DependencyException {
        injector.registerFactory("BigObject", new FactoryWithParameters(),
                "A", "B", "C");
        String object = (String) injector.getObject("BigObject");
    }

    @Test
    public void createImplementationD1() throws DependencyException {
        InterfaceD d = (InterfaceD) injector.getObject("D1");
        assertThat(d, is(instanceOf(ImplementationD1.class)));
        ImplementationD1 d1 = (ImplementationD1) d;
        assertThat(d1.getI(), is(25));
    }

    @Test
    public void createImplementationC1() throws DependencyException {
        InterfaceC c = (InterfaceC) injector.getObject("C1");
        assertThat(c, is(instanceOf(ImplementationC1.class)));
        ImplementationC1 c1 = (ImplementationC1) c;
        assertThat(c1.getString(), is("Hello"));
    }

    @Test
    public void createImplementationB1() throws DependencyException {
        InterfaceB b = (InterfaceB) injector.getObject("B1");
        assertThat(b, is(instanceOf(ImplementationB1.class)));
        ImplementationB1 b1 = (ImplementationB1) b;
        InterfaceD d = b1.getD();
        assertThat(d, is(instanceOf(ImplementationD1.class)));
        ImplementationD1 d1 = (ImplementationD1) d;
        assertThat(d1.getI(), is(25));
    }

    @Test
    public void createImplementationA1() throws DependencyException {
        InterfaceA a = (InterfaceA) injector.getObject("A1");
        assertThat(a, is(instanceOf(ImplementationA1.class)));

        ImplementationA1 a1 = (ImplementationA1) a;
        InterfaceB b = a1.getB();
        assertThat(b, is(instanceOf(ImplementationB1.class)));

        InterfaceC c = a1.getC();
        assertThat(c, is(instanceOf(ImplementationC1.class)));

        InterfaceD d = ((ImplementationB1) b).getD();
        assertThat(d, is(instanceOf(ImplementationD1.class)));

        int i = ((ImplementationD1) d).getI();
        assertThat(i, is(25));

        String s = ((ImplementationC1) c).getString();
        assertThat(s, is("Hello"));
    }

}

