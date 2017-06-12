package complex;

import common.DependencyException;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by rarques on 6/11/2017.
 */
public class ContainerTest {

    @Test
    public void registerConstant() throws DependencyException {
        Injector injector = new Container();
        injector.registerConstant(String.class, "Hello");
        String s = injector.getObject(String.class);
        assertThat(s, is("Hello"));
    }

}