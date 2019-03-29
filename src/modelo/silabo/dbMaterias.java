package modelo.silabo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.*;
import modelo.carrera.CarreraMD;
import modelo.materia.MateriaMD;

public class dbMaterias extends MateriaMD {

    pgConect con = new pgConect();

    public List<MateriaMD> buscarMateria(int[] aguja) {

        List<MateriaMD> lista = new ArrayList<>();
        String sql = "SELECT DISTINCT \"Materias\".id_materia, materia_nombre FROM \"Materias\",\"Cursos\",\"Docentes\",\"Personas\"\n"
                + "WHERE \"Personas\".id_persona=" + aguja[0] + "\n"
                + "AND \"Materias\".id_materia=\"Cursos\".id_materia\n"
                + "AND id_carrera=" + aguja[1] + "\n"
                + "AND \"Personas\".id_persona=\"Docentes\".id_persona\n"
                + "AND \"Cursos\".id_docente=\"Docentes\".id_docente";

        ResultSet rs = con.query(sql);
        System.out.println(sql);
        try {
            while (rs.next()) {
               MateriaMD mat = new MateriaMD();
                mat.setId(rs.getInt(1));
                mat.setNombre(rs.getString(2));
                lista.add(mat);
            }
            rs.close();
            return lista;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public MateriaMD retornaMateria(String aguja) {

        try {
            MateriaMD materia = null;
            String sql = "SELECT id_materia,materia_horas_docencia,materia_horas_practicas,materia_horas_auto_estudio, id_carrera FROM \"Materias\" WHERE materia_nombre='" + aguja + "'";
            ResultSet rs = con.query(sql);
            while (rs.next()) {
                materia = new MateriaMD();
                materia.setCarrera(new CarreraMD());
                materia.setId(Integer.parseInt(rs.getString("id_materia")));
                materia.setHorasDocencia(rs.getInt("materia_horas_docencia"));
                materia.setHorasPracticas(rs.getInt("materia_horas_practicas"));
                materia.setHorasAutoEstudio(rs.getInt("materia_horas_auto_estudio"));
                materia.getCarrera().setId(rs.getInt("id_carrera"));
            }

            rs.close();
            return materia;
        } catch (SQLException ex) {
            Logger.getLogger(dbCarreras.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public List<MateriaMD> mostrarMateriasSilabo(int aguja) {
        try {
            List<MateriaMD> lista = new ArrayList<>();
            String sql = "SELECT DISTINCT \"Materias\".id_materia, materia_nombre \n"
                    + "FROM \"Materias\",\"Cursos\",\"Docentes\",\"Personas\",\"Silabo\",\"PeriodoLectivo\"\n"
                    + "WHERE \"Personas\".id_persona="+aguja+"\n"
                    + "AND \"Materias\".id_materia=\"Cursos\".id_materia\n"
                    + "AND \"Personas\".id_persona=\"Docentes\".id_persona\n"
                    + "AND \"Cursos\".id_docente=\"Docentes\".id_docente\n"
                    + "AND \"Silabo\".id_materia=\"Materias\".id_materia\n"
                    + "AND \"PeriodoLectivo\".prd_lectivo_fecha_inicio>='2018-11-12'";
            ResultSet rs = con.query(sql);

            while (rs.next()) {
                MateriaMD m = new MateriaMD();
                m.setId(rs.getInt(1));
                m.setNombre(rs.getString(2));
                
                lista.add(m);
            }
            rs.close();
            return lista;

        } catch (SQLException ex) {
            Logger.getLogger(dbMaterias.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

}
