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
import modelo.ConnDBPool;

/**
 *
 * @author Skull
 */
public class SeguimientoSilaboBD extends SeguimientoSilaboMD {

    private static final ConnDBPool CON = ConnDBPool.single();

    public SeguimientoSilaboBD() {
    }

    public boolean insertarSeguimiento(SeguimientoSilaboMD ss) {
        PreparedStatement st = CON.prepareStatement("INSERT INTO public.\"SeguimientoSilabo\"(\n"
                + "	fecha_entrga_informe, es_interciclo, id_curso)\n"
                + "	VALUES (?, ?, ?)");

        try {
            st.setDate(1, java.sql.Date.valueOf(ss.getFecha_entrega_informe()));
            st.setBoolean(2, ss.isEsInterciclo());
            st.setInt(3, ss.getCurso().getId());
            st.executeUpdate();
            System.out.println(st);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(SeguimientoSilaboBD.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            CON.close(st);
        }
        return true;

    }

    public static List<SeguimientoSilaboMD> consultarSeguimientoSilaboDocentes(String[] parametros) {
        List<SeguimientoSilaboMD> lista_seguimiento = new ArrayList<>();
        PreparedStatement st = CON.prepareStatement("SELECT DISTINCT  ss.id_seguimientosilabo,p.persona_primer_apellido,p.persona_primer_nombre,m.materia_nombre, cr.curso_nombre,ss.estado_seguimiento,ss.fecha_entrga_informe,ss.es_interciclo\n"
                + "       FROM \"Silabo\" AS s\n"
                + "       JOIN \"Materias\" AS m ON s.id_materia=m.id_materia\n"
                + "       JOIN \"PeriodoLectivo\" AS pr ON pr.id_prd_lectivo=s.id_prd_lectivo\n"
                + "                    JOIN \"Carreras\" AS crr ON crr.id_carrera = m.id_carrera\n"
                + "                    JOIN \"Cursos\" AS cr ON cr.id_materia=m.id_materia\n"
                + "                    JOIN \"Docentes\" AS d ON d.id_docente= cr.id_docente\n"
                + "                    JOIN \"Personas\" AS p ON d.id_persona=p.id_persona \n"
                + "					JOIN \"SeguimientoSilabo\" AS ss on  cr.id_curso=ss.id_Curso \n"
                + "					JOIN \"Unidad_Seguimiento\" AS us on ss.id_seguimientosilabo=us.id_seguimientosilabo\n"
                + "					JOIN \"Jornadas\" AS jo on cr.id_jornada=jo.id_jornada\n"
                + "                    WHERE crr.carrera_nombre=?\n"
                + "                    AND p.id_persona=? AND jo.nombre_jornada=? AND cr.id_prd_lectivo=? AND m.materia_nombre ILIKE '%" + parametros[2] + "%'");

        try {

            st.setString(1, parametros[0]);
            st.setInt(2, Integer.parseInt(parametros[3]));
            st.setString(3, parametros[1]);
            st.setInt(4, Integer.parseInt(parametros[4]));
            ResultSet rs = st.executeQuery();
            System.out.println(st);

            while (rs.next()) {
                SeguimientoSilaboMD ss = new SeguimientoSilaboMD();
                ss.setId_seguimientoS(rs.getInt(1));
                ss.getPersona().setPrimerApellido(rs.getString(2));
                ss.getPersona().setPrimerNombre(rs.getString(3));
                ss.getMateria().setNombre(rs.getString(4));
                ss.getCurso().setNombre(rs.getString(5));
                ss.setEstado_seguimiento(rs.getInt(6));
                ss.setFecha_entrega_informe(rs.getDate(7).toLocalDate());
                ss.setEsInterciclo(rs.getBoolean(8));
                lista_seguimiento.add(ss);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SeguimientoSilaboBD.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            CON.close(st);
        }
        return lista_seguimiento;
    }

    public static List<SeguimientoSilaboMD> consultarSeguimientoSilaboCoordinador(String[] parametros) {
        List<SeguimientoSilaboMD> lista_seguimiento = new ArrayList<>();
        PreparedStatement st = CON.prepareStatement("SELECT DISTINCT  \n"
                + "ss.id_seguimientosilabo,p.persona_primer_apellido,\n"
                + "p.persona_primer_nombre,m.materia_nombre,\n"
                + "cr.curso_nombre,ss.estado_seguimiento,ss.fecha_entrga_informe,ss.es_interciclo\n"
                + "       FROM \"Silabo\" AS s\n"
                + "       JOIN \"Materias\" AS m ON s.id_materia=m.id_materia\n"
                + "       JOIN \"PeriodoLectivo\" AS pr ON pr.id_prd_lectivo=s.id_prd_lectivo\n"
                + "                    JOIN \"Carreras\" AS crr ON crr.id_carrera = m.id_carrera\n"
                + "                    JOIN \"Cursos\" AS cr ON cr.id_materia=m.id_materia\n"
                + "                    JOIN \"Docentes\" AS d ON d.id_docente= cr.id_docente\n"
                + "                    JOIN \"Personas\" AS p ON d.id_persona=p.id_persona \n"
                + "					JOIN \"SeguimientoSilabo\" AS ss on  cr.id_curso=ss.id_Curso \n"
                + "					JOIN \"Unidad_Seguimiento\" AS us on ss.id_seguimientosilabo=us.id_seguimientosilabo\n"
                + "					JOIN \"Jornadas\" AS jo on cr.id_jornada=jo.id_jornada\n"
                + "                    WHERE crr.carrera_nombre=?\n"
                + "                     AND jo.nombre_jornada=? AND cr.id_prd_lectivo=? AND m.materia_nombre ILIKE '%" + parametros[2] + "%'");

        try {

            st.setString(1, parametros[0]);
            st.setString(2, parametros[1]);
            st.setInt(3, Integer.parseInt(parametros[3]));
            ResultSet rs = st.executeQuery();
            System.out.println(st);

            while (rs.next()) {
                SeguimientoSilaboMD ss = new SeguimientoSilaboMD();
                ss.setId_seguimientoS(rs.getInt(1));
                ss.getPersona().setPrimerApellido(rs.getString(2));
                ss.getPersona().setPrimerNombre(rs.getString(3));
                ss.getMateria().setNombre(rs.getString(4));
                ss.getCurso().setNombre(rs.getString(5));
                ss.setEstado_seguimiento(rs.getInt(6));
                ss.setFecha_entrega_informe(rs.getDate(7).toLocalDate());
                ss.setEsInterciclo(rs.getBoolean(8));
                lista_seguimiento.add(ss);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SeguimientoSilaboBD.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            CON.close(st);
        }
        return lista_seguimiento;
    }

    public static List<SeguimientoSilaboMD> consultarSeguimientoExistentes2(int id_curso) {
        List<SeguimientoSilaboMD> lista_seguimiento = new ArrayList<>();
        PreparedStatement st = CON.prepareStatement("select id_curso from \"SeguimientoSilabo\" where id_curso=?");

        try {

            st.setInt(1, id_curso);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                SeguimientoSilaboMD ss = new SeguimientoSilaboMD();
                ss.getCurso().setId(rs.getInt(1));

                lista_seguimiento.add(ss);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SeguimientoSilaboBD.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            CON.close(st);
        }
        return lista_seguimiento;
    }

    public static List<SeguimientoSilaboMD> consultarSeguimientoEsInterciclo(int id_curso) {
        List<SeguimientoSilaboMD> lista_seguimiento = new ArrayList<>();
        PreparedStatement st = CON.prepareStatement("select es_interciclo from \"SeguimientoSilabo\" where id_curso=?");

        try {

            st.setInt(1, id_curso);
            ResultSet rs = st.executeQuery();
            System.out.println(st);

            while (rs.next()) {
                SeguimientoSilaboMD ss = new SeguimientoSilaboMD();
                ss.setEsInterciclo(rs.getBoolean(1));

                lista_seguimiento.add(ss);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SeguimientoSilaboBD.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            CON.close(st);
        }
        return lista_seguimiento;
    }

    public static List<SeguimientoSilaboMD> consultarSeguimientoAprobacion(int idsegui, int id_curso) {
        List<SeguimientoSilaboMD> lista_seguimiento = new ArrayList<>();
        PreparedStatement st = CON.prepareStatement("select estado_seguimiento,es_interciclo from \"SeguimientoSilabo\" where id_seguimientosilabo=? and id_curso=?");

        try {

            st.setInt(1, idsegui);
            st.setInt(2, id_curso);
            ResultSet rs = st.executeQuery();
            System.out.println(st + "  ----------------------------------------------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<< CONSULTAA");

            while (rs.next()) {
                SeguimientoSilaboMD ss = new SeguimientoSilaboMD();
                ss.setEstado_seguimiento(rs.getInt(1));
                ss.setEsInterciclo(rs.getBoolean(2));
                lista_seguimiento.add(ss);
                System.out.println(ss.getEstado_seguimiento() + "--------------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><<");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SeguimientoSilaboBD.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            CON.close(st);
        }
        return lista_seguimiento;
    }

    public static SeguimientoSilaboMD consultarIDsegui_IdCurso(int id_Segui) {
        SeguimientoSilaboMD segui = null;
        PreparedStatement st = CON.prepareStatement("SELECT id_seguimientosilabo,id_curso,es_interciclo\n"
                + "                    FROM  \"SeguimientoSilabo\"  \n"
                + "					\n"
                + "                      where id_seguimientosilabo=?");
        try {
            st.setInt(1, id_Segui);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                segui = new SeguimientoSilaboMD();
                segui.setId_seguimientoS(rs.getInt(1));
                segui.getCurso().setId(rs.getInt(2));
                segui.setEsInterciclo(rs.getBoolean(3));

            }
        } catch (SQLException ex) {
            Logger.getLogger(SeguimientoSilaboBD.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            CON.close(st);
        }
        return segui;
    }

    public static SeguimientoSilaboMD consultarUltimoSegumiento(int id_Segui, boolean esInterciclo) {
        SeguimientoSilaboMD segui = null;
        PreparedStatement st = CON.prepareStatement("select id_seguimientosilabo from public.\"SeguimientoSilabo\" where id_curso=? and es_interciclo=?");
        try {
            st.setInt(1, id_Segui);
            st.setBoolean(2, esInterciclo);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                segui = new SeguimientoSilaboMD();
                segui.setId_seguimientoS(rs.getInt(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SeguimientoSilaboBD.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            CON.close(st);
        }
        return segui;
    }

    public void eliminarSeguimientoSilabo(SeguimientoSilaboMD ss) {
        PreparedStatement st = CON.prepareStatement("DELETE FROM public.\"SeguimientoSilabo\"\n"
                + "	WHERE id_seguimientosilabo=?");
        try {
            st.setInt(1, ss.getId_seguimientoS());
            st.executeUpdate();
            System.out.println(st);
            st.close();
        } catch (SQLException e) {
            System.out.println("Falló al eliminar antes de guardar");
        } finally {
            CON.close(st);
        }

    }

    public void aprobarSeguimientoSilabo(int id_segui, int estado) {
        PreparedStatement st = CON.prepareStatement("UPDATE public.\"SeguimientoSilabo\"\n"
                + "SET estado_seguimiento=? where id_seguimientosilabo=?");
        try {
            st.setInt(1, estado);
            st.setInt(2, id_segui);
            st.executeUpdate();
            System.out.println(st);
            st.close();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            CON.close(st);
        }
    }

}
