package modelo.materia;

import modelo.ConectarDB;

/**
 *
 * @author Johnny
 */
public class MateriaRequisitosBD extends MateriaRequisitosMD{
    
    private final ConectarDB conecta; 
    private final MateriaBD mat; 

    public MateriaRequisitosBD(ConectarDB conecta) {
        this.conecta = conecta;
        this.mat = new MateriaBD(conecta);
    }
    
    
}
