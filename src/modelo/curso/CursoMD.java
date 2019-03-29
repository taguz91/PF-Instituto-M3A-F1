package modelo.curso;

import java.util.Objects;
import modelo.jornada.JornadaMD;
import modelo.materia.MateriaMD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.persona.DocenteMD;

/**
 *
 * @author arman
 */
public class CursoMD {

    private int id_curso;
    private MateriaMD id_materia;
    private PeriodoLectivoMD id_prd_lectivo;
    private DocenteMD id_docente;
    private String curso_nombre;
    private JornadaMD curso_jornada;
    private int curso_capacidad;
    private int curso_ciclo;
    private String paralelo; 

    public CursoMD() {
    }

    public int getId_curso() {
        return id_curso;
    }

    public void setId_curso(int id_curso) {
        this.id_curso = id_curso;
    }

    public MateriaMD getId_materia() {
        return id_materia;
    }

    public void setId_materia(MateriaMD id_materia) {
        this.id_materia = id_materia;
    }

    public PeriodoLectivoMD getId_prd_lectivo() {
        return id_prd_lectivo;
    }

    public void setId_prd_lectivo(PeriodoLectivoMD id_prd_lectivo) {
        this.id_prd_lectivo = id_prd_lectivo;
    }

    public DocenteMD getId_docente() {
        return id_docente;
    }

    public void setId_docente(DocenteMD id_docente) {
        this.id_docente = id_docente;
    }

    public String getCurso_nombre() {
        return curso_nombre;
    }

    public void setCurso_nombre(String curso_nombre) {
        this.curso_nombre = curso_nombre;
    }

    public JornadaMD getCurso_jornada() {
        return curso_jornada;
    }

    public void setCurso_jornada(JornadaMD curso_jornada) {
        this.curso_jornada = curso_jornada;
    }

    public int getCurso_capacidad() {
        return curso_capacidad;
    }

    public void setCurso_capacidad(int curso_capacidad) {
        this.curso_capacidad = curso_capacidad;
    }

    public int getCurso_ciclo() {
        return curso_ciclo;
    }

    public void setCurso_ciclo(int curso_ciclo) {
        this.curso_ciclo = curso_ciclo;
    }

    public String getParalelo() {
        return paralelo;
    }

    public void setParalelo(String paralelo) {
        this.paralelo = paralelo;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.id_curso;
        hash = 97 * hash + Objects.hashCode(this.id_materia);
        hash = 97 * hash + Objects.hashCode(this.id_prd_lectivo);
        hash = 97 * hash + Objects.hashCode(this.id_docente);
        hash = 97 * hash + Objects.hashCode(this.curso_nombre);
        hash = 97 * hash + Objects.hashCode(this.curso_jornada);
        hash = 97 * hash + this.curso_capacidad;
        hash = 97 * hash + this.curso_ciclo;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CursoMD other = (CursoMD) obj;
        if (this.id_curso != other.id_curso) {
            return false;
        }
        if (!Objects.equals(this.id_materia, other.id_materia)) {
            return false;
        }
        if (!Objects.equals(this.id_prd_lectivo, other.id_prd_lectivo)) {
            return false;
        }
        if (!Objects.equals(this.id_docente, other.id_docente)) {
            return false;
        }
        if (!Objects.equals(this.curso_nombre, other.curso_nombre)) {
            return false;
        }
        if (!Objects.equals(this.curso_jornada, other.curso_jornada)) {
            return false;
        }
        if (this.curso_capacidad != other.curso_capacidad) {
            return false;
        }
        if (this.curso_ciclo != other.curso_ciclo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Curso{" + "id_curso=" + id_curso + ", id_materia=" + id_materia + ", id_prd_lectivo=" + id_prd_lectivo + ", id_docente=" + id_docente + ", curso_nombre=" + curso_nombre + ", curso_jornada=" + curso_jornada + ", curso_capacidad=" + curso_capacidad + ", curso_ciclo=" + curso_ciclo + '}';
    }

}
