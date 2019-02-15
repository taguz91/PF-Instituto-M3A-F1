package modelo.materia;

import modelo.carrera.CarreraMD;

/**
 *
 * @author USUARIO
 */
public class MateriaMD {

    private int id;
    private CarreraMD carrera;
    private String codigo;
    private String nombre;
    private int ciclo;
    private int creditos;
    private char tipo;
    private String categoria;
    private String eje;
    private char tipoAcreditacion;
    private int horasTeoricas;
    private int horasPracticas;
    private int horasAutoEstudio;
    private int totalHoras;
    private String objetivo;
    private String descripcion;

    public MateriaMD() {
    }

    public MateriaMD(int id, CarreraMD carrera, String codigo, String nombre, int ciclo, int creditos, char tipo, String categoria, String eje, char tipoAcreditacion, int horasTeoricas, int horasPracticas, int horasAutoEstudio, int totalHoras, String objetivo, String descripcion) {
        this.id = id;
        this.carrera = carrera;
        this.codigo = codigo;
        this.nombre = nombre;
        this.ciclo = ciclo;
        this.creditos = creditos;
        this.tipo = tipo;
        this.categoria = categoria;
        this.eje = eje;
        this.tipoAcreditacion = tipoAcreditacion;
        this.horasTeoricas = horasTeoricas;
        this.horasPracticas = horasPracticas;
        this.horasAutoEstudio = horasAutoEstudio;
        this.totalHoras = totalHoras;
        this.objetivo = objetivo;
        this.descripcion = descripcion;
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

    public int getCiclo() {
        return ciclo;
    }

    public void setCiclo(int ciclo) {
        this.ciclo = ciclo;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    public char getTipo() {
        return tipo;
    }

    public void setTipo(char tipo) {
        this.tipo = tipo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getEje() {
        return eje;
    }

    public void setEje(String eje) {
        this.eje = eje;
    }

    public char getTipoAcreditacion() {
        return tipoAcreditacion;
    }

    public void setTipoAcreditacion(char tipoAcreditacion) {
        this.tipoAcreditacion = tipoAcreditacion;
    }

    public int getHorasTeoricas() {
        return horasTeoricas;
    }

    public void setHorasTeoricas(int horasTeoricas) {
        this.horasTeoricas = horasTeoricas;
    }

    public int getHorasPracticas() {
        return horasPracticas;
    }

    public void setHorasPracticas(int horasPracticas) {
        this.horasPracticas = horasPracticas;
    }

    public int getHorasAutoEstudio() {
        return horasAutoEstudio;
    }

    public void setHorasAutoEstudio(int horasAutoEstudio) {
        this.horasAutoEstudio = horasAutoEstudio;
    }

    public int getTotalHoras() {
        return totalHoras;
    }

    public void setTotalHoras(int totalHoras) {
        this.totalHoras = totalHoras;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
