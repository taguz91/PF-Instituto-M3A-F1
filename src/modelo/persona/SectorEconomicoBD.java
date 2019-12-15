package modelo.persona;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.CONBD;
import utils.M;

/**
 *
 * @author Johnny
 */
public class SectorEconomicoBD extends CONBD {
    
    private static SectorEconomicoBD SEBD; 
    
    public static SectorEconomicoBD single(){
        if (SEBD == null) {
            SEBD = new SectorEconomicoBD();
        }
        return SEBD;
    }
    
    public List<SectorEconomicoMD> capturarSectores() {
        List<SectorEconomicoMD> sectores = new ArrayList();
        String sql = "SELECT id_sec_economico, "
                + "sec_economico_descripcion "
                + "FROM public.\"SectorEconomico\" "
                + "WHERE id_sec_economico > 0;";
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SectorEconomicoMD s = new SectorEconomicoMD();
                s.setId_SecEconomico(rs.getInt("id_sec_economico"));
                s.setDescrip_SecEconomico(rs.getString("sec_economico_descripcion"));
                sectores.add(s);
            }
            return sectores;
        } catch (SQLException ex) {
            M.errorMsg("No pudimos consultar todos los sectores: "+ex.getMessage());
            return null;
        } finally {
            CON.cerrarCONPS(ps);
        }
    }

    public SectorEconomicoMD capturarIdSector(String aguja){
        SectorEconomicoMD sector = new SectorEconomicoMD();
        String sql = "SELECT id_sec_economico "
                + "FROM public.\"SectorEconomico\" "
                + "WHERE UPPER(sec_economico_descripcion) LIKE '%"
                + aguja + "%';";
        PreparedStatement ps = CON.getPSPOOL(sql);
        
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                sector.setId_SecEconomico(rs.getInt("id_sec_economico"));
            }
            return sector;
        } catch (SQLException ex) {
            M.errorMsg("Capturamos id de sectores. "+ex.getMessage());
            return null;
        } finally {
            CON.cerrarCONPS(ps);
        }
    }

    public SectorEconomicoMD capturarSector(int aguja) {
        SectorEconomicoMD sector = new SectorEconomicoMD();
        String sql = "SELECT sec_economico_descripcion "
                + "FROM public.\"SectorEconomico\" WHERE id_sec_economico = "
                + aguja + ";";
        PreparedStatement ps = CON.getPSPOOL(sql);
        
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                sector.setDescrip_SecEconomico(rs.getString("sec_economico_descripcion"));
            }
            return sector;
        } catch (SQLException ex) {
            M.errorMsg("Capturamos todos los sectores. "+ex.getMessage());
            return null;
        } finally {
            CON.cerrarCONPS(ps);
        }
    }
}
