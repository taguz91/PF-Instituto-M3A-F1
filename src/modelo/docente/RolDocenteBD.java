package modelo.docente;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.ConectarDB;
import modelo.persona.DocenteBD;
import modelo.persona.DocenteMD;

/**
 *
 * @author DAVICHO
 */
public class RolDocenteBD extends RolDocenteMD {

    private final ConectarDB conecta;
    private final DocenteBD docentes;
    private final RolPeriodoBD prdRol;

    public RolDocenteBD(ConectarDB conecta) {
        this.conecta = conecta;
        this.docentes = new DocenteBD(conecta);
        this.prdRol = new RolPeriodoBD(conecta);
    }

    public boolean InsertarRol() {
        String nsql = "INSERT INTO public.\"RolesDocente\"(\n"
                + "	id_docente, id_rol_prd)\n"
                + "	VALUES (" + getIdDocente().getIdDocente() + ",+" + getIdRolPeriodo().getId_rol() + ");";
        PreparedStatement ps = conecta.getPS(nsql);
        if (conecta.nosql(ps) == null) {
            return true;
        } else {
            System.out.println("Error de conexion a la BD");
            return false;
        }
    }

    public boolean editarRolPeriodo(int aguja) {
        String nsql = "UPDATE public.\"RolesDocente\"\n"
                + "	SET id_rol_prd= " + getIdRolPeriodo().getId_rol() + "\n"
                + "	WHERE id_rol_docente=" + aguja + ";";
        PreparedStatement ps = conecta.getPS(nsql);
        if (conecta.nosql(ps) == null) {
            return true;
        } else {
            System.out.println("Error");
            return false;
        }
    }

    public boolean eliminarRolPeriodo(int aguja) {
        String nsql = "Update public.\"RolesDocente\"n"
                + "SET rol_docente_activo= false \n"
                + "WHERE id_rol_docente=" + aguja + ";";
        PreparedStatement ps = conecta.getPS(nsql);
        if (conecta.nosql(ps) == null) {
            return true;
        } else {
            System.out.println("Error de consulta");
            return false;
        }
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
        PreparedStatement ps = conecta.getPS(sql);
        ResultSet rs = conecta.sql(sql);
        try {
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
            ps.getConnection().close();
            return lista;
        } catch (SQLException ex) {
            System.out.println("No se pudieron consultar alumnos");
            System.out.println(ex.getMessage());
            return null;
        }
    }
}
