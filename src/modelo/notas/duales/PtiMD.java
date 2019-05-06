package modelo.notas.duales;

/**
 *
 * @author MrRainx
 */
public class PtiMD {

    private int id;
    private double nota;
    private double totalFaseTeorica;

    public PtiMD(int id, double nota, double totalFaseTeorica) {
        this.id = id;
        this.nota = nota;
        this.totalFaseTeorica = totalFaseTeorica;
    }

    public PtiMD() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public double getTotalFaseTeorica() {
        return totalFaseTeorica;
    }

    public void setTotalFaseTeorica(double totalFaseTeorica) {
        this.totalFaseTeorica = totalFaseTeorica;
    }

    @Override
    public String toString() {
        return "PTI{" + "id=" + id + ", nota=" + nota + ", totalFaseTeorica=" + totalFaseTeorica + '}';
    }
    
}
