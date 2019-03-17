
package modelo.notas_Grupo_16;

import modelo.ConectarDB;

import vista.notas_Grupo_16.VtnNotasAlumnoCurso;

/**
 *
 * @author Alejandro
 */
public class VtnNotasAlumnoCursoBD {

    private final ConectarDB conecta;

    public VtnNotasAlumnoCursoBD(ConectarDB conecta, VtnNotasAlumnoCurso Nac) {
        this.conecta = conecta;
        this.Nac = Nac;
    }
   
    private VtnNotasAlumnoCurso Nac;

}
   

