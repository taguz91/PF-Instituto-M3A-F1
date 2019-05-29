
package modelo.ReferenciasB;


public class ReferenciasMD {
    private int id_referencia;
    private String codigo_referencia;
    private String descripcion_referencia;
    private String tipo_referencia;
    private String observaciones;
    private String codigo_isbn;
    private String numero_de_paginas;
    private String codigo_koha;
    private String codigo_dewey;
    private String area_referencias;
    private String autor2;
    private String autor3;
    private boolean existe_en_biblioteca;
    private String titulo;
    private String año;
    private String editor;
    private String ciudad;
    private String autor1;

    public ReferenciasMD() {
    }

    public ReferenciasMD(int id_referencia, String codigo_referencia, String descripcion_referencia, String tipo_referencia, boolean existe_en_biblioteca, String observaciones,
            String codigo_ibsn, String numero_de_paginas, String codigo_koha, String codigo_dewey, String area_referencias,String autor2,String autor3
            ,String titulo, String autor1, String año, String editor,String ciudad) {
        this.id_referencia = id_referencia;
        this.codigo_referencia = codigo_referencia;
        this.descripcion_referencia = descripcion_referencia;
        this.tipo_referencia = tipo_referencia;
        this.existe_en_biblioteca = existe_en_biblioteca;
        this.observaciones=observaciones;
        this.codigo_isbn=codigo_ibsn;
        this.numero_de_paginas=numero_de_paginas;
        this.codigo_koha=codigo_koha;
        this.codigo_dewey=codigo_dewey;
        this.area_referencias=area_referencias;
        this.autor2=autor2;
        this.autor3=autor3;
        this.titulo=titulo;
        this.año=año;
        this.editor=editor;
        this.ciudad=ciudad;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAño() {
        return año;
    }

    public void setAño(String año) {
        this.año = año;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getAutor1() {
        return autor1;
    }

    public void setAutor1(String autor1) {
        this.autor1 = autor1;
    }

    public String getAutor2() {
        return autor2;
    }

    public void setAutor2(String autor2) {
        this.autor2 = autor2;
    }

    public String getAutor3() {
        return autor3;
    }

    public void setAutor3(String autor3) {
        this.autor3 = autor3;
    }

    public String getCodigo_isbn() {
        return codigo_isbn;
    }

    public String getCodigo_koha() {
        return codigo_koha;
    }

    public void setCodigo_koha(String codigo_koha) {
        this.codigo_koha = codigo_koha;
    }

    public String getCodigo_dewey() {
        return codigo_dewey;
    }

    public void setCodigo_dewey(String codigo_dewey) {
        this.codigo_dewey = codigo_dewey;
    }

    public String getArea_referencias() {
        return area_referencias;
    }

    public void setArea_referencias(String area_referencias) {
        this.area_referencias = area_referencias;
    }
    

    public void setCodigo_isbn(String codigo_isbn) {
        this.codigo_isbn = codigo_isbn;
    }

    public String getNumero_de_paginas() {
        return numero_de_paginas;
    }

    public void setNumero_de_paginas(String numero_de_paginas) {
        this.numero_de_paginas = numero_de_paginas;
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