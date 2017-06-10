package simple;

import common.DependencyException;
import factories.*;
import implementations.ImplementationB1;
import implementations.ImplementationC1;
import implementations.ImplementationD1;
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

    @Test(expected = DependencyException.class)
    public void requestNotRegisteredConstant() throws DependencyException {
        injector.getObject("NOT_REGISTERED_CONSTANT");
    }

    @Test
    public void registerFactory() throws DependencyException {
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

    @Test
    public void createObjectWithRegisteredDependencies() throws DependencyException {
        injector.registerConstant("I", 42);
        injector.registerFactory("D", new FactoryD1(), "I");
        InterfaceD d = (InterfaceD) injector.getObject("D");
        assertThat(d, is(instanceOf(ImplementationD1.class)));
        ImplementationD1 d1 = (ImplementationD1) d;
        assertThat(d1.getI(), is(42));
    }

    @Test(expected = DependencyException.class)
    public void createObjectWithWrongParameters() throws DependencyException {
        injector.registerFactory("D", new FactoryD1());
        InterfaceD d = (InterfaceD) injector.getObject("D");
    }

    @Test(expected = DependencyException.class)
    public void createObjectWithUnregisteredConstant() throws DependencyException {
        injector.registerFactory("D", new FactoryD1(), "I");
        InterfaceD d = (InterfaceD) injector.getObject("D");
        assertThat(d, is(instanceOf(ImplementationD1.class)));
    }

    @Test
    public void createImplementationC1() throws DependencyException {
        injector.registerConstant("S", "Hello");
        injector.registerFactory("C1", new FactoryC1(), "S");
        InterfaceC c = (InterfaceC) injector.getObject("C1");
        assertThat(c, is(instanceOf(ImplementationC1.class)));
        ImplementationC1 c1 = (ImplementationC1) c;
        assertThat(c1.getString(), is("Hello"));
    }

    @Test
    public void createImplementationB1() throws DependencyException {
        injector.registerFactory("B1", new FactoryB1(), "D1");
        injector.registerConstant("I", 25);
        injector.registerFactory("D1", new FactoryD1(), "I");
        InterfaceB b = (InterfaceB) injector.getObject("B1");
        assertThat(b, is(instanceOf(ImplementationB1.class)));
        ImplementationB1 b1 = (ImplementationB1) b;
        InterfaceD d = b1.getD();
        assertThat(d, is(instanceOf(ImplementationD1.class)));
        ImplementationD1 d1 = (ImplementationD1) d;
        assertThat(d1.getI(), is(25));
    }

}

