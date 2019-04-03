package modelo.persona;
import java.awt.Image;
import java.time.LocalDate;
import modelo.lugar.LugarBD;
import modelo.lugar.LugarMD;
/**
 *
 * @author ana96
 */

public class DocenteMD extends PersonaMD{
    private String codigo,docenteTipoTiempo, estado, tituloDocente, abreviaturaDocente,tipoIdenticacion;
    private int docenteCategoria,idDocente;
    private boolean docenteOtroTrabajo, docenteCapacitador;
    private LocalDate fechaInicioContratacion,fechaFinContratacion;

    public DocenteMD() {
    }

    public DocenteMD(String codigo, String docenteTipoTiempo, String estado, String tituloDocente, String abreviaturaDocente, String tipoIdenticacion, int docenteCategoria, int idDocente, boolean docenteOtroTrabajo, boolean docenteCapacitador, LocalDate fechaInicioContratacion, LocalDate fechaFinContratacion) {
        this.codigo = codigo;
        this.docenteTipoTiempo = docenteTipoTiempo;
        this.estado = estado;
        this.tituloDocente = tituloDocente;
        this.abreviaturaDocente = abreviaturaDocente;
        this.tipoIdenticacion = tipoIdenticacion;
        this.docenteCategoria = docenteCategoria;
        this.idDocente = idDocente;
        this.docenteOtroTrabajo = docenteOtroTrabajo;
        this.docenteCapacitador = docenteCapacitador;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public boolean isDocenteCapacitador() {
        return docenteCapacitador;
    }

    public void setDocenteCapacitador(boolean docenteCapacitador) {
        this.docenteCapacitador = docenteCapacitador;
    }

    public String getTituloDocente() {
        return tituloDocente;
    }

    public void setTituloDocente(String tituloDocente) {
        this.tituloDocente = tituloDocente;
    }

    public String getAbreviaturaDocente() {
        return abreviaturaDocente;
    }

    public void setAbreviaturaDocente(String abreviaturaDocente) {
        this.abreviaturaDocente = abreviaturaDocente;
    }

    public String getTipoIdenticacion() {
        return tipoIdenticacion;
    }

    public void setTipoIdenticacion(String tipoIdenticacion) {
        this.tipoIdenticacion = tipoIdenticacion;
    }
        
}