package modelo.persona;
import java.time.LocalDate;
/**
 *
 * @author ana96
 */

public class DocenteMD {
    private String codigo,docenteTipoTiempo;
    private int docenteCategoria,idDocente;
    private boolean docenteCapacitador, docenteActivo,docenteOtroTrabajo;
    private LocalDate fechaInicioContratacion,fechaFinContratacion;

    public DocenteMD() {
    }

    public DocenteMD(String codigo, String docenteTipoTiempo, int docenteCategoria, int idDocente, boolean docenteCapacitador, boolean docenteActivo, boolean docenteOtroTrabajo, LocalDate fechaInicioContratacion, LocalDate fechaFinContratacion) {
        this.codigo = codigo;
        this.docenteTipoTiempo = docenteTipoTiempo;
        this.docenteCategoria = docenteCategoria;
        this.idDocente = idDocente;
        this.docenteCapacitador = docenteCapacitador;
        this.docenteActivo = docenteActivo;
        this.docenteOtroTrabajo = docenteOtroTrabajo;
        this.fechaInicioContratacion = fechaInicioContratacion;
        this.fechaFinContratacion = fechaFinContratacion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDocenteTipoTiempo() {
        return docenteTipoTiempo;
    }

    public void setDocenteTipoTiempo(String docenteTipoTiempo) {
        this.docenteTipoTiempo = docenteTipoTiempo;
    }

    public int getDocenteCategoria() {
        return docenteCategoria;
    }

    public void setDocenteCategoria(int docenteCategoria) {
        this.docenteCategoria = docenteCategoria;
    }

    public int getIdDocente() {
        return idDocente;
    }

    public void setIdDocente(int idDocente) {
        this.idDocente = idDocente;
    }

    public boolean isDocenteCapacitador() {
        return docenteCapacitador;
    }

    public void setDocenteCapacitador(boolean docenteCapacitador) {
        this.docenteCapacitador = docenteCapacitador;
    }

    public boolean isDocenteActivo() {
        return docenteActivo;
    }

    public void setDocenteActivo(boolean docenteActivo) {
        this.docenteActivo = docenteActivo;
    }

    public boolean isDocenteOtroTrabajo() {
        return docenteOtroTrabajo;
    }

    public void setDocenteOtroTrabajo(boolean docenteOtroTrabajo) {
        this.docenteOtroTrabajo = docenteOtroTrabajo;
    }

    public LocalDate getFechaInicioContratacion() {
        return fechaInicioContratacion;
    }

    public void setFechaInicioContratacion(LocalDate fechaInicioContratacion) {
        this.fechaInicioContratacion = fechaInicioContratacion;
    }

    public LocalDate getFechaFinContratacion() {
        return fechaFinContratacion;
    }

    public void setFechaFinContratacion(LocalDate fechaFinContratacion) {
        this.fechaFinContratacion = fechaFinContratacion;
    }
        
}