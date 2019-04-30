
package modelo.persona;

public class ProfesionMD {
    
    private int id_Titulo;
    private String titulo_nombre, titulo_abreviatura, titulo_institucion, titulo_observacion;
    private boolean titulo_estado;

    public ProfesionMD() {
    }
    
    public ProfesionMD(int id_Titulo, String titulo_nombre, String titulo_abreviatura, String titulo_institucion, String titulo_observacion, boolean titulo_estado) {
        this.id_Titulo = id_Titulo;
        this.titulo_nombre = titulo_nombre;
        this.titulo_abreviatura = titulo_abreviatura;
        this.titulo_institucion = titulo_institucion;
        this.titulo_observacion = titulo_observacion;
        this.titulo_estado = titulo_estado;
    }

    public int getId_Titulo() {
        return id_Titulo;
    }

    public void setId_Titulo(int id_Titulo) {
        this.id_Titulo = id_Titulo;
    }

    public String getTitulo_nombre() {
        return titulo_nombre;
    }

    public void setTitulo_nombre(String titulo_nombre) {
        this.titulo_nombre = titulo_nombre;
    }

    public String getTitulo_abreviatura() {
        return titulo_abreviatura;
    }

    public void setTitulo_abreviatura(String titulo_abreviatura) {
        this.titulo_abreviatura = titulo_abreviatura;
    }

    public String getTitulo_institucion() {
        return titulo_institucion;
    }

    public void setTitulo_institucion(String titulo_institucion) {
        this.titulo_institucion = titulo_institucion;
    }

    public String getTitulo_observacion() {
        return titulo_observacion;
    }

    public void setTitulo_observacion(String titulo_observacion) {
        this.titulo_observacion = titulo_observacion;
    }

    public boolean isTitulo_estado() {
        return titulo_estado;
    }

    public void setTitulo_estado(boolean titulo_estado) {
        this.titulo_estado = titulo_estado;
    }
    
    
    
}
