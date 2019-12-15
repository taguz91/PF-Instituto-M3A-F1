package modelo.docente;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.periodolectivo.PeriodoLectivoMD;
import utils.CONBD;
import utils.M;

/**
 *
 * @author arman
 */
public class RolPeriodoBD extends CONBD {

    private static RolPeriodoBD RPBD;

    public static RolPeriodoBD single() {
        if (RPBD == null) {
            RPBD = new RolPeriodoBD();
        }
        return RPBD;
    }

    public boolean InsertarRol(RolPeriodoMD rp) {
        String nsql = "Insert into public.\"RolesPeriodo\"(\n"
                + "id_prd_lectivo,rol_prd)\n"
                + "Values (" + rp.getPeriodo().getID() + ", "
                + "UPPER('" + rp.getNombre_rol() + "'));";
        return CON.executeNoSQL(nsql);
    }

    public boolean editarRolPeriodo(RolPeriodoMD rp) {
        String nsql = "UPDATE public.\"RolesPeriodo\"\n"
                + "SET id_prd_lectivo=" + rp.getPeriodo().getID() + ","
                + " rol_prd='" + rp.getNombre_rol() + "'\n"
                + " WHERE id_rol_prd= " + rp.getId_rol() + ";";
        return CON.executeNoSQL(nsql);
    }

    public boolean eliminarRolPeriodo(int aguja) {
        String nsql = "UPDATE public.\"RolesPeriodo\"\n"
                + "	SET rol_activo=false\n"
                + "	WHERE id_rol_prd=" + aguja + ";";
        return CON.executeNoSQL(nsql);
    }

    public ArrayList<RolPeriodoMD> llenarTabla() {
        ArrayList<RolPeriodoMD> lista = new ArrayList();
        String sql = "Select prd_lectivo_nombre, rol_prd, id_rol_prd, p.id_prd_lectivo  "
                + "from \n"
                + "public.\"PeriodoLectivo\" p join public.\"RolesPeriodo\"\n"
                + "using (id_prd_lectivo) where rol_activo= true;";
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                RolPeriodoMD m = new RolPeriodoMD();
                PeriodoLectivoMD perL = new PeriodoLectivoMD();
                perL.setID(rs.getInt("id_prd_lectivo"));
                perL.setNombre(rs.getString("prd_lectivo_nombre"));
                m.setId_rol(rs.getInt("id_rol_prd"));
                m.setPeriodo(perL);
                m.setNombre_rol(rs.getString("rol_prd"));
                lista.add(m);
            }
        } catch (SQLException ex) {
            M.errorMsg("Error al consultar roles de peridoo. " + ex.getMessage());
        } finally {
            CON.cerrarCONPS(ps);
        }
        return lista;
    }

    public ArrayList<RolPeriodoMD> cargarRolesPorPeriodo(int idPrd) {
        ArrayList<RolPeriodoMD> rPrd = new ArrayList();
        String sql = "SELECT p.id_rol_prd, p.id_prd_lectivo, p.rol_prd, p.rol_activo\n"
                + "	FROM public.\"RolesPeriodo\" p\n"
                + "	where p.id_prd_lectivo=" + idPrd + "\n"
                + "	and p.rol_activo= true;";
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                RolPeriodoMD rol = new RolPeriodoMD();
                rol.setId_rol(rs.getInt("id_rol_prd"));
                rol.setNombre_rol(rs.getString("rol_prd"));
                System.out.println("Nombre " + rol.getNombre_rol());
                rPrd.add(rol);
            }
        } catch (SQLException ex) {
            M.errorMsg("Error al consultar roles de peridoo. " + ex.getMessage());
        } finally {
            CON.cerrarCONPS(ps);
        }
        return rPrd;
    }
}
