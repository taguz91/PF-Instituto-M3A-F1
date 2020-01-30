package modelo.curso;

import modelo.jornada.JornadaMD;
import modelo.materia.MateriaMD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.persona.DocenteMD;

/**
 *
 * @author arman
 */
public class CursoMD {

    private int id;
    private MateriaMD materia;
    private PeriodoLectivoMD periodo;
    private DocenteMD docente;
    private String nombre;
    private JornadaMD jornada;
    private int capacidad;
    private int ciclo;
    private String paralelo;
    private int numMatriculados;
    private boolean activo;
    //Solo para matriculas 
    private int numMatricula;

    public CursoMD() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id_curso) {
        this.id = id_curso;
    }

    public MateriaMD getMateria() {
        return materia;
    }

    public void setMateria(MateriaMD id_materia) {
        this.materia = id_materia;
    }

    public PeriodoLectivoMD getPeriodo() {
        return periodo;
    }

    public void setPeriodo(PeriodoLectivoMD periodo) {
        this.periodo = periodo;
    }

    public DocenteMD getDocente() {
        return docente;
    }

    public void setDocente(DocenteMD docente) {
        this.docente = docente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String curso_nombre) {
        this.nombre = curso_nombre;
    }

    public JornadaMD getJornada() {
        return jornada;
    }

    public void setJornada(JornadaMD jornada) {
        this.jornada = jornada;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public int getCiclo() {
        return ciclo;
    }

    public void setCiclo(int ciclo) {
        this.ciclo = ciclo;
    }

    public String getParalelo() {
        return paralelo;
    }

    public void setParalelo(String paralelo) {
        this.paralelo = paralelo;
    }

    public int getNumMatriculados() {
        return numMatriculados;
    }

    public void setNumMatriculados(int numMatriculados) {
        this.numMatriculados = numMatriculados;
    }

    public int getCapaciadActual() {
        return capacidad - numMatriculados;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    /**
     * Esto unicamente se usa en matriculas
     *
     * @param numMatricula
     */
    public void setNumMatricula(int numMatricula) {
        this.numMatricula = numMatricula;
    }

    /**
     * Solo se usa en numero de matriculas
     *
     * @return
     */
    public int getNumMatricula() {
        return numMatricula;
    }

    public String getCursoMateriaNombre() {
        return String.format(
                "%s | %s",
                this.nombre,
                this.materia.getNombre()
        );
    }

}
