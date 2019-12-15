package modelo.docente;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.persona.DocenteMD;
import utils.CONBD;
import utils.M;

/**
 *
 * @author DAVICHO
 */
public class RolDocenteBD extends CONBD {

    private static RolDocenteBD RDBD;

    public static RolDocenteBD single() {
        if (RDBD == null) {
            RDBD = new RolDocenteBD();
        }
        return RDBD;
    }

    public boolean InsertarRol(RolDocenteMD rd) {
        String nsql = "INSERT INTO public.\"RolesDocente\"(\n"
                + "id_docente, "
                + "id_rol_prd)\n"
                + "VALUES (" + rd.getIdDocente().getIdDocente() + ", "
                + "" + rd.getIdRolPeriodo().getId_rol() + ");";
        return CON.executeNoSQL(nsql);
    }

    public boolean editarRolPeriodo(RolDocenteMD rd) {
        String nsql = "UPDATE public.\"RolesDocente\"\n"
                + "SET id_rol_prd= " + rd.getIdRolPeriodo().getId_rol() + "\n"
                + "WHERE id_rol_docente=" + rd.getIdDocente() + ";";
        return CON.executeNoSQL(nsql);
    }

    public boolean eliminarRolPeriodo(int aguja) {
        String nsql = "Update public.\"RolesDocente\"n"
                + "SET rol_docente_activo= false \n"
                + "WHERE id_rol_docente=" + aguja + ";";
        return CON.executeNoSQL(nsql);
    }

    public ArrayList<RolDocenteMD> llenarTabla() {
        ArrayList<RolDocenteMD> lista = new ArrayList();
        String sql = "Select "
                + "a.persona_primer_apellido,a.persona_primer_nombre"
                + "p.rol_prd\n"
                + "from\n"
                + "public.\"RolesDocente\" r, public.\"RolesPeriodo\" p, "
                + "public.\"Personas\" a, public.\"Docentes\" d\n"
                + "where\n"
                + "p.id_rol_prd=r.id_rol_prd and\n"
                + "r.id_docente = d.id_docente and\n"
                + "d.id_persona= a.id_persona and id_docente_activo= true;";
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                RolDocenteMD rd = new RolDocenteMD();
                DocenteMD d = new DocenteMD();
                RolPeriodoMD m = new RolPeriodoMD();
                d.setPrimerNombre(rs.getString("persona_primer_nombre"));
                d.setPrimerApellido(rs.getString("persona_primer_apellido"));
                m.setNombre_rol(rs.getString("rol_prd"));
                rd.setIdDocente(d);
                rd.setIdRolPeriodo(m);
                lista.add(rd);
            }
        } catch (SQLException ex) {
            M.errorMsg("No consultamos roles de docente. " + ex.getMessage());
        }
        return lista;
    }
}
