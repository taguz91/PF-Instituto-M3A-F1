package modelo.materia;

import modelo.carrera.CarreraMD;

/**
 *
 * @author Johnny
 */
public class EjeFormacionMD {
    private int id; 
    private CarreraMD carrera; 
    private String codigo; 
    private String nombre; 

    public EjeFormacionMD() {
    }

    public EjeFormacionMD(int id, CarreraMD carrera, String codigo, String nombre) {
        this.id = id;
        this.carrera = carrera;
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CarreraMD getCarrera() {
        return carrera;
    }

    public void setCarrera(CarreraMD carrera) {
        this.carrera = carrera;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
}
