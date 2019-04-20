/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.alumno;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.ConectarDB;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.persona.AlumnoMD;

/**
 *
 * @author Johnny
 */
public class MatriculaBD extends MatriculaMD {

    private String sql = "", nsql = "";

    private final ConectarDB conecta;

    public MatriculaBD(ConectarDB conecta) {
        this.conecta = conecta;
    }

    public void ingresar() {
        nsql = "INSERT INTO public.\"Matricula\"(\n"
                + "	id_alumno, id_prd_lectivo)\n"
                + "	VALUES (" + getAlumno().getId_Alumno() + ", " + getPeriodo().getId_PerioLectivo() + ");";
        if (conecta.nosql(nsql) == null) {
            JOptionPane.showMessageDialog(null, "Matricula realizada con exito.");
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo realizar la matricula, \n"
                    + "compruebe su conexion a internet.");
        }
    }

    public ArrayList<MatriculaMD> cargarMatriculas() {
        sql = "SELECT id_matricula, m.id_alumno, m.id_prd_lectivo, matricula_fecha, \n"
                + "persona_identificacion, persona_primer_nombre, persona_segundo_nombre, \n"
                + "persona_primer_apellido, persona_segundo_apellido, prd_lectivo_nombre\n"
                + "FROM public.\"Matricula\" m, public.\"PeriodoLectivo\" pl,\n"
                + "public.\"Alumnos\" a, public.\"Personas\" p\n"
                + "	WHERE pl.id_prd_lectivo = m.id_prd_lectivo AND \n"
                + "	a.id_alumno = m.id_alumno AND \n"
                + "	p.id_persona = a.id_persona; ";
        return consultarParaTbl(sql);
    }

    private ArrayList<MatriculaMD> consultarParaTbl(String sql) {
        ArrayList<MatriculaMD> matriculas = new ArrayList();
        ResultSet rs = conecta.sql(sql);
        if (rs != null) {
            try {
                while (rs.next()) {
                    MatriculaMD m = new MatriculaMD();
                    AlumnoMD a = new AlumnoMD();
                    PeriodoLectivoMD p = new PeriodoLectivoMD();
                    m.setId(rs.getInt("id_matricula"));
                    a.setId_Alumno(rs.getInt("id_alumno"));
                    p.setId_PerioLectivo(rs.getInt("id_prd_lectivo"));
                    m.setFecha(rs.getTimestamp("matricula_fecha").toLocalDateTime());
                    a.setIdentificacion("persona_identificacion");
                    a.setPrimerNombre(rs.getString("persona_primer_nombre"));
                    a.setSegundoNombre(rs.getString("persona_segundo_nombre"));
                    a.setPrimerApellido(rs.getString("persona_primer_apellido"));
                    a.setSegundoApellido(rs.getString("persona_segundo_apellido"));
                    p.setNombre_PerLectivo(rs.getString("prd_lectivo_nombre"));
                    m.setAlumno(a);
                    m.setPeriodo(p);
                    
                    matriculas.add(m); 
                }
                return matriculas;
            } catch (SQLException e) {
                System.out.println("No se pudieron consultar matriculas. "+e.getMessage());
                return null;
            }
        } else {
            return null;
        }
    }

}
