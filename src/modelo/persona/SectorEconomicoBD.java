package modelo.persona;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.ConectarDB;

/**
 *
 * @author Johnny
 */
public class SectorEconomicoBD extends SectorEconomicoMD {

    private final ConectarDB conecta;

    public SectorEconomicoBD(ConectarDB conecta) {
        this.conecta = conecta;
    }

    public List<SectorEconomicoMD> capturarSectores() {
        List<SectorEconomicoMD> sectores = new ArrayList();
        String sql = "SELECT id_sec_economico, sec_economico_descripcion FROM public.\"SectorEconomico\" WHERE id_sec_economico > 0;";
        PreparedStatement ps = conecta.getPS(sql);
        ResultSet rs = conecta.sql(ps);
        try {
            while (rs.next()) {
                SectorEconomicoMD s = new SectorEconomicoMD();
                s.setId_SecEconomico(rs.getInt("id_sec_economico"));
                s.setDescrip_SecEconomico(rs.getString("sec_economico_descripcion"));
                sectores.add(s);
            }
            rs.close();
            ps.getConnection().close();
            return sectores;
        } catch (SQLException ex) {
            System.out.println("No pudimos consultar todos los sectores: "+ex.getMessage());
            return null;
        }
    }

    public SectorEconomicoMD capturarIdSector(String aguja){
        SectorEconomicoMD sector = new SectorEconomicoMD();
        String sql = "SELECT id_sec_economico FROM public.\"SectorEconomico\" WHERE UPPER(sec_economico_descripcion) LIKE '%"
                + aguja + "%';";
        PreparedStatement ps = conecta.getPS(sql);
        ResultSet rs = conecta.sql(ps);
        try {
            while (rs.next()) {
                sector.setId_SecEconomico(rs.getInt("id_sec_economico"));
            }
            rs.close();
            ps.getConnection().close();
            return sector;
        } catch (SQLException ex) {
            System.out.println("Capturamos id de sectores. "+ex.getMessage());
            return null;
        }
    }

    public SectorEconomicoMD capturarSector(int aguja) {
        SectorEconomicoMD sector = new SectorEconomicoMD();
        String sql = "SELECT sec_economico_descripcion FROM public.\"SectorEconomico\" WHERE id_sec_economico = "
                + aguja + ";";
        PreparedStatement ps = conecta.getPS(sql);
        ResultSet rs = conecta.sql(ps);
        try {
            while (rs.next()) {
                sector.setDescrip_SecEconomico(rs.getString("sec_economico_descripcion"));
            }
            rs.close();
            ps.getConnection().close();
            return sector;
        } catch (SQLException ex) {
            System.out.println("Capturamos todos los sectores. "+ex.getMessage());
            return null;
        }
    }
}
