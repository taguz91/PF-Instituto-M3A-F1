/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.silabo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.pgConect;

/**
 *
 * @author Andres Ullauri
 */
public class dbPeriodoLectivo extends PeriodoLectivoMD {

    pgConect con = new pgConect();

    public List<PeriodoLectivoMD> mostrarPeriodosSilabo() {
        try {
            List<PeriodoLectivoMD> lista = new ArrayList<>();
            String sql = "SELECT \"PeriodoLectivo\".prd_lectivo_fecha_inicio,\"PeriodoLectivo\".prd_lectivo_fecha_fin\n"
                    + "FROM \"Materias\",\"Silabo\",\"PeriodoLectivo\",\"Carreras\"\n"
                    + "WHERE \"Materias\".id_materia=\"Silabo\".id_materia\n"
                    + "AND \"Materias\".id_carrera=\"Carreras\".id_carrera\n"
                    + "AND \"PeriodoLectivo\".id_carrera=\"Carreras\".id_carrera\n"
                    + "AND \"PeriodoLectivo\".prd_lectivo_fecha_inicio>='2018-11-12';";
            ResultSet rs = con.query(sql);

            while (rs.next()) {
                PeriodoLectivoMD p = new PeriodoLectivoMD();
                p.setFecha_Inicio(rs.getDate(1).toLocalDate());
                p.setFecha_Fin(rs.getDate(2).toLocalDate());

                lista.add(p);
            }
            rs.close();
            return lista;

        } catch (SQLException ex) {
            Logger.getLogger(dbPeriodoLectivo.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    public PeriodoLectivoMD retornaPeriodoActual(int  aguja) {

        try {
            PeriodoLectivoMD periodo = null;
            String sql = "SELECT DISTINCT prd_lectivo_fecha_inicio, prd_lectivo_fecha_fin\n"
                    + "FROM \"PeriodoLectivo\",\"Materias\",\"Carreras\" \n"
                    + "WHERE \"Materias\".id_carrera=\"Carreras\".id_carrera\n"
                    + "AND \"PeriodoLectivo\".id_carrera=\"Carreras\".id_carrera\n"
                    + "AND prd_lectivo_fecha_fin>current_date \n"
                    + "AND \"PeriodoLectivo\".id_carrera="+aguja+"";
            ResultSet rs = con.query(sql);
            while (rs.next()) {
                periodo = new PeriodoLectivoMD();
                periodo.setFecha_Inicio(rs.getDate(1).toLocalDate());
                periodo.setFecha_Fin(rs.getDate(2).toLocalDate());
            }

            rs.close();
            return periodo;
        } catch (SQLException ex) {
            Logger.getLogger(dbPeriodoLectivo.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
