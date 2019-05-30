/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.silabo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.ConexionBD;

import modelo.materia.MateriaMD;
import modelo.periodolectivo.PeriodoLectivoMD;

/**
 *
 * @author Andres Ullauri
 */
public class SilaboBD extends SilaboMD {

    private ConexionBD conexion;

    public SilaboBD(ConexionBD conexion) {
        this.conexion = conexion;
    }

    public SilaboBD(ConexionBD conexion, MateriaMD idMateria, PeriodoLectivoMD idPeriodoLectivo) {
        super(idMateria, idPeriodoLectivo);
        this.conexion = conexion;
    }

    public void insertar() {

        try {
            PreparedStatement st = conexion.getCon().prepareStatement("INSERT INTO public.\"Silabo\"(\n"
                    + "	 id_materia, id_prd_lectivo)\n"
                    + "	VALUES (?, ?)");

            st.setInt(1, getIdMateria().getId());
            st.setInt(2, getIdPeriodoLectivo().getId_PerioLectivo());
            st.executeUpdate();
            System.out.println(st);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(SilaboBD.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static List<SilaboMD> consultar(ConexionBD conexion, String[] clave) {

        List<SilaboMD> silabos = new ArrayList<>();
        try {

            PreparedStatement st = conexion.getCon().prepareStatement("SELECT DISTINCT id_silabo,\n"
                    + "s.id_materia, m.materia_nombre, m.materia_horas_docencia,m.materia_horas_practicas,m.materia_horas_auto_estudio, estado_silabo,\n"
                    + "pr.id_prd_lectivo, pr.prd_lectivo_fecha_inicio, pr.prd_lectivo_fecha_fin\n"
                    + "FROM \"Silabo\" AS s\n"
                    + "JOIN \"Materias\" AS m ON s.id_materia=m.id_materia\n"
                    + "JOIN \"PeriodoLectivo\" AS pr ON pr.id_prd_lectivo=s.id_prd_lectivo\n"
                    + "JOIN \"Carreras\" AS crr ON crr.id_carrera = m.id_carrera\n"
                    + "JOIN \"Cursos\" AS cr ON cr.id_materia=m.id_materia\n"
                    + "JOIN \"Docentes\" AS d ON d.id_docente= cr.id_docente\n"
                    + "JOIN \"Personas\" AS p ON d.id_persona=p.id_persona\n"
                    + "WHERE crr.carrera_nombre=?\n"
                    + "AND m.materia_nombre ILIKE '%" + clave[1] + "%'\n"
                    + "AND p.id_persona=? AND cr.id_prd_lectivo=s.id_prd_lectivo");

            st.setString(1, clave[0]);
            st.setInt(2, Integer.parseInt(clave[2]));

            System.out.println(st);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {

                SilaboMD tmp = new SilaboMD();
                tmp.setIdSilabo(rs.getInt(1));
                tmp.getIdMateria().setId(rs.getInt(2));
                tmp.getIdMateria().setNombre(rs.getString(3));
                tmp.getIdMateria().setHorasDocencia(rs.getInt(4));
                tmp.getIdMateria().setHorasPracticas(rs.getInt(5));
                tmp.getIdMateria().setHorasAutoEstudio(rs.getInt(6));
                tmp.setEstadoSilabo(rs.getInt(7));
                tmp.getIdPeriodoLectivo().setId_PerioLectivo(rs.getInt(8));
                tmp.getIdPeriodoLectivo().setFecha_Inicio(rs.getDate(9).toLocalDate());
                tmp.getIdPeriodoLectivo().setFecha_Fin(rs.getDate(10).toLocalDate());

                silabos.add(tmp);
            }

        } catch (SQLException ex) {
            Logger.getLogger(SilaboBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return silabos;
    }
    
    public static List<SilaboMD> consultarCoordinador(ConexionBD conexion, int clave, String parametro) {

        List<SilaboMD> silabos = new ArrayList<>();
        try {

            PreparedStatement st = conexion.getCon().prepareStatement("SELECT id_silabo, s.id_materia, m.materia_nombre, pr.id_prd_lectivo, pr.prd_lectivo_fecha_inicio, pr.prd_lectivo_fecha_fin, s.estado_silabo FROM \"Silabo\" s\n"
                    + "JOIN \"Materias\" m ON s.id_materia=m.id_materia\n"
                    + "JOIN \"PeriodoLectivo\" pr ON pr.id_prd_lectivo=s.id_prd_lectivo\n"
                    + "WHERE m.id_carrera=? "
                    + "AND m.materia_nombre ILIKE '%" + parametro + "%'\n"
                    + "ORDER BY pr.id_prd_lectivo DESC ");

            st.setInt(1, clave);
            System.out.println(st);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {

                SilaboMD tmp = new SilaboMD();
                tmp.setIdSilabo(rs.getInt(1));
                tmp.getIdMateria().setId(rs.getInt(2));
                tmp.getIdMateria().setNombre(rs.getString(3));
                tmp.getIdPeriodoLectivo().setId_PerioLectivo(rs.getInt(4));
                tmp.getIdPeriodoLectivo().setFecha_Inicio(rs.getDate(5).toLocalDate());
                tmp.getIdPeriodoLectivo().setFecha_Fin(rs.getDate(6).toLocalDate());
                tmp.setEstadoSilabo(rs.getInt(7));
                silabos.add(tmp);
            }

        } catch (SQLException ex) {
            Logger.getLogger(SilaboBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return silabos;
    }

    public static List<SilaboMD> consultarCoordinador(ConexionBD conexion, String clave) {

        List<SilaboMD> silabos = new ArrayList<>();
        try {

            PreparedStatement st = conexion.getCon().prepareStatement("SELECT id_silabo, m.materia_nombre  FROM \"Silabo\" s\n"
                    + "JOIN \"Materias\" m ON s.id_materia=m.id_materia\n"
                    + "WHERE m.id_carrera=2");

            st.setString(1, clave);

            System.out.println(st);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {

                SilaboMD tmp = new SilaboMD();
                tmp.setIdSilabo(rs.getInt(1));
                tmp.getIdMateria().setId(rs.getInt(2));
                tmp.getIdMateria().setNombre(rs.getString(3));
                tmp.getIdMateria().setHorasDocencia(rs.getInt(4));
                tmp.getIdMateria().setHorasPracticas(rs.getInt(5));
                tmp.getIdMateria().setHorasAutoEstudio(rs.getInt(6));
                tmp.setEstadoSilabo(rs.getInt(7));
                tmp.getIdPeriodoLectivo().setId_PerioLectivo(rs.getInt(8));
                tmp.getIdPeriodoLectivo().setFecha_Inicio(rs.getDate(9).toLocalDate());
                tmp.getIdPeriodoLectivo().setFecha_Fin(rs.getDate(10).toLocalDate());

                silabos.add(tmp);
            }

        } catch (SQLException ex) {
            Logger.getLogger(SilaboBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return silabos;
    }

    public void eliminar() {

        try {
            PreparedStatement st = conexion.getCon().prepareStatement("DELETE FROM public.\"Silabo\"\n"
                    + "	WHERE id_silabo=?");

            st.setInt(1, getIdSilabo());
            st.executeUpdate();
            System.out.println(st);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(SilaboBD.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void actualizar() {

        try {
            PreparedStatement st = conexion.getCon().prepareStatement("UPDATE public.\"Silabo\"\n"
                    + "	SET id_materia=?, estado_silabo=?, id_prd_lectivo=?\n"
                    + "	WHERE id_silabo=?");

            st.setInt(1, getIdMateria().getId());
            st.setInt(2, getEstadoSilabo());
            st.setInt(3, getIdPeriodoLectivo().getId_PerioLectivo());
            st.setInt(4, getIdSilabo());
            st.executeUpdate();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(SilaboBD.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
//Ya ahora si se guarda en la base

    public static void guardarSilabo(ConexionBD conexion, FileInputStream fis, File f, SilaboMD s) {

        try {
            PreparedStatement st = conexion.getCon().prepareStatement("UPDATE public.\"Silabo\"\n"
                    + "	SET documento_silabo =?\n"
                    + "	WHERE id_silabo=?");

            st.setBinaryStream(1, fis, (int) f.length());

            st.setInt(2, s.getIdSilabo());
            st.execute();
            System.out.println(st);
            st.close();

        } catch (SQLException ex) {
            Logger.getLogger(SilaboBD.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void guardarAnalitico(ConexionBD conexion, FileInputStream fis1, File fl, SilaboMD s) {
        try {
            PreparedStatement st = conexion.getCon().prepareStatement("UPDATE public.\"Silabo\"\n"
                    + "	SET documento_analitico =?\n"
                    + "	WHERE id_silabo=?");

            st.setBinaryStream(1, fis1, (int) fl.length());

            st.setInt(2, s.getIdSilabo());
            st.execute();
            System.out.println(st);
            st.close();

        } catch (SQLException ex) {
            Logger.getLogger(SilaboBD.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    public SilaboMD retornaSilabo(int id) {
        SilaboMD silabo = null;
        try {

            PreparedStatement st = conexion.getCon().prepareStatement("SELECT id_silabo, id_materia, id_prd_lectivo, estado_silabo\n"
                    + "FROM public.\"Silabo\"\n"
                    + "WHERE id_silabo =?");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                silabo = new SilaboMD();

                silabo.setIdSilabo(rs.getInt(1));
            }

        } catch (SQLException ex) {
            Logger.getLogger(SilaboBD.class.getName()).log(Level.SEVERE, null, ex);

        }

        return silabo;
    }

    public void eliminar(SilaboMD s) {

        try {

            if (conexion.getCon() != null) {

                PreparedStatement st = conexion.getCon().prepareStatement("DELETE FROM public.\"Silabo\"\n"
                        + "	WHERE id_silabo=?");

                st.setInt(1, s.getIdSilabo());

                st.executeUpdate();

                System.out.println(st);
                st.close();
            }

        } catch (SQLException ex) {
            Logger.getLogger(SilaboBD.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void aprobar(SilaboMD s) {

        try {
            PreparedStatement st = conexion.getCon().prepareStatement("UPDATE public.\"Silabo\"\n"
                    + "	SET estado_silabo=1"
                    + "	WHERE id_silabo=?");

            st.setInt(1, s.getIdSilabo());

            st.executeUpdate();
            System.out.println(st);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(SilaboBD.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void insertar(SilaboMD s) {

        try {
            PreparedStatement st = conexion.getCon().prepareStatement("INSERT INTO public.\"Silabo\"(\n"
                    + "	 id_materia, id_prd_lectivo)\n"
                    + "	VALUES (?, ?)");

            st.setInt(1, s.getIdMateria().getId());
            st.setInt(2, s.getIdPeriodoLectivo().getId_PerioLectivo());
            st.executeUpdate();
            System.out.println(st);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(SilaboBD.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static List<SilaboMD> consultarSilabo1(ConexionBD conexion, String[] clave) {

        List<SilaboMD> silabos = new ArrayList<>();
        try {

            PreparedStatement st = conexion.getCon().prepareStatement("SELECT DISTINCT id_silabo,\n"
                    + "s.id_materia, m.materia_nombre\n"
                    + "FROM \"Silabo\" AS s\n"
                    + "JOIN \"Materias\" AS m ON s.id_materia=m.id_materia\n"
                    + "JOIN \"PeriodoLectivo\" AS pr ON pr.id_prd_lectivo=s.id_prd_lectivo\n"
                    + "JOIN \"Carreras\" AS crr ON crr.id_carrera = m.id_carrera\n"
                    + "JOIN \"Cursos\" AS cr ON cr.id_materia=m.id_materia\n"
                    + "JOIN \"Docentes\" AS d ON d.id_docente= cr.id_docente\n"
                    + "JOIN \"Personas\" AS p ON d.id_persona=p.id_persona\n"
                    + "WHERE crr.carrera_nombre=?\n"
                    + "AND p.id_persona=? AND prd_lectivo_nombre=?");

            st.setString(1, clave[0]);
            st.setInt(2, Integer.parseInt(clave[1]));
            st.setString(3, clave[2]);
            System.out.println(st);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {

                SilaboMD tmp = new SilaboMD();
                tmp.setIdSilabo(rs.getInt(1));
                tmp.getIdMateria().setId(rs.getInt(2));
                tmp.getIdMateria().setNombre(rs.getString(3));

                silabos.add(tmp);
            }

        } catch (SQLException ex) {
            Logger.getLogger(SilaboBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return silabos;
    }

    public static List<SilaboMD> consultarSilabo2(ConexionBD conexion, String carrera, int id_persona) {

        List<SilaboMD> silabos = new ArrayList<>();
        try {

            PreparedStatement st = conexion.getCon().prepareStatement("SELECT DISTINCT id_silabo,\n"
                    + "s.id_materia, m.materia_nombre, m.materia_horas_docencia,m.materia_horas_practicas,m.materia_horas_auto_estudio, estado_silabo,\n"
                    + "pr.id_prd_lectivo, pr.prd_lectivo_fecha_inicio, pr.prd_lectivo_fecha_fin\n"
                    + "FROM \"Silabo\" AS s\n"
                    + "JOIN \"Materias\" AS m ON s.id_materia=m.id_materia\n"
                    + "JOIN \"PeriodoLectivo\" AS pr ON pr.id_prd_lectivo=s.id_prd_lectivo\n"
                    + "JOIN \"Carreras\" AS crr ON crr.id_carrera = m.id_carrera\n"
                    + "JOIN \"Cursos\" AS cr ON cr.id_materia=m.id_materia\n"
                    + "JOIN \"Docentes\" AS d ON d.id_docente= cr.id_docente\n"
                    + "JOIN \"Personas\" AS p ON d.id_persona=p.id_persona\n"
                    + "WHERE crr.carrera_nombre=?\n"
                    + "AND p.id_persona=?");

            st.setString(1, carrera);
            st.setInt(2, id_persona);

            System.out.println(st);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {

                SilaboMD tmp = new SilaboMD();
                tmp.setIdSilabo(rs.getInt(1));
                tmp.getIdMateria().setId(rs.getInt(2));
                tmp.getIdMateria().setNombre(rs.getString(3));
                tmp.getIdMateria().setHorasDocencia(rs.getInt(4));
                tmp.getIdMateria().setHorasPracticas(rs.getInt(5));
                tmp.getIdMateria().setHorasAutoEstudio(rs.getInt(6));
                tmp.setEstadoSilabo(rs.getInt(7));
                tmp.getIdPeriodoLectivo().setId_PerioLectivo(rs.getInt(8));
                tmp.getIdPeriodoLectivo().setFecha_Inicio(rs.getDate(9).toLocalDate());
                tmp.getIdPeriodoLectivo().setFecha_Fin(rs.getDate(10).toLocalDate());

                silabos.add(tmp);
            }

        } catch (SQLException ex) {
            Logger.getLogger(SilaboBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return silabos;
    }

    public static List<SilaboMD> consultarAnteriores(ConexionBD conexion, Integer[] clave) {

        List<SilaboMD> silabos = new ArrayList<>();
        try {

            PreparedStatement st = conexion.getCon().prepareStatement("SELECT DISTINCT id_silabo,\n"
                    + "s.id_materia, m.materia_nombre, m.materia_horas_docencia,m.materia_horas_practicas,m.materia_horas_auto_estudio, estado_silabo,\n"
                    + "pr.id_prd_lectivo, pr.prd_lectivo_nombre\n"
                    + "FROM \"Silabo\" AS s\n"
                    + "JOIN \"Materias\" AS m ON s.id_materia=m.id_materia\n"
                    + "JOIN \"PeriodoLectivo\" AS pr ON pr.id_prd_lectivo=s.id_prd_lectivo\n"
                    + "JOIN \"Carreras\" AS crr ON crr.id_carrera = m.id_carrera\n"
                    + "JOIN \"Cursos\" AS cr ON cr.id_materia=m.id_materia\n"
                    + "JOIN \"Docentes\" AS d ON d.id_docente= cr.id_docente\n"
                    + "JOIN \"Personas\" AS p ON d.id_persona=p.id_persona\n"
                    + "WHERE m.id_materia=?\n"
                    + "AND p.id_persona=?");

            st.setInt(1, clave[0]);
            st.setInt(2, clave[1]);

            System.out.println(st);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {

                SilaboMD tmp = new SilaboMD();
                tmp.setIdSilabo(rs.getInt(1));
                tmp.getIdMateria().setId(rs.getInt(2));
                tmp.getIdMateria().setNombre(rs.getString(3));
                tmp.getIdMateria().setHorasDocencia(rs.getInt(4));
                tmp.getIdMateria().setHorasPracticas(rs.getInt(5));
                tmp.getIdMateria().setHorasAutoEstudio(rs.getInt(6));
                tmp.setEstadoSilabo(rs.getInt(7));
                tmp.getIdPeriodoLectivo().setId_PerioLectivo(rs.getInt(8));
                tmp.getIdPeriodoLectivo().setNombre_PerLectivo(rs.getString(9));

                silabos.add(tmp);
            }

        } catch (SQLException ex) {
            Logger.getLogger(SilaboBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return silabos;
    }

    public static SilaboMD consultarUltimo(ConexionBD conexion, int im, int ip) {
        SilaboMD silabo = null;
        try {

            PreparedStatement st = conexion.getCon().prepareStatement("SELECT id_silabo FROM \"Silabo\" WHERE id_materia=? AND id_prd_lectivo=?");
            st.setInt(1, im);
            st.setInt(2, ip);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                silabo = new SilaboMD();

                silabo.setIdSilabo(rs.getInt(1));
            }

        } catch (SQLException ex) {
            Logger.getLogger(SilaboBD.class.getName()).log(Level.SEVERE, null, ex);

        }

        return silabo;
    }

//    SELECT * FROM "Carreras" c 
//JOIN "Docentes" d ON c.id_docente_coordinador=d.id_docente
//JOIN "Personas" p ON p.id_persona=d.id_persona
//JOIN "Usuarios" u ON u.id_persona=p.id_persona
//WHERE u.usu_username='0103156675'
}
