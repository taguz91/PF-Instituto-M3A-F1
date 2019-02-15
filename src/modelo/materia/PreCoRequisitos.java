
package modelo.materia;

import java.util.Objects;

/**
 *
 * @author arman
 */
public class PreCoRequisitos {
    private int id;
    private MateriaMD materia;
    private char tipo;
    private MateriaMD materiaRequisito;

    public PreCoRequisitos(int id, MateriaMD materia, char tipo, MateriaMD materiaRequisito) {
        this.id = id;
        this.materia = materia;
        this.tipo = tipo;
        this.materiaRequisito = materiaRequisito;
    }
    
    public PreCoRequisitos(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MateriaMD getMateria() {
        return materia;
    }

    public void setMateria(MateriaMD materia) {
        this.materia = materia;
    }

    public char getTipo() {
        return tipo;
    }

    public void setTipo(char tipo) {
        this.tipo = tipo;
    }

    public MateriaMD getMateriaRequisito() {
        return materiaRequisito;
    }

    public void setMateriaRequisito(MateriaMD materiaRequisito) {
        this.materiaRequisito = materiaRequisito;
    }

    @Override
    public String toString() {
        return "PreCoRequisitos{" + "id=" + id + ", materia=" + materia + ", tipo=" + tipo + ", materiaRequisito=" + materiaRequisito + '}';
    }
    
    
}
