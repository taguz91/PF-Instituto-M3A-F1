
package modelo.periodolectivo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.ConectarDB;
import modelo.persona.AlumnoBD;
import modelo.persona.DocenteMD;

public class PeriodoLectivoBD extends PeriodoLectivoMD{
    
    ConectarDB conecta = new ConectarDB("Periodo lectivo");

    public PeriodoLectivoBD() {
    }

    public PeriodoLectivoBD(int id_PerioLectivo, String nombre_PerLectivo, String observacion_PerLectivo, boolean activo_PerLectivo, LocalDate fecha_Inicio, LocalDate fecha_Fin, int id, String codigo, String nombre, LocalDate fechaInicio, LocalDate fechaFin, String modalidad, DocenteMD coordinador) {
        super(id_PerioLectivo, nombre_PerLectivo, observacion_PerLectivo, activo_PerLectivo, fecha_Inicio, fecha_Fin, id, codigo, nombre, fechaInicio, fechaFin, modalidad, coordinador);
    }
    
    public boolean guardarPeriodo(){
        String nsql = "INSERTO INTO public.\"PeriodoLectivo\"(\n"
                + "id_carrera, prd_lectivo_nombre, prd_lectivo_fecha_inicio, prd_lectivo_fecha_fin, prd_lectivo_observacion, prd_lectivo_activo"
                + " VALUES( " + getId() + ", '" + getNombre_PerLectivo() + "', " + getFecha_Inicio() 
                + ", " + getFecha_Fin() + ", '" + getObservacion_PerLectivo() + "', true);";
        if (conecta.nosql(nsql) == null) {
            return true;
        } else {
            System.out.println("Error");
            return false;
        }
    }
    
    public boolean editarPeriodo(){
        String nsql = "UPDATE public.\"PeriodoLectivo\" SET\n"
                + " id_carrera = " + getId() + ", prd_lectivo_nombre = '" + getNombre_PerLectivo() + "',"
                + " prd_lectivo_fecha_inicio = " + getFecha_Inicio() + ", prd_lectivo_fecha_fin = " + getFecha_Fin()
                + " prd_lectivo_observacion = '" + getObservacion_PerLectivo() + "\n"
                + " WHERE id_prd_lectivo = " + getId_PerioLectivo() +";";
        if (conecta.nosql(nsql) == null) {
            return true;
        } else {
            System.out.println("Error");
            return false;
        }
    }
    
    public boolean eliminarPeriodo(){
        PeriodoLectivoMD periodo = new PeriodoLectivoMD();
        String nsql = "UPDATE public.\"PeriodoLectivo\" SET\n"
                + "prd_lectivo_activo = false" 
                + "WHERE id_prd_lectivo = " + getId_PerioLectivo() + ";";
        if (conecta.nosql(nsql) == null) {
            return true;
        } else {
            System.out.println("Error");
            return false;
        }
    }
    
