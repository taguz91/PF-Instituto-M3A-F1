/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.alumno;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.ConectarDB;

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
        sql = "";
        return null;
    }

}
