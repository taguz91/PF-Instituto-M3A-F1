/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.docente;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.ConectarDB;
import modelo.periodolectivo.PeriodoLectivoBD;
import modelo.periodolectivo.PeriodoLectivoMD;

/**
 *
 * @author arman
 */
public class RolPeriodoBD extends RolPeriodoMD {

    private final ConectarDB conecta;

    //Para consultar periodos
    private final PeriodoLectivoBD perLec;

    public RolPeriodoBD(ConectarDB conecta) {
        this.conecta = conecta;
        this.perLec = new PeriodoLectivoBD(conecta);
    }

    public boolean InsertarRol() {
        String nsql = "Insert into public.\"RolesPeriodo\"(\n"
                + "id_prd_lectivo,rol_prd)\n"
                + "Values (" + getPeriodo().getId_PerioLectivo() + ", '" + getNombre_rol() + "');";
        if (conecta.nosql(nsql) == null) {
            return true;
        } else {
            System.out.println("Error");
            return false;
        }
    }

    public boolean editarRolPeriodo(int aguja) {
        String nsql = "UPDATE public.\"RolesPeriodo\"\n"
                + "SET id_prd_lectivo=" + getPeriodo().getId_PerioLectivo() + ","
                + " rol_prd='" + getNombre_rol() + "'\n"
                + " WHERE id_rol_prd= " + aguja + ";";
        if (conecta.nosql(nsql) == null) {
            return true;
        } else {
            System.out.println("Error");
            return false;
        }
    }
    public boolean eliminarRolPeriodo(int aguja){
        String nsql="UPDATE public.\"RolesPeriodo\"\n" +
"	SET rol_activo=false\n" +
"	WHERE id_rol_prd=" +aguja +";";
        if(conecta.nosql(nsql)== null){
            return true;
        } else{
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
        ResultSet rs = conecta.sql(sql);
        try {
            while (rs.next()) {
                RolPeriodoMD m = new RolPeriodoMD();
                PeriodoLectivoMD perL = new PeriodoLectivoMD();
                perL.setId_PerioLectivo(rs.getInt("id_prd_lectivo"));
                perL.setNombre_PerLectivo(rs.getString("prd_lectivo_nombre"));
                m.setId_rol(rs.getInt("id_rol_prd"));
                m.setPeriodo(perL);
                //m.setPeriodo(perLec.getNombre_PerLectivo());
                m.setNombre_rol(rs.getString("rol_prd"));
                lista.add(m);
            }
            rs.close();
            return lista;
        } catch (SQLException ex) {
            System.out.println("No se pudieron consultar alumnos");
            System.out.println(ex.getMessage());
            return null;
        }
    }
}
