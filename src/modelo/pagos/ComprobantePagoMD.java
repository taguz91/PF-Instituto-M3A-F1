package modelo.pagos;

import java.awt.Image;
import java.io.FileInputStream;
import java.time.LocalDateTime;
import java.util.List;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.persona.AlumnoMD;

/**
 *
 * @author MrRainx
 */
public class ComprobantePagoMD {

    private Integer id = 0;
    private PeriodoLectivoMD periodo;
    private AlumnoMD alumno;
    private Image comprobante;
    private Double total;
    private String codigo;
    private String observaciones;
    private LocalDateTime fechaPago;
    private String usuarioIngreso;
    private Boolean activo;
    // Para subir la foto 
    private FileInputStream file = null;
    private int longBytes = 0;
    // Lista comprobantes 
    private List<PagoMateriaMD> pagos;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PeriodoLectivoMD getPeriodo() {
        return periodo;
    }

    public void setPeriodo(PeriodoLectivoMD periodo) {
        this.periodo = periodo;
    }

    public AlumnoMD getAlumno() {
        return alumno;
    }

    public void setAlumno(AlumnoMD alumno) {
        this.alumno = alumno;
    }

    public Image getComprobante() {
        return comprobante;
    }

    public void setComprobante(Image comprobante) {
        this.comprobante = comprobante;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public LocalDateTime getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDateTime fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getUsuarioIngreso() {
        return usuarioIngreso;
    }

    public void setUsuarioIngreso(String usuarioIngreso) {
        this.usuarioIngreso = usuarioIngreso;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public FileInputStream getFile() {
        return file;
    }

    public void setFile(FileInputStream file) {
        this.file = file;
    }

    public int getLongBytes() {
        return longBytes;
    }

    public void setLongBytes(int longBytes) {
        this.longBytes = longBytes;
    }

    public List<PagoMateriaMD> getPagos() {
        return pagos;
    }

    public void setPagos(List<PagoMateriaMD> pagos) {
        this.pagos = pagos;
    }

}
