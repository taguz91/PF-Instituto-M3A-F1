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

    public MateriaMD setId(int id) {
        this.id = id;
        return this;
    }

    public CarreraMD getCarrera() {
        return carrera;
    }

    public MateriaMD setCarrera(CarreraMD carrera) {
        this.carrera = carrera;
        return this;
    }

    public String getCodigo() {
        return codigo;
    }

    public MateriaMD setCodigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public String getNombre() {
        return nombre;
    }

    public MateriaMD setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public int getCiclo() {
        return ciclo;
    }

    public MateriaMD setCiclo(int ciclo) {
        this.ciclo = ciclo;
        return this;
    }

    public int getCreditos() {
        return creditos;
    }

    public MateriaMD setCreditos(int creditos) {
        this.creditos = creditos;
        return this;
    }

    public char getTipo() {
        return tipo;
    }

    public MateriaMD setTipo(char tipo) {
        this.tipo = tipo;
        return this;
    }

    public String getCategoria() {
        return categoria;
    }

    public MateriaMD setCategoria(String categoria) {
        this.categoria = categoria;
        return this;
    }

    public EjeFormacionMD getEje() {
        return eje;
    }

    public MateriaMD setEje(EjeFormacionMD eje) {
        this.eje = eje;
        return this;
    }

    public char getTipoAcreditacion() {
        return tipoAcreditacion;
    }

    public MateriaMD setTipoAcreditacion(char tipoAcreditacion) {
        this.tipoAcreditacion = tipoAcreditacion;
        return this;
    }

    public int getHorasDocencia() {
        return horasDocencia;
    }

    public MateriaMD setHorasDocencia(int horasDocencia) {
        this.horasDocencia = horasDocencia;
        return this;
    }

    public int getHorasPresenciales() {
        return horasPresenciales;
    }

    public MateriaMD setHorasPresenciales(int horasPresenciales) {
        this.horasPresenciales = horasPresenciales;
        return this;
    }

    public int getHorasPracticas() {
        return horasPracticas;
    }

    public MateriaMD setHorasPracticas(int horasPracticas) {
        this.horasPracticas = horasPracticas;
        return this;
    }

    public int getHorasAutoEstudio() {
        return horasAutoEstudio;
    }

    public MateriaMD setHorasAutoEstudio(int horasAutoEstudio) {
        this.horasAutoEstudio = horasAutoEstudio;
        return this;
    }

    public int getTotalHoras() {
        return totalHoras;
    }

    public MateriaMD setTotalHoras(int totalHoras) {
        this.totalHoras = totalHoras;
        return this;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public MateriaMD setObjetivo(String objetivo) {
        this.objetivo = objetivo;
        return this;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public MateriaMD setDescripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public String getObjetivoespecifico() {
        return objetivoespecifico;
    }

    public MateriaMD setObjetivoespecifico(String objetivoespecifico) {
        this.objetivoespecifico = objetivoespecifico;
        return this;
    }

    public String getOrganizacioncurricular() {
        return organizacioncurricular;
    }

    public MateriaMD setOrganizacioncurricular(String organizacioncurricular) {
        this.organizacioncurricular = organizacioncurricular;
        return this;
    }

    public String getMateriacampoformacion() {
        return materiacampoformacion;
    }

    public MateriaMD setMateriacampoformacion(String materiacampoformacion) {
        this.materiacampoformacion = materiacampoformacion;
        return this;
    }

    public boolean isMateriaNucleo() {
        return materiaNucleo;
    }

    public MateriaMD setMateriaNucleo(boolean materiaNucleo) {
        this.materiaNucleo = materiaNucleo;
        return this;
    }

    public boolean isMateriaActiva() {
        return materiaActiva;
    }

    public MateriaMD setMateriaActiva(boolean materiaActiva) {
        this.materiaActiva = materiaActiva;
        return this;
    }

}
