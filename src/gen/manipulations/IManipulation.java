package gen.manipulations;

import gen.Population;

/**
 * Created by hugo on 12/03/15.
 */
public interface IManipulation {

    public void doManipulation( final Population current, Population novel );

}
