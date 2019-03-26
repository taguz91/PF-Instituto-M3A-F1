package modelo.docente;

import modelo.materia.MateriaMD;
import modelo.persona.DocenteMD;

/**
 *
 * @author Johnny
 */
public class DocenteMateriaMD {
    private int id; 
    private DocenteMD docente; 
    private MateriaMD materia; 

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DocenteMD getDocente() {
        return docente;
    }

    public void setDocente(DocenteMD docente) {
        this.docente = docente;
    }

    public MateriaMD getMateria() {
        return materia;
    }

    public void setMateria(MateriaMD materia) {
        this.materia = materia;
    }
}
