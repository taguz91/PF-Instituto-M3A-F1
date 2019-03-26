
package modelo.persona;

public class SectorEconomicoMD {
    
    private String descrip_SecEconomico, codigo_SectEconomico;
    private int id_SecEconomico;

    public SectorEconomicoMD() {
    }

    
    
    public SectorEconomicoMD(int id_SecEconomico, String descrip_SecEconomico, String codigo_SectEconomico) {
        this.id_SecEconomico = id_SecEconomico;
        this.descrip_SecEconomico = descrip_SecEconomico;
        this.codigo_SectEconomico = codigo_SectEconomico;
    }

    public int getId_SecEconomico() {
        return id_SecEconomico;
    }

    public void setId_SecEconomico(int id_SecEconomico) {
        this.id_SecEconomico = id_SecEconomico;
    }

    public String getDescrip_SecEconomico() {
        return descrip_SecEconomico;
    }

    public void setDescrip_SecEconomico(String descrip_SecEconomico) {
        this.descrip_SecEconomico = descrip_SecEconomico;
    }

    public String getCodigo_SectEconomico() {
        return codigo_SectEconomico;
    }

    public void setCodigo_SectEconomico(String codigo_SectEconomico) {
        this.codigo_SectEconomico = codigo_SectEconomico;
    }
    
    
    
}
