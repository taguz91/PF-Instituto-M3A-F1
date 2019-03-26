
package vista.precorequisitos;

import java.util.Objects;
import modelo.materia.MateriaMD;

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
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + this.id;
        hash = 71 * hash + Objects.hashCode(this.materia);
        hash = 71 * hash + this.tipo;
        hash = 71 * hash + Objects.hashCode(this.materiaRequisito);
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
        final PreCoRequisitos other = (PreCoRequisitos) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.materia, other.materia)) {
            return false;
        }
        if (this.tipo != other.tipo) {
            return false;
        }
        if (!Objects.equals(this.materiaRequisito, other.materiaRequisito)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PreCoRequisitos{" + "id=" + id + ", materia=" + materia + ", tipo=" + tipo + ", materiaRequisito=" + materiaRequisito + '}';
    }
    
    
}
