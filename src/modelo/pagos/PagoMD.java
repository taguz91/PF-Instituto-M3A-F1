package modelo.pagos;

import modelo.alumno.MallaAlumnoMD;

/**
 *
 * @author MrRainx
 */
public class PagoMD {

    private Integer id;
    private ComprobanteMD comprobante;
    private MallaAlumnoMD mallaAlumno;
    private String observaciones;
    private Double pago;
    private Integer numMatricula;
    private Boolean activo;

    public PagoMD() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ComprobanteMD getComprobante() {
        return comprobante;
    }

    public void setComprobante(ComprobanteMD comprobante) {
        this.comprobante = comprobante;
    }

    public MallaAlumnoMD getMallaAlumno() {
        return mallaAlumno;
    }

    public void setMallaAlumno(MallaAlumnoMD mallaAlumno) {
        this.mallaAlumno = mallaAlumno;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Double getPago() {
        return pago;
    }

    public void setPago(Double pago) {
        this.pago = pago;
    }

    public Integer getNumMatricula() {
        return numMatricula;
    }

    public void setNumMatricula(Integer numMatricula) {
        this.numMatricula = numMatricula;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

}
