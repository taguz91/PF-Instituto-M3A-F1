package utils;

/**
 *
 * @author gus
 */
public class FRMCTR {
    
    public static FRMCTR FCTR; 
    
    public static FRMCTR single(){
        if (FCTR == null){
            FCTR = new FRMCTR();
        }
        return FCTR;
    }
}
