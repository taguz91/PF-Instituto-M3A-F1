package utils;

import modelo.ConnDBPool;

/**
 *
 * @author gus
 */
public class CONBD {
    
    protected final ConnDBPool CON = ConnDBPool.single();
    
}
