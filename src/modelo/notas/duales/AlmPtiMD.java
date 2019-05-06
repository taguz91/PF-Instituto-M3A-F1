package modelo.notas.duales;

import modelo.alumno.AlumnoCursoMD;

/**
 *
 * @author MrRainx
 */
public class AlmPtiMD {

    private int id;
    private AlumnoCursoMD alumnoCurso;
    private PtiMD pti;

    public AlmPtiMD(int id, AlumnoCursoMD alumnoCurso, PtiMD pti) {
        this.id = id;
        this.alumnoCurso = alumnoCurso;
        this.pti = pti;
    }

    public AlmPtiMD() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AlumnoCursoMD getAlumnoCurso() {
        return alumnoCurso;
    }

    public void setAlumnoCurso(AlumnoCursoMD alumnoCurso) {
        this.alumnoCurso = alumnoCurso;
    }

    public PtiMD getPti() {
        return pti;
    }

    public void setPti(PtiMD pti) {
        this.pti = pti;
    }

    @Override
    public String toString() {
        return "AlmPtiMD{" + "id=" + id + ", alumnoCurso=" + alumnoCurso + ", pti=" + pti + '}';
    }

}
