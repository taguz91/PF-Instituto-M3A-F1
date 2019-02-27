package modelo.persona;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.ConectarDB;

/**
 *
 * @author Johnny
 */
public class SectorEconomicoBD extends SectorEconomicoMD {

    ConectarDB conecta = new ConectarDB("Sector economico");

    public List<SectorEconomicoMD> capturarSectores() {
        List<SectorEconomicoMD> sectores = new ArrayList();
        String sql = "SELECT id_sec_economico, sec_economico_descripcion FROM public.\"SectorEconomico\";";
        ResultSet rs = conecta.sql(sql);
        try {
            while (rs.next()) {
                SectorEconomicoMD s = new SectorEconomicoMD();
                s.setId_SecEconomico(rs.getInt("id_sec_economico"));
                s.setDescrip_SecEconomico(rs.getString("sec_economico_descripcion"));
                sectores.add(s);
            }
            rs.close();
            return sectores;
        } catch (SQLException ex) {
            Logger.getLogger(AlumnoBD.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public SectorEconomicoMD capturarIdSector(String aguja) {
        SectorEconomicoMD sector = new SectorEconomicoMD();
        String sql = "SELECT id_sec_economico FROM public.\"SectorEconomico\" WHERE UPPER(sec_economico_descripcion) LIKE '%"
                + aguja + "%';";
        ResultSet rs = conecta.sql(sql);
        try {
            while (rs.next()) {
                sector.setId_SecEconomico(rs.getInt("id_sec_economico"));
            }
            rs.close();
            return sector;
        } catch (SQLException ex) {
            Logger.getLogger(AlumnoBD.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public SectorEconomicoMD capturarSector(int aguja) {
        SectorEconomicoMD sector = new SectorEconomicoMD();
        String sql = "SELECT sec_economico_descripcion FROM public.\"SectorEconomico\" WHERE id_sec_economico = "
                + aguja + ";";
        ResultSet rs = conecta.sql(sql);
        try {
            while (rs.next()) {
                sector.setDescrip_SecEconomico(rs.getString("sec_economico_descripcion"));
            }
            rs.close();
            return sector;
        } catch (SQLException ex) {
            Logger.getLogger(AlumnoBD.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
