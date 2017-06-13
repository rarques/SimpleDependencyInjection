package factories.complex;

import common.DependencyException;
import complex.Factory;

/**
 * Created by rarques on 6/10/2017.
 */
public class SimpleFactory implements Factory<String> {

    @Override
    public String create(Object... parameters) throws DependencyException {
        return "Hello from factory";
    }

}
