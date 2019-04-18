package modelo.materia;

import modelo.carrera.CarreraMD;

/**
 *
 * @author USUARIO
 */
public class MateriaMD {

    private int id;
    private CarreraMD carrera;
    private EjeFormacionMD eje; 
    private String codigo;
    private String nombre;
    private int ciclo;
    private int creditos;
    private char tipo;
    private String categoria;
    private char tipoAcreditacion;
    private int horasDocencia;
    private int horasPracticas;
    private int horasAutoEstudio;
    private int horasPresenciales; 
    private int totalHoras;
    private String objetivo;
    private String descripcion;
    private String objetivoespecifico;
    private String organizacioncurricular;
    private String materiacampoformacion;
    private boolean materiaNucleo, materiaActiva;
    public MateriaMD() {
    }

    public MateriaMD(int id, CarreraMD carrera, EjeFormacionMD eje, String codigo, String nombre, int ciclo, int creditos, char tipo, String categoria, char tipoAcreditacion, int horasDocencia, int horasPracticas, int horasAutoEstudio, int horasPresenciales, int totalHoras, String objetivo, String descripcion, String objetivoespecifico, String organizacioncurricular, String materiacampoformacion, boolean materiaNucleo, boolean materiaActiva) {
        this.id = id;
        this.carrera = carrera;
        this.eje = eje;
        this.codigo = codigo;
        this.nombre = nombre;
        this.ciclo = ciclo;
        this.creditos = creditos;
        this.tipo = tipo;
        this.categoria = categoria;
        this.tipoAcreditacion = tipoAcreditacion;
        this.horasDocencia = horasDocencia;
        this.horasPracticas = horasPracticas;
        this.horasAutoEstudio = horasAutoEstudio;
        this.horasPresenciales = horasPresenciales;
        this.totalHoras = totalHoras;
        this.objetivo = objetivo;
        this.descripcion = descripcion;
        this.objetivoespecifico = objetivoespecifico;
        this.organizacioncurricular = organizacioncurricular;
        this.materiacampoformacion = materiacampoformacion;
        this.materiaActiva = materiaActiva;
        this.materiaNucleo = materiaNucleo;
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

    public EjeFormacionMD getEje() {
        return eje;
    }

    public void setEje(EjeFormacionMD eje) {
        this.eje = eje;
    }

    public char getTipoAcreditacion() {
        return tipoAcreditacion;
    }

    public void setTipoAcreditacion(char tipoAcreditacion) {
        this.tipoAcreditacion = tipoAcreditacion;
    }

    public int getHorasDocencia() {
        return horasDocencia;
    }

    public void setHorasDocencia(int horasDocencia) {
        this.horasDocencia = horasDocencia;
    }

    public int getHorasPresenciales() {
        return horasPresenciales;
    }

    public void setHorasPresenciales(int horasPresenciales) {
        this.horasPresenciales = horasPresenciales;
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

    public String getObjetivoespecifico() {
        return objetivoespecifico;
    }

    public void setObjetivoespecifico(String objetivoespecifico) {
        this.objetivoespecifico = objetivoespecifico;
    }

    public String getOrganizacioncurricular() {
        return organizacioncurricular;
    }

    public void setOrganizacioncurricular(String organizacioncurricular) {
        this.organizacioncurricular = organizacioncurricular;
    }

    public String getMateriacampoformacion() {
        return materiacampoformacion;
    }

    public void setMateriacampoformacion(String materiacampoformacion) {
        this.materiacampoformacion = materiacampoformacion;
    }
    
    
}
