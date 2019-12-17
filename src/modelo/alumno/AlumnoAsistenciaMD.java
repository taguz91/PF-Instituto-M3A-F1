
package modelo.alumno;

import java.util.List;
import modelo.asistencia.AsistenciaBD;
import modelo.curso.CursoMD;
import modelo.persona.AlumnoMD;

/**
 *
 * @author Lushito
 */
public class AlumnoAsistenciaMD {
    
    private int id;
    private AlumnoMD alumno;
    private CursoMD curso;
    private String fecha;
    private int numFaltas;
    
    private List<AsistenciaBD> faltas;

    public AlumnoAsistenciaMD(int id, AlumnoMD alumno, CursoMD curso, String fecha, int numFaltas, List<AsistenciaBD> faltas) {
        this.id = id;
        this.alumno = alumno;
        this.curso = curso;
        this.fecha = fecha;
        this.numFaltas = numFaltas;
        this.faltas = faltas;
    }

    public AlumnoAsistenciaMD() {
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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getNumFaltas() {
        return numFaltas;
    }

    public void setNumFaltas(int numFaltas) {
        this.numFaltas = numFaltas;
    }

    public List<AsistenciaBD> getFaltas() {
        return faltas;
    }

    public void setFaltas(List<AsistenciaBD> faltas) {
        this.faltas = faltas;
    }

    @Override
    public String toString() {
        return "AlumnoAsistenciaMD{" + "id=" + id + ", alumno=" + alumno + ", curso=" + curso + ",             fecha=" + fecha + ", numFaltas=" + numFaltas + ", faltas=" + faltas + '}';
    }    
}