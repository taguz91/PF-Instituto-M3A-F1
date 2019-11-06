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
public class ComprobanteMD {

    private Integer id;
    private PeriodoLectivoMD periodo;
    private AlumnoMD alumno;
    private Image comprobante;
    private FileInputStream file;
    private int longBytes;
    private Double total;
    private String codigo;
    private LocalDateTime fechaPago;
    private String usuarioIngreso;
    private Boolean activo;

    private List<PagoMD> pagos;

    public ComprobanteMD() {
    }

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

    public List<PagoMD> getPagos() {
        return pagos;
    }

    public void setPagos(List<PagoMD> pagos) {
        this.pagos = pagos;
    }

}
