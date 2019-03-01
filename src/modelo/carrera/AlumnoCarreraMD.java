package modelo.carrera;

import modelo.persona.AlumnoMD;

/**
 *
 * @author Johnny
 */
public class AlumnoCarreraMD {
    private int id; 
    private AlumnoMD alumno;  
    private CarreraMD carrera; 

    public AlumnoCarreraMD() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AlumnoMD getAlumno() {
        return alumno;
    }

    public void setAlumno(AlumnoMD alumno) {
        this.alumno = alumno;
    }

    public CarreraMD getCarrera() {
        return carrera;
    }

    public void setCarrera(CarreraMD carrera) {
        this.carrera = carrera;
    }
    
    
}
