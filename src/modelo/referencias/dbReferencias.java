/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.referencias;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import modelo.pgConect;

/**
 *
 * @author Andres Ullauri
 */
public class dbReferencias extends Referencias {

    pgConect conecta = new pgConect();

    public dbReferencias() {
    }

    public List<Referencias> mostrarReferencias() {
        try {
            List<Referencias> lista = new ArrayList<>();
            String sql = "SELECT * FROM \"Referencias\" WHERE existe_en_biblioteca=true";
            ResultSet rs = conecta.query(sql);

            while (rs.next()) {
                Referencias r = new Referencias();
                r.setIdReferencia(rs.getInt("id_referencia"));
                r.setCodigoReferencia(rs.getString("codigo_referencia"));
                r.setDescripcionReferencia(rs.getString("descripcion_referencia"));
                r.setTipoReferencia(rs.getString("tipo_referencia"));
                r.setExisteEnBiblioteca(rs.getBoolean("existe_en_biblioteca"));

                lista.add(r);
            }
            rs.close();
            return lista;

        } catch (SQLException ex) {
            Logger.getLogger(dbReferencias.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public List<Referencias> buscarReferencias(String aguja) {
        try {
            List<Referencias> lista = new ArrayList<>();
            String sql = "SELECT * FROM \"Referencias\" "
                    + "WHERE (codigo_referencia ILIKE '%" + aguja + "%' OR descripcion_referencia ILIKE '%" + aguja + "%') AND existe_en_biblioteca=true";
            ResultSet rs = conecta.query(sql);

            while (rs.next()) {
                Referencias r = new Referencias();
                r.setIdReferencia(rs.getInt("id_referencia"));
                r.setCodigoReferencia(rs.getString("codigo_referencia"));
                r.setDescripcionReferencia(rs.getString("descripcion_referencia"));
                r.setTipoReferencia(rs.getString("tipo_referencia"));
                r.setExisteEnBiblioteca(rs.getBoolean("existe_en_biblioteca"));

                lista.add(r);
            }
            rs.close();
            return lista;

        } catch (SQLException ex) {
            Logger.getLogger(dbReferencias.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public boolean insertarDatos(Referencias r) {

        String nSql = "INSERT INTO public.\"Referencias\"(\n"
                + "	 codigo_referencia,descripcion_referencia, tipo_referencia, existe_en_biblioteca)\n"
                + "	VALUES ('" + r.getCodigoReferencia() + "','" + r.getDescripcionReferencia() + "','" + r.getTipoReferencia() + "'," + r.getExisteEnBiblioteca() + ")";

        if (conecta.noQuery(nSql) == null) {
            return true;
        } else {
            System.out.println("Error");
            return false;
        }

    }

    public boolean EliminarReferencia(int id) {
        String sql = "DELETE FROM public.\"Referencias\"\n"
                + "	WHERE id_referencia=" + id + "";
        if (conecta.noQuery(sql) == null) {
            return true;
        } else {
            System.out.println("ERROR");
            return false;
        }
    }

    public Referencias retornaReferencia(int silabo, String referencia) {

        try {
            Referencias ref = null;
            String sql = "SELECT \"Referencias\".id_referencia FROM \"Referencias\", \"ReferenciaSilabo\"\n"
                    + "WHERE \"Referencias\".id_referencia=\"ReferenciaSilabo\".id_referencia\n"
                    + "AND \"Referencias\".descripcion_referencia='"+referencia +"' AND \"ReferenciaSilabo\".id_silabo="+silabo+"";
            ResultSet rs = conecta.query(sql);
            while (rs.next()) {
                ref = new Referencias();
               
                ref.setIdReferencia(rs.getInt(1));

            }

            rs.close();
            return ref;
        } catch (SQLException ex) {
            Logger.getLogger(dbReferencias.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
