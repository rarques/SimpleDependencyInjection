package complex;

import common.DependencyException;
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

}