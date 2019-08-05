package modelo.tipoDeNota;

import java.time.LocalDate;
import modelo.curso.CursoBD;

/**
 *
 * @author MrRainx
 */
public class IngresoNotasMD {

    private int id;
    private LocalDate fechaInicio;
    private LocalDate fechaCierre;
    private LocalDate fechaCierreExtendido;
    private boolean estado;
    private TipoDeNotaBD tipoNota;
    private CursoBD curso;

    public IngresoNotasMD(int id, LocalDate fechaInicio, LocalDate fechaCierre, LocalDate fechaCierreExtendido, boolean estado, TipoDeNotaBD tipoNota, CursoBD curso) {
        this.id = id;
        this.fechaInicio = fechaInicio;
        this.fechaCierre = fechaCierre;
        this.fechaCierreExtendido = fechaCierreExtendido;
        this.estado = estado;
        this.tipoNota = tipoNota;
        this.curso = curso;
    }

    public IngresoNotasMD() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(LocalDate fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public LocalDate getFechaCierreExtendido() {
        return fechaCierreExtendido;
    }

    public void setFechaCierreExtendido(LocalDate fechaCierreExtendido) {
        this.fechaCierreExtendido = fechaCierreExtendido;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public TipoDeNotaBD getTipoNota() {
        return tipoNota;
    }

    public void setTipoNota(TipoDeNotaBD tipoNota) {
        this.tipoNota = tipoNota;
    }

    public CursoBD getCurso() {
        return curso;
    }

    public void setCurso(CursoBD curso) {
        this.curso = curso;
    }

    @Override
    public String toString() {
        return "IngresoNotasMD{" + "id=" + id + ", fechaInicio=" + fechaInicio + ", fechaCierre=" + fechaCierre + ", fechaCierreExtendido=" + fechaCierreExtendido + ", estado=" + estado + ", tipoNota=" + tipoNota + ", curso=" + curso + '}';
    }

}
