package modelo.pagos;

import modelo.alumno.MallaAlumnoMD;

/**
 *
 * @author MrRainx
 */
public class PagoMateriaMD {

    private Integer id = 0;
    private ComprobantePagoMD comprobante;
    private MallaAlumnoMD mallaAlumno;
    private Double pago;
    private Integer numMatricula;
    private Boolean activo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ComprobantePagoMD getComprobante() {
        return comprobante;
    }

    public void setComprobante(ComprobantePagoMD comprobante) {
        this.comprobante = comprobante;
    }

    public MallaAlumnoMD getMallaAlumno() {
        return mallaAlumno;
    }

    public void setMallaAlumno(MallaAlumnoMD mallaAlumno) {
        this.mallaAlumno = mallaAlumno;
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