    public List<PeriodoLectivoMD> capturarCarrera(){
        List<PeriodoLectivoMD> lista = new ArrayList<PeriodoLectivoMD>();
        String sql = "SELECT id_carrera, carrera_nombre FROM public.\"Carreras\";";
        ResultSet rs = conecta.sql(sql);
        try {
            while (rs.next()) {
                PeriodoLectivoMD a = new PeriodoLectivoMD();
                a.setId(rs.getInt("id_carrera"));
                a.setNombre(rs.getString("carrera_nombre"));
                lista.add(a);
            }
            rs.close();
            return lista;
        } catch (SQLException ex) {
            Logger.getLogger(PeriodoLectivoBD.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public PeriodoLectivoMD capturarIdCarrera(String aguja){
        String sql = "SELECT id_carrera"
                + " FROM public.\"Carreras\" WHERE carrera_nombre LIKE '%" + aguja + "%';";
        ResultSet rs = conecta.sql(sql);
        try {
            PeriodoLectivoMD c = new PeriodoLectivoMD();
            while (rs.next()) {
                c.setId(rs.getInt("id_carrera"));
            }
            rs.close();
            return c;
        } catch (SQLException ex) {
            Logger.getLogger(PeriodoLectivoBD.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public PeriodoLectivoMD capturarNomCarrera(int aguja){
        String sql = "SELECT carrera_nombre"
                + " FROM public.\"Carreras\" WHERE id_carrera = " + aguja + ";";
        ResultSet rs = conecta.sql(sql);
        try {
            PeriodoLectivoMD c = new PeriodoLectivoMD();
            while (rs.next()) {
                c.setNombre(rs.getString("carrera_nombre"));
            }
            rs.close();
            return c;
        } catch (SQLException ex) {
            Logger.getLogger(PeriodoLectivoBD.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<PeriodoLectivoMD> llenarTabla(){
        List<PeriodoLectivoMD> lista = new ArrayList<PeriodoLectivoMD>();
        String sql = "SELECT id_prd_lectivo, id_carrera, prd_lectivo_nombre, prd_lectivo_fecha_inicio, prd_lectivo_fecha_fin"
                + " FROM public.\"PeriodoLectivo\" WHERE prd_lectivo_activo = true;";
        ResultSet rs = conecta.sql(sql);
        try {
            while (rs.next()) {
                PeriodoLectivoMD m = new PeriodoLectivoMD();
                m.setId_PerioLectivo(rs.getInt("id_prd_lectivo"));
                m.setId(rs.getInt("id_carrera"));
                m.setNombre_PerLectivo(rs.getString("prd_lectivo_nombre"));
                //m.setFecha_Inicio(rs.getDate("prd_lectivo_fecha_inicio"));
                //m.setFecha_Fin(rs.getDate("prd_lectivo_fecha_fin"));
                lista.add(m);
            }
            rs.close();
            return lista;
        } catch (SQLException ex) {
            Logger.getLogger(AlumnoBD.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } 
    }
    
    public PeriodoLectivoMD capturarPeriodos(String aguja){
        String sql = "SELECT p.id_prd_lectivo, p.id_carrera, p.prd_lectivo_nombre, p.prd_lectivo_fecha_inicio, p.prd_lectivo_fecha_fin, p.prd_lectivo_observacion, c.carrera_nombre"
                + " FROM public.\"PeriodoLectivo\" p, public.\"Carreras\" c WHERE p.id_carrera = c.id_carrera AND c.carrera_nombre LIKE '" 
                + aguja + "%' OR prd_lectivo_nombre LIKE '" + aguja + "%' AND prd_lectivo_activo = true;";
        ResultSet rs = conecta.sql(sql);
        try {
            PeriodoLectivoMD m = new PeriodoLectivoMD();
            while (rs.next()) {
                m.setId_PerioLectivo(rs.getInt("p.id_prd_lectivo"));
                m.setNombre_PerLectivo(rs.getString("p.prd_lectivo_nombre"));
                //m.setFecha_Inicio(rs.getDate("p.prd_lectivo_fecha_inicio"));
                //m.setFecha_Fin(rs.getDate("p.prd_lectivo_fecha_fin"));
                m.setObservacion_PerLectivo(rs.getString("p.prd_lectivo_observacion"));
                m.setId(rs.getInt("p.id_carrera"));
                m.setNombre(rs.getString("c.carrera_nombre"));
            }
            rs.close();
            return m;
        } catch (SQLException ex) {
            Logger.getLogger(AlumnoBD.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public PeriodoLectivoMD capturarPerLectivo(int ID){
        String sql = "SELECT p.id_prd_lectivo, p.id_carrera, p.prd_lectivo_nombre, p.prd_lectivo_fecha_inicio, p.prd_lectivo_fecha_fin, p.prd_lectivo_observacion, c.carrera_nombre"
                + " FROM public.\"PeriodoLectivo\" p, public.\"Carreras\" c"
                +" WHERE p.id_carrera = c.id_carrera AND c.id_prd_lectivo = " 
                + ID + " AND prd_lectivo_activo = true;";
        ResultSet rs = conecta.sql(sql);
        try {
            PeriodoLectivoMD m = new PeriodoLectivoMD();
            while (rs.next()) {
                m.setId_PerioLectivo(rs.getInt("p.id_prd_lectivo"));
                m.setNombre_PerLectivo(rs.getString("p.prd_lectivo_nombre"));
                //m.setFecha_Inicio(rs.getDate("p.prd_lectivo_fecha_inicio"));
                //m.setFecha_Fin(rs.getDate("p.prd_lectivo_fecha_fin"));
                m.setObservacion_PerLectivo(rs.getString("p.prd_lectivo_observacion"));
                m.setId(rs.getInt("p.id_carrera"));
                m.setNombre(rs.getString("c.carrera_nombre"));
            }
            rs.close();
            return m;
        } catch (SQLException ex) {
            Logger.getLogger(AlumnoBD.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
