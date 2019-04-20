package modelo.curso;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.ConectarDB;

/**
 *
 * @author Johnny
 */
public class SesionClaseBD extends SesionClaseMD {

    private final ConectarDB conecta;
    private String sql, nsql;

    public SesionClaseBD(ConectarDB conecta) {
        this.conecta = conecta;
    }

    public void ingresar() {
        nsql = "INSERT INTO public.\"SesionClase\"(\n"
                + "	id_curso, dia_sesion, hora_inicio_sesion, hora_fin_sesion)\n"
                + "	VALUES (" + getCurso().getId_curso() + ", " + getDia() + ", "
                + "'" + getHoraIni() + "', '" + getHoraFin() + "');";
        if (conecta.nosql(nsql) == null) {
            JOptionPane.showMessageDialog(null, "Se guardo correctamente el horario.");
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo guardar el horario correctamente.");
        }
    }

    public void editar(int idSesion) {
        nsql = "UPDATE public.\"SesionClase\"\n"
                + "	SET  dia_sesion=" + getDia() + ", "
                + " hora_inicio_sesion=" + getHoraIni() + ", hora_fin_sesion=" + getHoraFin() + "\n"
                + "	WHERE id_sesion=" + idSesion + ";";
        if (conecta.nosql(nsql) == null) {
            JOptionPane.showMessageDialog(null, "Se edito correctamente el horario.");
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo editar correctamente el horario.");
        }
    }

    public void eliminar(int idSesion) {
        nsql = "DELETE FROM public.\"SesionClase\"\n"
                + "	WHERE id_sesion= " + idSesion + ";";
        if (conecta.nosql(nsql) == null) {
            JOptionPane.showMessageDialog(null, "Se elimino correctamente el horario.");
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo eliminar correctamente el horario.");
        }
    }

    public SesionClaseMD buscarSesion(int idSesion) {
        SesionClaseMD s = new SesionClaseMD();
        sql = "SELECT id_sesion, id_curso, dia_sesion, hora_inicio_sesion, hora_fin_sesion \n"
                + "	FROM public.\"SesionClase\" \n"
                + "	WHERE id_sesion = " + idSesion + " ;";
        ResultSet rs = conecta.sql(sql);
        if (rs != null) {
            try {
                while (rs.next()) {
                    CursoMD c = new CursoMD();
                    s.setId(rs.getInt("id_sesion"));
                    c.setId_curso(rs.getInt("id_curso"));
                    s.setCurso(c);
                    s.setDia(rs.getInt("dia_sesion"));
                    s.setHoraFin(rs.getTime("hora_fin_sesion").toLocalTime());
                    s.setHoraIni(rs.getTime("hora_inicio_sesion").toLocalTime());
                }
                return s;
            } catch (SQLException e) {
                System.out.println("No se pudo consultar sesion " + e.getMessage());
                return null;
            }
        } else {
            return null;
        }
    }

    public ArrayList<SesionClaseMD> cargarHorarioCursoPorDia(int idCurso, int dia) {
        sql = "SELECT id_sesion, dia_sesion, hora_inicio_sesion, hora_fin_sesion \n"
                + "	FROM public.\"SesionClase\" \n"
                + "	WHERE id_curso = " + idCurso + " \n"
                + "	AND dia_sesion = " + dia + " ;";
        ArrayList<SesionClaseMD> sesiones = new ArrayList<>();
        ResultSet rs = conecta.sql(sql);
        //System.out.println(sql);
        if (rs != null) {
            try {
                while (rs.next()) {
                    SesionClaseMD s = new SesionClaseMD();
                    s.setDia(rs.getInt("dia_sesion"));
                    s.setHoraFin(rs.getTime("hora_fin_sesion").toLocalTime());
                    s.setHoraIni(rs.getTime("hora_inicio_sesion").toLocalTime());
                    s.setId(rs.getInt("id_sesion"));

                    sesiones.add(s);
                }
                return sesiones;
            } catch (SQLException e) {
                return null;
            }
        } else {
            return null;
        }
    }

}
