/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.AvanceSilabo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.ConexionBD;
import modelo.silabo.SilaboMD;

/**
 *
 * @author HP
 */
public class SeguimientoSilaboDB extends SeguimientoSilabo {

    private ConexionBD conexion;

    public SeguimientoSilaboDB() {
    }

    public SeguimientoSilaboDB(ConexionBD conexion) {
        this.conexion = conexion;
    }

    public SeguimientoSilaboDB(ConexionBD conexion, int id_seguimiento, LocalDate fecha_entrega, boolean esInterciclo, int id_curso) {
        super(id_seguimiento, fecha_entrega, esInterciclo, id_curso);
        this.conexion = conexion;
    }

    public void insertaAvance(SeguimientoSilabo sg) {

        try {
            PreparedStatement st = conexion.getCon().prepareStatement("INSERT INTO public.\"SeguimientoSilabo\"(\n"
                    + "	fecha_entrga_informe, \"esInterciclo\", id_curso)\n"
                    + "	VALUES (?, ?, ?);");

            st.setDate(1, java.sql.Date.valueOf(sg.getFecha_entrega()));
            st.setBoolean(2, sg.isInterciclo());
            st.setInt(3, sg.getId_curso());
            st.executeUpdate();
            System.out.println(st);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(SeguimientoSilaboDB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public int ultimoAvance(int id_curso) {
        int max = 0;
        String sql = "SELECT max(id_seguimientosilabo) FROM \"SeguimientoSilabo\" where id_curso=?";
        PreparedStatement st;
        try {
            st = conexion.getCon().prepareStatement(sql);
            st.setInt(1, id_curso);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                max = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SeguimientoSilaboDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return max;
    }

    public SeguimientoSilabo ultimoAvanceOb(int id_curso) {
        SeguimientoSilabo seAvan = null;
        String sql = "SELECT id_seguimientosilabo,fecha_entrga_informe,id_curso,\n"
                + "\"esInterciclo\",max(id_seguimientosilabo) FROM \"SeguimientoSilabo\" where id_curso=? group by \n"
                + "id_seguimientosilabo,fecha_entrga_informe,id_curso";
         PreparedStatement st;
        try {
            st = conexion.getCon().prepareStatement(sql);
            st.setInt(1, id_curso);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                seAvan.setEsInterciclo(rs.getBoolean(4));
                seAvan.setFecha_entrega(rs.getDate(2).toLocalDate());
                seAvan.setId_curso(rs.getInt(3));
                seAvan.setId_seguimiento(rs.getInt(1));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(SeguimientoSilaboDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        return seAvan;
    }
}
