package modelo.docente;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.ConectarDB;
import modelo.periodolectivo.PeriodoLectivoMD;

/**
 *
 * @author arman
 */
public class RolPeriodoBD extends RolPeriodoMD {

    private final ConectarDB conecta;

    public RolPeriodoBD(ConectarDB conecta) {
        this.conecta = conecta;
    }

    public boolean InsertarRol() {
        String nsql = "Insert into public.\"RolesPeriodo\"(\n"
                + "id_prd_lectivo,rol_prd)\n"
                + "Values (" + getPeriodo().getID() + ", "
                + "UPPER('" + getNombre_rol() + "'));";
        PreparedStatement ps = conecta.getPS(nsql);
        if (conecta.nosql(ps) == null) {
            return true;
        } else {
            System.out.println("Error");
            return false;
        }
    }

    public boolean editarRolPeriodo(int aguja) {
        String nsql = "UPDATE public.\"RolesPeriodo\"\n"
                + "SET id_prd_lectivo=" + getPeriodo().getID() + ","
                + " rol_prd='" + getNombre_rol() + "'\n"
                + " WHERE id_rol_prd= " + aguja + ";";
        PreparedStatement ps = conecta.getPS(nsql);
        if (conecta.nosql(ps) == null) {
            return true;
        } else {
            System.out.println("Error");
            return false;
        }
    }

    public boolean eliminarRolPeriodo(int aguja) {
        String nsql = "UPDATE public.\"RolesPeriodo\"\n"
                + "	SET rol_activo=false\n"
                + "	WHERE id_rol_prd=" + aguja + ";";
        PreparedStatement ps = conecta.getPS(nsql);
        if (conecta.nosql(ps) == null) {
            return true;
        } else {
            System.out.println("Error de consulta");
            return false;
        }
    }

    public ArrayList<RolPeriodoMD> llenarTabla() {
        ArrayList<RolPeriodoMD> lista = new ArrayList();
        String sql = "Select prd_lectivo_nombre, rol_prd, id_rol_prd, p.id_prd_lectivo  "
                + "from \n"
                + "public.\"PeriodoLectivo\" p join public.\"RolesPeriodo\"\n"
                + "using (id_prd_lectivo) where rol_activo= true;";
        PreparedStatement ps = conecta.getPS(sql);
        ResultSet rs = conecta.sql(ps);
        try {
            while (rs.next()) {
                RolPeriodoMD m = new RolPeriodoMD();
                PeriodoLectivoMD perL = new PeriodoLectivoMD();
                perL.setID(rs.getInt("id_prd_lectivo"));
                perL.setNombre(rs.getString("prd_lectivo_nombre"));
                m.setId_rol(rs.getInt("id_rol_prd"));
                m.setPeriodo(perL);
                //m.setPeriodo(perLec.getNombre_PerLectivo());
                m.setNombre_rol(rs.getString("rol_prd"));
                lista.add(m);
            }
            ps.getConnection().close();
            return lista;
        } catch (SQLException ex) {
            System.out.println("No se pudieron consultar alumnos");
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public ArrayList<RolPeriodoMD> cargarRolesPorPeriodo(int idPrd) {
        ArrayList<RolPeriodoMD> rPrd = new ArrayList();
        String sql = "SELECT p.id_rol_prd, p.id_prd_lectivo, p.rol_prd, p.rol_activo\n"
                + "	FROM public.\"RolesPeriodo\" p\n"
                + "	where p.id_prd_lectivo=" + idPrd + "\n"
                + "	and p.rol_activo= true;";
        PreparedStatement ps = conecta.getPS(sql);
        ResultSet rs = conecta.sql(ps);

        try {
            while (rs.next()) {
                RolPeriodoMD rol = new RolPeriodoMD();
                rol.setId_rol(rs.getInt("id_rol_prd"));
                rol.setNombre_rol(rs.getString("rol_prd"));
                System.out.println("Nombre " + rol.getNombre_rol());
                rPrd.add(rol);
            }
            ps.getConnection().close();
            return rPrd;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
}
