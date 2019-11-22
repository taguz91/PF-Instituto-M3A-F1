package modelo.evaluacionSilabo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;
import modelo.seguimientoSilabo.SeguimientoEvaluacionMD;
import modelo.tipoActividad.TipoActividadMD;
import modelo.unidadSilabo.UnidadSilaboMD;

/**
 *
 * @author Andres Ullauri
 */
public class EvaluacionSilaboMD implements Serializable {

    private final int idLocal = UUID.randomUUID().hashCode();

    private String indicador;

    private Integer idEvaluacion = 0;

    private String instrumento;

    private double valoracion;

    private LocalDate fechaEnvio;

    private LocalDate fechaPresentacion;

    private TipoActividadMD idTipoActividad;

    private UnidadSilaboMD idUnidad;

    private SeguimientoEvaluacionMD seguimientoEvaluacion;

    public EvaluacionSilaboMD() {

        this.idTipoActividad = new TipoActividadMD();
        this.idUnidad = new UnidadSilaboMD();
    }

    public EvaluacionSilaboMD(Integer idEvaluacion, String indicador, String instrumento, double valoracion, LocalDate fechaEnvio, LocalDate fechaPresentacion, TipoActividadMD idTipoActividad, UnidadSilaboMD idUnidad) {
        this.idEvaluacion = idEvaluacion;
        this.indicador = indicador;
        this.instrumento = instrumento;
        this.valoracion = valoracion;
        this.fechaEnvio = fechaEnvio;
        this.fechaPresentacion = fechaPresentacion;
        this.idTipoActividad = idTipoActividad;
        this.idUnidad = idUnidad;
    }

    public String getIndicador() {
        return indicador;
    }

    public void setIndicador(String indicador) {
        this.indicador = indicador;
    }

    public Integer getIdEvaluacion() {
        return idEvaluacion;
    }

    public void setIdEvaluacion(Integer idEvaluacion) {
        this.idEvaluacion = idEvaluacion;
    }

    public String getInstrumento() {
        return instrumento;
    }

    public void setInstrumento(String instrumento) {
        this.instrumento = instrumento;
    }

    public double getValoracion() {
        return valoracion;
    }

    public void setValoracion(double valoracion) {
        this.valoracion = valoracion;
    }

    public LocalDate getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(LocalDate fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public LocalDate getFechaPresentacion() {
        return fechaPresentacion;
    }

    public void setFechaPresentacion(LocalDate fechaPresentacion) {
        this.fechaPresentacion = fechaPresentacion;
    }

    public TipoActividadMD getIdTipoActividad() {
        return idTipoActividad;
    }

    public void setIdTipoActividad(TipoActividadMD idTipoActividad) {
        this.idTipoActividad = idTipoActividad;
    }

    public UnidadSilaboMD getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(UnidadSilaboMD idUnidad) {
        this.idUnidad = idUnidad;
    }

    public int getIdLocal() {
        return idLocal;
    }

    public SeguimientoEvaluacionMD getSeguimientoEvaluacion() {
        return seguimientoEvaluacion;
    }

    public void setSeguimientoEvaluacion(SeguimientoEvaluacionMD seguimientoEvaluacion) {
        this.seguimientoEvaluacion = seguimientoEvaluacion;
    }

    public String descripcionTextArea() {
        return "Informacion de la Actividad:\n"
                + " -Indicador: " + indicador + "\n"
                + " -Instrumento: " + instrumento + "\n"
                + " -Valoracion: " + valoracion + "\n"
                + " -Fecha de envio: " + fechaEnvio + "\n"
                + " -Fecha de entrega: " + fechaPresentacion + "\n"
                + "Tipo de Actividad: \n"
                + " -Tipo: " + idTipoActividad.getNombreTipoActividad() + "\n"
                + " -SubTipo: " + idTipoActividad.getNombreSubtipoActividad() + "\n"
                + "";
    }

    @Override
    public String toString() {
        return "EvaluacionSilaboMD{" + "idLocal=" + idLocal + ", indicador=" + indicador + ", idEvaluacion=" + idEvaluacion + ", instrumento=" + instrumento + ", valoracion=" + valoracion + ", fechaEnvio=" + fechaEnvio + ", fechaPresentacion=" + fechaPresentacion + ", idTipoActividad=" + idTipoActividad + ", idUnidad=" + idUnidad + '}';
    }

}
