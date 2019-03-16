
package modelo.notas_Grupo_16;

import modelo.ConectarDB;
import modelo.carrera.CarreraBD;
import modelo.carrera.CarreraMD;
import vista.notas_Grupo_16.VtnNotasAlumnoCurso;

/**
 *
 * @author Alejandro
 */


public class VtnNotasAlumnoCursoBD {
    
    private final ConectarDB conecta;
    private final CarreraBD car;
    private VtnNotasAlumnoCurso Nac;

    public VtnNotasAlumnoCursoBD(ConectarDB conecta, CarreraBD car, int id_PerioLectivo, String nombre_PerLectivo, String observacion_PerLectivo, boolean activo_PerLectivo, int Carreraid, CarreraMD carrera, String Carreracodigo, String Carreranombre, int Materiaid, String Materianombre) {
        this.conecta = conecta;
        this.car = car;
    }
   
}
