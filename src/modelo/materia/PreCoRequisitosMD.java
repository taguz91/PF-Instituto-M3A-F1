
package modelo.materia;

/**
 *
 * @author arman
 */
public class PreCoRequisitosMD {
    private int id;
    private MateriaMD materia;
    private char tipo;
    private MateriaMD materiaRequisito;

    public PreCoRequisitosMD(int id, MateriaMD materia, char tipo, MateriaMD materiaRequisito) {
        this.id = id;
        this.materia = materia;
        this.tipo = tipo;
        this.materiaRequisito = materiaRequisito;
    }
    
    public PreCoRequisitosMD(){}

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
