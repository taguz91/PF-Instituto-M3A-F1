package modelo.notas;

import modelo.alumno.AlumnoCursoMD;
import modelo.tipoDeNota.TipoDeNotaMD;

/**
 *
 * @author MrRainx
 */
public class NotasMD {

    private int idNota;
    private double notaValor;
    private AlumnoCursoMD alumnoCurso;
    private TipoDeNotaMD tipoDeNota;

    public NotasMD(int idNota, double notaValor, AlumnoCursoMD alumnoCurso, TipoDeNotaMD tipoDeNota) {
        this.idNota = idNota;
        this.notaValor = notaValor;
        this.alumnoCurso = alumnoCurso;
        this.tipoDeNota = tipoDeNota;
    }

    public NotasMD() {
    }

    public int getIdNota() {
        return idNota;
    }

    public void setIdNota(int idNota) {
        this.idNota = idNota;
    }

    public double getNotaValor() {
        return notaValor;
    }

    public void setNotaValor(double notaValor) {
        this.notaValor = notaValor;
    }

    public AlumnoCursoMD getAlumnoCurso() {
        return alumnoCurso;
    }

    public void setAlumnoCurso(AlumnoCursoMD alumnoCurso) {
        this.alumnoCurso = alumnoCurso;
    }

    public TipoDeNotaMD getTipoDeNota() {
        return tipoDeNota;
    }

    public void setTipoDeNota(TipoDeNotaMD tipoDeNota) {
        this.tipoDeNota = tipoDeNota;
    }

    @Override
    public String toString() {
        return "NotasMD{" + "idNota=" + idNota + ", notaValor=" + notaValor + ", alumnoCurso=" + alumnoCurso + ", tipoDeNota=" + tipoDeNota + '}';
    }

}
