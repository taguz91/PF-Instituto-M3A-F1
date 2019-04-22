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
    private double nota1Parcial;
    private double notaExamenInter;
    private double nota2Parcial;
    private double notaExamenFinal;
    private double notaExamenSupletorio;
    private String asistencia;
    private double notaFinal;
    private String estado;
    private int numFalta;
    private int totalHoras;

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

    public double getNota1Parcial() {
        return nota1Parcial;
    }

    public void setNota1Parcial(double nota1Parcial) {
        this.nota1Parcial = nota1Parcial;
    }

    public double getNotaExamenInter() {
        return notaExamenInter;
    }

    public void setNotaExamenInter(double notaExamenInter) {
        this.notaExamenInter = notaExamenInter;
    }

    public double getNota2Parcial() {
        return nota2Parcial;
    }

    public void setNota2Parcial(double nota2Parcial) {
        this.nota2Parcial = nota2Parcial;
    }

    public double getNotaExamenFinal() {
        return notaExamenFinal;
    }

    public void setNotaExamenFinal(double notaExamenFinal) {
        this.notaExamenFinal = notaExamenFinal;
    }

    public double getNotaExamenSupletorio() {
        return notaExamenSupletorio;
    }

    public void setNotaExamenSupletorio(double notaExamenSupletorio) {
        this.notaExamenSupletorio = notaExamenSupletorio;
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

    @Override
    public String toString() {
        return "AlumnoCursoMD{" + "id=" + id + ", alumno=" + alumno + ", curso=" + curso + ", nota1Parcial=" + nota1Parcial + ", notaExamenInter=" + notaExamenInter + ", nota2Parcial=" + nota2Parcial + ", notaExamenFinal=" + notaExamenFinal + ", notaExamenSupletorio=" + notaExamenSupletorio + ", asistencia=" + asistencia + ", notaFinal=" + notaFinal + ", estado=" + estado + ", numFalta=" + numFalta + ", totalHoras=" + totalHoras + ", notas=" + notas + '}';
    }

}
