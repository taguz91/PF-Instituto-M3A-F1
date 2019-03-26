/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.silabo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.carrera.CarreraMD;
import modelo.persona.DocenteMD;
import modelo.pgConect;

/**
 *
 * @author Andres Ullauri
 */
public class dbCarreras extends CarreraMD {

    pgConect conecta = new pgConect();

    public dbCarreras() {
    }

    public dbCarreras(int id, String codigo, String nombre, LocalDate fechaInicio, LocalDate fechaFin, String modalidad, DocenteMD coordinador) {
        super(id, codigo, nombre, fechaInicio, fechaFin, modalidad, coordinador);
    }

    

    public List<CarreraMD> buscarCarreras(int aguja) {

        try {
            List<CarreraMD> lista = new ArrayList<CarreraMD>();
            String sql = "SELECT DISTINCT \"Carreras\".id_carrera, carrera_nombre FROM \"Cursos\",\"PeriodoLectivo\",\"Docentes\",\"Personas\",\"Carreras\"\n"
                    + "WHERE \"Personas\".id_persona=" + aguja + "\n"
                    + "AND \"PeriodoLectivo\".id_prd_lectivo=\"Cursos\".id_prd_lectivo\n"
                    + "AND \"Carreras\".id_carrera=\"PeriodoLectivo\".id_carrera\n"
                    + "AND \"Personas\".id_persona=\"Docentes\".id_persona\n"
                    + "AND \"Cursos\".id_docente=\"Docentes\".id_docente";

            ResultSet rs = conecta.query(sql);

            while (rs.next()) {
                CarreraMD car = new CarreraMD();

                car.setId(Integer.parseInt(rs.getString("id_carrera")));
                car.setNombre(rs.getString("carrera_nombre"));

                lista.add(car);
            }
            rs.close();
            return lista;

        } catch (SQLException ex) {
            Logger.getLogger(dbCarreras.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }
    
    public CarreraMD retornaCarrera(String aguja) {

       try {
            CarreraMD carrera = null;
            String sql = "SELECT id_carrera FROM \"Carreras\" WHERE carrera_nombre='"+aguja+"'";
            ResultSet rs = conecta.query(sql);
            while (rs.next()) {
                carrera= new CarreraMD();
                carrera.setId(rs.getInt("id_carrera"));
                
               
            }

            rs.close();
            return carrera;
        } catch (SQLException ex) {
            Logger.getLogger(dbCarreras.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
