package modelo.alumno;

import java.util.List;
import modelo.curso.CursoMD;
import modelo.notas.NotasBD;
import modelo.persona.AlumnoMD;

/**
 *
 * @author Johnny
 */
public class AlumnoCursoMD {

    private int id;
    private AlumnoMD alumno;
    private CursoMD curso;
    private String asistencia;
    private double notaFinal;
    private String estado;
    private int numFalta;
    private int totalHoras;
    private int faltas;

    private List<NotasBD> notas;

    public AlumnoCursoMD() {
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

    public CursoMD getCurso() {
        return curso;
    }

    public void setCurso(CursoMD curso) {
        this.curso = curso;
    }

    public String getAsistencia() {
        return asistencia;
    }

    public void setAsistencia(String asistencia) {
        this.asistencia = asistencia;
    }

    public double getNotaFinal() {
        return notaFinal;
    }

    public void setNotaFinal(double notaFinal) {
        this.notaFinal = notaFinal;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getNumFalta() {
        return numFalta;
    }

    public void setNumFalta(int numFalta) {
        this.numFalta = numFalta;
    }

    public int getTotalHoras() {
        return totalHoras;
    }

    public void setTotalHoras(int totalHoras) {
        this.totalHoras = totalHoras;
    }

    public List<NotasBD> getNotas() {
        return notas;
    }

    public void setNotas(List<NotasBD> notas) {
        this.notas = notas;
    }

    public int getFaltas() {
        return faltas;
    }

    public void setFaltas(int faltas) {
        this.faltas = faltas;
    }
    
    @Override
    public String toString() {
        return "AlumnoCursoMD{" + "id=" + id + ", alumno=" + alumno + ", curso=" + curso + ", asistencia=" + asistencia + ", notaFinal=" + notaFinal + ", estado=" + estado + ", numFalta=" + numFalta + ", totalHoras=" + totalHoras + ", notas=" + notas + '}';
    }

}
