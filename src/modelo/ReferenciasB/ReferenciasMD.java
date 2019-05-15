
package modelo.ReferenciasB;


public class ReferenciasMD {
    private int id_referencia;
    private String codigo_referencia;
    private String descripcion_referencia;
    private String tipo_referencia;
    private String observaciones;
    private boolean existe_en_biblioteca;

    public ReferenciasMD() {
    }

    public ReferenciasMD(int id_referencia, String codigo_referencia, String descripcion_referencia, String tipo_referencia, boolean existe_en_biblioteca, String observaciones) {
        this.id_referencia = id_referencia;
        this.codigo_referencia = codigo_referencia;
        this.descripcion_referencia = descripcion_referencia;
        this.tipo_referencia = tipo_referencia;
        this.existe_en_biblioteca = existe_en_biblioteca;
        this.observaciones=observaciones;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
    public int getId_referencia() {
        return id_referencia;
    }

    public void setId_referencia(int id_referencia) {
        this.id_referencia = id_referencia;
    }

    public String getCodigo_referencia() {
        return codigo_referencia;
    }

    public void setCodigo_referencia(String codigo_referencia) {
        this.codigo_referencia = codigo_referencia;
    }

    public String getDescripcion_referencia() {
        return descripcion_referencia;
    }

    public void setDescripcion_referencia(String descripcion_referencia) {
        this.descripcion_referencia = descripcion_referencia;
    }

    public String getTipo_referencia() {
        return tipo_referencia;
    }

    public void setTipo_referencia(String tipo_referencia) {
        this.tipo_referencia = tipo_referencia;
    }

    public boolean isExiste_en_biblioteca() {
        return existe_en_biblioteca;
    }

    public void setExiste_en_biblioteca(boolean existe_en_biblioteca) {
        this.existe_en_biblioteca = existe_en_biblioteca;
    }

}