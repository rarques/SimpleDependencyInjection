package factories;

import common.DependencyException;
import simple.Factory;

/**
 * Created by rarques on 6/10/2017.
 */
public class FactoryWithParameters implements Factory {

    @Override
    public Object create(Object... parameters) throws DependencyException {
        StringBuilder strb = new StringBuilder();
        for (Object o : parameters) {
            strb.append((String) o);
        }
        return strb.toString();
    }
}