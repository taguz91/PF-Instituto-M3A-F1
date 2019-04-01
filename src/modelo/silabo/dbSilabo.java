package modelo.silabo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.*;
import modelo.materia.MateriaMD;
import modelo.referenciasSilabo.ReferenciaSilabo;

public class dbSilabo extends Silabo {

    pgConect con = new pgConect();

    public dbSilabo() {
    }

    public dbSilabo(Integer idSilabo) {
        super(idSilabo);
    }

    public dbSilabo(Integer idSilabo, String estadoSilabo) {
        super(idSilabo, estadoSilabo);
    }

    public dbSilabo(MateriaMD idMateria, String estadoSilabo) {
        super(idMateria, estadoSilabo);
    }

    public boolean InsertarSilabo() {
        String sql = "INSERT INTO public.\"Silabo\"(\n"
                + "	id_silabo, id_materia, estado_silabo)\n"
                + "	VALUES (" + getIdSilabo() + "," + getIdMateria().getId() + ",'" + getEstadoSilabo() + "')";
        if (con.noQuery(sql) == null) {
            return true;
        } else {
            System.out.println("ERROR");
            return false;
        }
    }

    public boolean ModificarSilabo(int id) {
        String sql = "UPDATE public.\"Silabo\"\n"
                + "	SET id_silabo=" + getIdSilabo() + ", id_materia=" + getIdMateria() + ", estado_silabo='" + getEstadoSilabo() + "'\n"
                + "	WHERE id_silabo=" + id + "";
        if (con.noQuery(sql) == null) {
            return true;
        } else {
            System.out.println("ERROR");
            return false;
        }
    }

    public boolean EliminarSilabo(int id) {
        String sql = "DELETE FROM public.\"Silabo\"\n"
                + "	WHERE id_silabo=" + id + "";
        if (con.noQuery(sql) == null) {
            return true;
        } else {
            System.out.println("ERROR");
            return false;
        }
    }

    public List<Silabo> mostrarDatosSilabo() {
        List<Silabo> lista = new ArrayList<Silabo>();
        String sql = "SELECT * FROM public.\"Silabo\"";

        ResultSet rs = con.query(sql);
        System.out.println(sql);
        try {
            while (rs.next()) {
                Silabo s = new Silabo();
                s.setIdSilabo(rs.getInt(1));
                //s.setIdMateria(rs.get);
                s.setEstadoSilabo(rs.getString(1));
                lista.add(s);
            }
            rs.close();
            return lista;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public void cargarCarreras() {
        String sql = "SELECT carrera_nombre FROM public.\"Silabo\"";
        ResultSet rs = con.query(sql);
        try {
            while (rs.next()) {
                rs.getString("carrera_nombre");
            }
            rs.close();

        } catch (Exception e) {
            System.out.println(e);

        }

    }

    public boolean insertarDatos() {

        String nSql = "INSERT INTO public.\"Silabo\"(\n"
                + "	 id_materia, estado_silabo)\n"
                + "	VALUES ( " + this.getIdMateria().getId() + ",'" + this.getEstadoSilabo() + "')";

        if (con.noQuery(nSql) == null) {
            return true;
        } else {
            System.out.println("Error");
            return false;
        }
    }

    public boolean insertarReferencias(ReferenciaSilabo rs) {

        String nSql;
        if (rs.getIdReferencia().getIdReferencia() != null) {
            nSql = "INSERT INTO public.\"ReferenciaSilabo\"(\n"
                    + "	 id_referencia, id_silabo)\n"
                    + "	VALUES (" + rs.getIdReferencia().getIdReferencia() + "," + "(SELECT MAX(id_silabo) FROM \"Silabo\"))";

        } else {

            nSql = "INSERT INTO public.\"ReferenciaSilabo\"(\n"
                    + "	 id_referencia, id_silabo)\n"
                    + "	VALUES ((SELECT MAX(id_referencia) FROM \"Referencias\"),(SELECT MAX(id_silabo) FROM \"Silabo\"))";

        }
        if (con.noQuery(nSql) == null) {
            return true;
        } else {
            System.out.println("Error");
            return false;
        }
    }

    public String cod_sib() {
        String sql = "select max(id_silabo) from \"Silabo\"";
        ResultSet rs = con.query(sql);
        String id = null;
        try {
            while (rs.next()) {
                id = rs.getString("max");
            }
            rs.close();
            return id;

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }

    }

    public Silabo retornaSilabo(int aguja) {
        try {
            Silabo silabo = null;
            String sql = "SELECT id_silabo FROM \"Silabo\",\"Materias\"\n"
                    + "WHERE \"Materias\".id_materia=\"Silabo\".id_materia\n"
                    + "AND \"Materias\".id_materia=" + aguja + "";
            ResultSet rs = con.query(sql);
            while (rs.next()) {
                silabo = new Silabo();

                silabo.setIdSilabo(rs.getInt(1));
            }

            rs.close();
            return silabo;
        } catch (SQLException ex) {
            Logger.getLogger(dbSilabo.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public List<Silabo> mostrarSilabos(int id, String aguja, String filtro) {
        List<Silabo> lista = new ArrayList<Silabo>();
        String sql = "SELECT DISTINCT \"Materias\".id_materia,\"Materias\".materia_nombre, \"Silabo\".estado_silabo \n"
                + "FROM \"Materias\",\"Silabo\",\"PeriodoLectivo\",\"Carreras\",\"Cursos\",\"Docentes\",\"Personas\"\n"
                + "WHERE \"Materias\".id_materia=\"Silabo\".id_materia\n"
                + "AND \"Materias\".id_carrera =\"Carreras\".id_carrera\n"
                + "AND \"Carreras\".id_carrera = \"PeriodoLectivo\".id_carrera\n"
                + "AND \"PeriodoLectivo\".prd_lectivo_fecha_fin>'2018-11-12'\n"
                + "AND \"Personas\".id_persona="+id+"\n"
                + "AND \"Materias\".id_materia=\"Cursos\".id_materia\n"
                + "AND \"Personas\".id_persona=\"Docentes\".id_persona\n"
                + "AND \"Cursos\".id_docente=\"Docentes\".id_docente\n"
                + "AND \"Carreras\".carrera_nombre='"+filtro+"'\n"
                + "AND (\"Materias\".materia_nombre ILIKE '%"+aguja+"%' \n"
                + "	 OR TO_CHAR(\"PeriodoLectivo\".prd_lectivo_fecha_inicio,'yyyy-MM-dd') ILIKE '%"+aguja+"%'\n"
                + "	OR TO_CHAR(\"PeriodoLectivo\".prd_lectivo_fecha_inicio,'yyyy-MM-dd') ILIKE '%"+aguja+"%'\n"
                + "	OR \"Silabo\".estado_silabo ILIKE '%"+aguja+"%')";

        ResultSet rs = con.query(sql);
        System.out.println(sql);
        try {
            while (rs.next()) {
                Silabo s = new Silabo();
                s.getIdMateria().setId(rs.getInt(1));
                s.getIdMateria().setNombre(rs.getString(2));
                s.setEstadoSilabo(rs.getString(3));

                lista.add(s);
            }
            rs.close();
            return lista;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
