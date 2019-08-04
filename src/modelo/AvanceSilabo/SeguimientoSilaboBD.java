/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.AvanceSilabo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.ConexionBD;

/**
 *
 * @author Skull
 */
public class SeguimientoSilaboBD  extends SeguimientoSilaboMD{
    
    private ConexionBD conexion;

    public SeguimientoSilaboBD(ConexionBD conexion) {
        this.conexion = conexion;
    }
    
    
    
    
    
    
    
    
    
    public static List<SeguimientoSilaboMD> consultarSeguimientoSilaboDocentes(ConexionBD conexion,String[] parametros){
        List<SeguimientoSilaboMD> lista_seguimiento=new ArrayList<>();
        
        try {
            PreparedStatement st=conexion.getCon().prepareStatement("SELECT DISTINCT  ss.id_seguimientosilabo,p.persona_primer_apellido,p.persona_primer_nombre,m.materia_nombre, cr.curso_nombre,ss.estado_seguimiento,ss.fecha_entrga_informe\n" +
"       FROM \"Silabo\" AS s\n" +
"       JOIN \"Materias\" AS m ON s.id_materia=m.id_materia\n" +
"       JOIN \"PeriodoLectivo\" AS pr ON pr.id_prd_lectivo=s.id_prd_lectivo\n" +
"                    JOIN \"Carreras\" AS crr ON crr.id_carrera = m.id_carrera\n" +
"                    JOIN \"Cursos\" AS cr ON cr.id_materia=m.id_materia\n" +
"                    JOIN \"Docentes\" AS d ON d.id_docente= cr.id_docente\n" +
"                    JOIN \"Personas\" AS p ON d.id_persona=p.id_persona \n" +
"					JOIN \"SeguimientoSilabo\" AS ss on  cr.id_curso=ss.id_Curso \n" +
"					JOIN \"Unidad_Seguimiento\" AS us on ss.id_seguimientosilabo=us.id_seguimientosilabo\n" +
"					JOIN \"Jornadas\" AS jo on cr.id_jornada=jo.id_jornada\n" +
"                    WHERE crr.carrera_nombre=?\n" +
"                    AND p.id_persona=? AND jo.nombre_jornada=? AND cr.id_prd_lectivo=? AND m.materia_nombre ILIKE '%" + parametros[2] + "%'");
            
             st.setString(1, parametros[0]);
            st.setInt(2, Integer.parseInt(parametros[3]));
            st.setString(3, parametros[1]);
            st.setInt(4, Integer.parseInt(parametros[4]));
            ResultSet rs = st.executeQuery();
            System.out.println(st );
            
            while(rs.next()){
                SeguimientoSilaboMD ss=new SeguimientoSilaboMD();
                ss.setId_seguimientoS(rs.getInt(1));
                ss.getPersona().setPrimerApellido(rs.getString(2));
                ss.getPersona().setPrimerNombre(rs.getString(3));
                ss.getMateria().setNombre(rs.getString(4));
                ss.getCurso().setNombre(rs.getString(5));
                ss.setEstado_seguimiento(rs.getInt(6));
                ss.setFecha_entrega_informe(rs.getDate(7).toLocalDate());
                lista_seguimiento.add(ss);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SeguimientoSilaboBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista_seguimiento;
    }
    public static List<SeguimientoSilaboMD> consultarSeguimientoSilaboCoordinador(ConexionBD conexion,String[] parametros){
        List<SeguimientoSilaboMD> lista_seguimiento=new ArrayList<>();
        
        try {
            PreparedStatement st=conexion.getCon().prepareStatement("SELECT DISTINCT  \n" +
"ss.id_seguimientosilabo,p.persona_primer_apellido,\n" +
"p.persona_primer_nombre,m.materia_nombre,\n" +
"cr.curso_nombre,ss.estado_seguimiento,ss.fecha_entrga_informe\n" +
"       FROM \"Silabo\" AS s\n" +
"       JOIN \"Materias\" AS m ON s.id_materia=m.id_materia\n" +
"       JOIN \"PeriodoLectivo\" AS pr ON pr.id_prd_lectivo=s.id_prd_lectivo\n" +
"                    JOIN \"Carreras\" AS crr ON crr.id_carrera = m.id_carrera\n" +
"                    JOIN \"Cursos\" AS cr ON cr.id_materia=m.id_materia\n" +
"                    JOIN \"Docentes\" AS d ON d.id_docente= cr.id_docente\n" +
"                    JOIN \"Personas\" AS p ON d.id_persona=p.id_persona \n" +
"					JOIN \"SeguimientoSilabo\" AS ss on  cr.id_curso=ss.id_Curso \n" +
"					JOIN \"Unidad_Seguimiento\" AS us on ss.id_seguimientosilabo=us.id_seguimientosilabo\n" +
"					JOIN \"Jornadas\" AS jo on cr.id_jornada=jo.id_jornada\n" +
"                    WHERE crr.carrera_nombre=?\n" +
"                     AND jo.nombre_jornada=? AND cr.id_prd_lectivo=? AND m.materia_nombre ILIKE '%" + parametros[2] + "%'");
            
            st.setString(1, parametros[0]);
            st.setString(2, parametros[1]);
            st.setInt(3, Integer.parseInt(parametros[3]));
            ResultSet rs = st.executeQuery();
            System.out.println(st );
            
            while(rs.next()){
                SeguimientoSilaboMD ss=new SeguimientoSilaboMD();
                ss.setId_seguimientoS(rs.getInt(1));
                ss.getPersona().setPrimerApellido(rs.getString(2));
                ss.getPersona().setPrimerNombre(rs.getString(3));
                ss.getMateria().setNombre(rs.getString(4));
                ss.getCurso().setNombre(rs.getString(5));
                ss.setEstado_seguimiento(rs.getInt(6));
                ss.setFecha_entrega_informe(rs.getDate(7).toLocalDate());
                lista_seguimiento.add(ss);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SeguimientoSilaboBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista_seguimiento;
    }
    
}
