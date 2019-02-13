package modelo.lugar;

/**
 *
 * @author Johnny
 */
public class LugarMD {
    private int id; 
    private String codigo; 
    private String nombre; 
    private String nivel; 
    private int idReferencia;    
    
    public LugarMD(){
        
    }

    public LugarMD(int id, String codigo, String nombre, String nivel, int idReferencia) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.nivel = nivel;
        this.idReferencia = idReferencia;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public int getIdReferencia() {
        return idReferencia;
    }

    public void setIdReferencia(int idReferencia) {
        this.idReferencia = idReferencia;
    }
    
}
