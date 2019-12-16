package utils;

import modelo.ConnDBPool;

/**
 *
 * @author gus
 */
public abstract class CONBD {

    protected static final ConnDBPool CON = ConnDBPool.single();

}
