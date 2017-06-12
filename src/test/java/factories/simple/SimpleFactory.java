package factories.simple;

import common.DependencyException;
import simple.Factory;

/**
 * Created by rarques on 6/10/2017.
 */
public class SimpleFactory implements Factory {

    @Override
    public Object create(Object... parameters) throws DependencyException {
        return "Simple object created";
    }
}
